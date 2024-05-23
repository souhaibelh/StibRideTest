package mvp.model;

import java.util.*;

/**
 * Class that implements the Dijkstra algorithm
 */
public class Dijkstra {
    private final Node start;
    private final Node end;
    private final List<Node> visited;
    private final List<Node> unvisited;
    private List<Node> shortestPath;

    /**
     * Constructor of Dijkstra class
     * @param nodes the nodes that represent the graph
     * @param start the start node
     * @param end the end node
     */
    public Dijkstra(List<Node> nodes, Node start, Node end) {
        this.start = start;
        this.end = end;
        visited = new ArrayList<>();
        unvisited = nodes;
        shortestPath = new ArrayList<>();
        start.setDistance(0);
    }

    /**
     * Calculates the shortest path between start and end by calling dijkstraHelper
     */
    public void calculate() {
        dijkstraHelper(start, end);
    }

    /**
     * If the shortestPath wasn't yet calculated it calculates it then it returns it
     * @return the shortest path between node start and node end
     */
    public List<Node> getShortestPath() {
        if (shortestPath == null) {
            calculate();
        }
        return shortestPath;
    }

    /**
     * Main algorithm of dijkstra.
     * We start with current node, we get the surrounding nodes, and we calculate what the distance would be if the shortest
     * path to them was the current node. So we basically add the distance from the main node to the current node to the
     * weight ("ponderation") of the surrounding nodes, if it's smaller than their current set distance to the main node
     * then we update their distances for the new calculated value. We mark then the current node as visited, and we call
     * the method again on the node with the smallest distance from the origin.
     * We do this until the visited list contains the end node (which means we visited it so we already know the shortest
     * distance from the main node to it)
     *
     * @param currentNode current node we are visiting
     * @param end the end node
     */
    public void dijkstraHelper(Node currentNode, Node end) {
        if (visited.contains(end)) {
            shortestPath = getPath(end);
            return;
        }

        Map<Node, Integer> neighbours = currentNode.getAdjacentNodes();
        neighbours.forEach((node, distance) -> {
            if (unvisited.contains(node)) {
                int computeDistance = distance + currentNode.getDistance();
                if (computeDistance < node.getDistance()) {
                    node.setDistance(computeDistance);
                    node.setPrevNode(currentNode);
                }
            }
        });

        unvisited.remove(currentNode);
        visited.add(currentNode);

        Node nextNode = getSmallestDistanceNode(unvisited);
        dijkstraHelper(nextNode, end);
    }

    /**
     * Receives a node, and it backtracks through the previousNode till finding the start node (the one that doesn't have
     * a previous node, so previousNode == null).
     * @param end the node to backtrack
     * @return the path to get to that node, list of nodes.
     */
    private List<Node> getPath(Node end) {
        Node prevNode = end;
        List<Node> shortestPathToEnd = new ArrayList<>();
        shortestPathToEnd.add(end);
        while (prevNode.getPreviousNode() != null) {
            shortestPathToEnd.add(prevNode.getPreviousNode());
            prevNode = prevNode.getPreviousNode();
        }
        Collections.reverse(shortestPathToEnd);
        return shortestPathToEnd;
    }

    /**
     * Receives a list of nodes and returns the node with the smallest distance to the start point
     * @param nodes list of nodes
     * @return node with the smallest distance to the start point
     */
    private Node getSmallestDistanceNode(List<Node> nodes) {
        Node node = null;
        int distance = Integer.MAX_VALUE;
        for (Node d : nodes) {
            if (d.getDistance() < distance) {
                distance = d.getDistance();
                node = d;
            }
        }
        return node;
    }
}
