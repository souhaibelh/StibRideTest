package mvp.exceptions.model;

/**
 * Exception thrown when the origin is null and we try to save, or search a ride
 */
public class NullOriginStation extends DijkstraException {
    public NullOriginStation(String message) {
        super(message);
    }
}
