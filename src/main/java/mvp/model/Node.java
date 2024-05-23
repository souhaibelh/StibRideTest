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

    /**
     * Constructor
     * @param station the station that this node will hold
     */
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
     *  Adds a Node as an adjacent node to this node with a weight on it (ponderation)
     * @param node the node we wish to add as adjacent
     * @param weight distance of that node to our node
     */
    public void addAdjacentNode(Node node, Integer weight) {
        adjacentNodes.put(node, weight);
    }

    /**
     * Returns the previous node
     * @return the node before this one, the node that points to this node (helps us backtrack for djikstra)
     */
    public Node getPreviousNode() {
        return previousNode;
    }

    /**
     * Returns all adjacent nodes of this node
     * @return adjacent nodes
     */
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * Changes the distance from the start node to this node
     * @param newDistance new distance
     */
    public void setDistance(int newDistance) {
        shortestDistanceRoot = newDistance;
    }

    /**
     * Will set a previous node, when we find this node through djikstra we call this method to indicate what node found
     * it, or if a shortest distance is found also.
     * @param node
     */
    public void setPrevNode(Node node) {
        this.previousNode = node;
    }

    /**
     * @return distance from this node to the start node
     */
    public int getDistance() {
        return shortestDistanceRoot;
    }

    @Override
    public String toString() {
        return station.toString();
    }
}
