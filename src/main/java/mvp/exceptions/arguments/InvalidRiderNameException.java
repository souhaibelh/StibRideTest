package mvp.exceptions.arguments;

/**
 * Exception thrown when the name of the ride to save is invalid
 */
public class InvalidRiderNameException extends IllegalArgumentException {
    public InvalidRiderNameException(String message) {
        super(message);
    }
}
