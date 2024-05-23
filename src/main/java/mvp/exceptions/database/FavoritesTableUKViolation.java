package mvp.exceptions.database;

import mvp.model.db.dto.StationsDto;

/**
 * Exception thrown when we violate Unique Key rule in our Favorites table
 */
public class FavoritesTableUKViolation extends Exception {
    private final StationsDto origin;
    private final StationsDto destination;

    public FavoritesTableUKViolation(String message, StationsDto origin, StationsDto destination) {
        super(message);
        this.origin = origin;
        this.destination = destination;
    }

    public StationsDto getOrigin() {
        return origin;
    }

    public StationsDto getDestination() {
        return destination;
    }
}
