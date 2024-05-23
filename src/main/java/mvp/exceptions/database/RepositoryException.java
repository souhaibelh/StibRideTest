package mvp.exceptions.database;

/**
 * Exception thrown when there is a problem with our repository
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(String error) {
        super(error);
    }
}
