package mvp.exceptions.model;

/**
 * Exception thrown when the destination is null and we try to save, or search a ride
 */
public class NullDestinationStation extends DijkstraException {
    public NullDestinationStation(String message) {
        super(message);
    }
}
