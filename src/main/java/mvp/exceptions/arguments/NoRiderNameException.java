package mvp.exceptions.arguments;

/**
 * Exception thrown when no name is given to the ride we want to save
 */
public class NoRiderNameException extends IllegalArgumentException {
    public NoRiderNameException(String message) {
        super(message);
    }
}
