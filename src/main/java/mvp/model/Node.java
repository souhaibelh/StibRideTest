package mvp.model;
import mvp.model.db.dto.StationsDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents a Node that can contain information about a station, adjacent nodes...
 */
public class Node {
    private final Map<Node, Integer> adjacentNodes;
    private int shortestDistanceRoot;
    private Node previousNode;
    private final StationsDto station;

    public Node(StationsDto station) {
        this.station = station;
        adjacentNodes = new HashMap<>();
        shortestDistanceRoot = Integer.MAX_VALUE;
    }

    /**
     * Method that returns the station held by this node
     * @return station held by this node
     */
    public StationsDto getStation() {
        return this.station;
    }

    /**
     *
     * @param node
     * @param weight
     */
    public void addAdjacentNode(Node node, Integer weight) {
        adjacentNodes.put(node, weight);
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setDistance(int newDistance) {
        shortestDistanceRoot = newDistance;
    }

    public void setPrevNode(Node node) {
        this.previousNode = node;
    }

    public int getDistance() {
        return shortestDistanceRoot;
    }

    @Override
    public String toString() {
        return station.toString();
    }
}
