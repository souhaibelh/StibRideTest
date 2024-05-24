package mvp.model;
import mvp.exceptions.database.FavoritesTableUKViolation;
import mvp.model.db.repository.FavoritesRepository;
import mvp.model.db.repository.StationsNlRepository;
import mvp.model.db.repository.StationsRepository;
import mvp.model.db.repository.StopsRepository;
import mvp.model.util.ModelEvent;
import mvp.model.util.ModelUpdate;
import mvp.model.util.Observable;
import mvp.model.util.Observer;
import mvp.model.db.dto.FavoritesDto;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.dto.StopsDto;
import mvp.model.db.tablepk.FavoritesPK;
import mvp.model.db.tablepk.StationsPK;
import java.util.*;

/**
 * Model class, handles all database logic and handles the talking with the dijkstra algorithm.
 */
public class Model implements Observable {
    private final List<Observer> observers;
    private final StopsRepository stopsRepository;
    private final StationsRepository stationsRepository;
    private final FavoritesRepository favRepository;
    private final StationsNlRepository stationsNlRepository;
    private Dijkstra d;

    public Model() {
        observers = new ArrayList<>();
        stopsRepository = new StopsRepository();
        stationsRepository = new StationsRepository();
        favRepository = new FavoritesRepository();
        stationsNlRepository = new StationsNlRepository();
    }

    /**
     * @return all the stations in our Stations table
     */
    public List<StationsDto> getAllStations() {
        return stationsRepository.getAll();
    }

    /**
     * @return all the favorite rides in our Favorites table
     */
    public List<FavoritesDto> getAllFavoriteRides() {
        return favRepository.getAll();
    }

    /**
     * Adds observer to watch for model updates
     * @param observer observer of model
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes observer so it stops watching our model
     * @param observer observer to remove
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of a ModelUpdate
     * @param update the update that we wish to notify to our observers
     */
    @Override
    public void notifyObservers(ModelUpdate update) {
        observers.forEach((o) -> o.update(update));
    }

    /**
     * Receives a StationsDto object and creates a node with it, by going to the database and retreiving the stationsDto
     * object, with its full data.
     * @param station station that we are trying to get as a node
     * @return node created with that station retrieved from the database
     */
    public Node getStationAsNode(StationsDto station) {
        StationsPK pk = new StationsPK(station.getIdStation());
        StationsDto dto = stationsRepository.get(pk);
        return new Node(dto);
    }

    /**
     * Method that receives an origin and a destination, it then gets all stations in the database through the getAllStations()
     * method and creates an array of nodes, and for each station it makes a node and calls a method that returns a node that
     * contains that station, then adds the node to the list of allNodes. Now to link all nodes (adjacency) it calls a method
     * that does so, then it gets the start and end node by matching the origin and destination station passed as parameters,
     * it then creates a new dijkstra instance with all the nodes (graph), and calculates the shortest Path, then it proceeds
     * to inform the observers that the shortest path was calculated.
     * @param origin start station for dijkstra algorithm
     * @param destination end station for dijkstra algorithm
     */
    public void shortestPath(StationsDto origin, StationsDto destination) {
        List<StationsDto> allStations = getAllStations();
        List<Node> allNodes = new ArrayList<>();
        allStations.forEach((s) -> {
            Node node = getStationAsNode(s);
            allNodes.add(node);
        });
        linkNodes(allNodes);
        Node start = getNodeByStation(allNodes, origin);
        Node end = getNodeByStation(allNodes, destination);

        d = new Dijkstra(allNodes, start, end);
        d.calculate();
        System.out.println(d.getShortestPath());
        notifyObservers(new ModelUpdate(ModelEvent.SHORTEST_PATH_CALCULATED));
    }

    /**
     * @return shortest path calculated by dijkstra algorithm.
     */
    public List<Node> getShortestPath() {
        return d.getShortestPath();
    }

    /**
     * Receives a favoriteDto and attempts to save it
     * @param favoriteDto name of the ride
     */
    public void saveRide(FavoritesDto favoriteDto) {
        try {
            favRepository.add(favoriteDto);
            notifyObservers(new ModelUpdate(ModelEvent.SAVE_RIDE_SUCCESS));
        } catch (FavoritesTableUKViolation e) {
            Object[] information = new Object[2];
            information[0] = e.getOrigin();
            information[1] = e.getDestination();
            notifyObservers(new ModelUpdate(ModelEvent.FAVORITES_TABLE_UK_VIOLATION, information));
        }
    }

    /**
     * Attempts to delete a favorite ride by receiving its primary key.
     * @param key primary key of favorite ride to delete from table
     */
    public void deleteFavoriteRide(FavoritesPK key) {
        favRepository.delete(key);
        notifyObservers(new ModelUpdate(ModelEvent.DELETE_FAVORITE_RIDE_SUCCESS));
    }

    /**
     * It attempts to save multiple favorite rides, validates the name of each, then calls the repository method
     * that adds/updates them all, the method returns an array containing in the first position an integer indicating
     * how many of these rows only needed an updating, in the second position of the array there is the count of new
     * added rows.
     * @param favDtoList list of favorite rides to update/add
     */
    public void saveFavRides(List<FavoritesDto> favDtoList) {
        try {
            Object[] information = favRepository.addAll(favDtoList);
            notifyObservers(new ModelUpdate(ModelEvent.SAVE_FAVORITE_CHANGES_SUCCESS, information));
        } catch (FavoritesTableUKViolation e) {
            Object[] information = new Object[2];
            information[0] = e.getOrigin();
            information[1] = e.getDestination();
            notifyObservers(new ModelUpdate(ModelEvent.FAVORITES_TABLE_UK_VIOLATION, information));
        }
    }

    /**
     * Receives a list of nodes and a station dto, it returns the node that contains that station dto.
     * @param node node list
     * @param station station that the node must have
     * @return first encountered node with that station dto
     */
    public Node getNodeByStation(List<Node> node, StationsDto station) {
        Node wantedNode = null;
        for (Node value : node) {
            if (value.getStation().equals(station)) {
                wantedNode = value;
                break;
            }
        }
        return wantedNode;
    }

    /**
     * Method that receives a list of nodes and links them between them, (it links each node to its adjacent nodes).
     * It does that like follows: For each node, we get the station connected to it, and for that station we get all
     * stops it's involved in, and for each of those stops we check the next and previous stop to our station,
     * and we retrieve the stations connected to them, those are the surrounding stations, for each of those stations
     * I find the node that holds them and I add that node to be the adjacentNode of my mainNode.
     * @param nodes list of nodes to link
     */
    public void linkNodes(List<Node> nodes) {
        int defaultWeight = 1;
        nodes.forEach((n) -> {
            StationsDto heldStation = n.getStation();

            List<StopsDto> stopsOfStation = stopsRepository.selectByStation(heldStation.getIdStation());
            List<StationsDto> adjacentStations = new ArrayList<>();
            stopsOfStation.forEach((stop) -> {
                int id_order = stop.getOrderId();
                int id_line = stop.getIdLine();
                StopsDto nextStop = stopsRepository.selectByLineOrder(id_line, id_order + 1);

                if (nextStop != null) {
                    StationsPK pk = new StationsPK(nextStop.getIdStation());
                    StationsDto stationInNextStop = stationsRepository.get(pk);
                    if (!adjacentStations.contains(stationInNextStop)) {
                        adjacentStations.add(stationInNextStop);
                    }
                }

                if (id_order > 1) {
                    StopsDto previousStop = stopsRepository.selectByLineOrder(id_line, id_order - 1);
                    StationsPK pk = new StationsPK(previousStop.getIdStation());
                    StationsDto stationInPreviousStop = stationsRepository.get(pk);
                    if (!adjacentStations.contains(stationInPreviousStop)) {
                        adjacentStations.add(stationInPreviousStop);
                    }
                }
            });

            adjacentStations.forEach((adjstation) -> {
                nodes.forEach((node) -> {
                    if (node.getStation().equals(adjstation)) {
                        n.addAdjacentNode(node, defaultWeight);
                    }
                });
            });
        });
    }

    public List<StationsDto> getAllNlStations() {
        return stationsNlRepository.getAll();
    }
}
