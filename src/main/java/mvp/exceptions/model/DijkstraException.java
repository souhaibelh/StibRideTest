package mvp.exceptions.model;

/**
 * Exception thrown when there is an error in the djikstra algorithm
 */
public class DijkstraException extends Exception {
    public DijkstraException(String message) {
        super(message);
    }
}
