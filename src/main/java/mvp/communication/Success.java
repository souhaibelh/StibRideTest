package mvp.communication;

/**
 * Stores all the possible success messages to display to the user to inform about an success
 */
public enum Success {
    CHANGES_SAVED_SUCCESS("Successfully saved changes"),
    SHORTEST_PATH_SUCCESS("Shortest path calculated"),
    RIDE_SAVED_SUCCESS("Successfully saved ride"),
    RIDE_DELETED_SUCCESS("Successfully deleted ride");

    private String message;

    Success(String message) {
        this.message = message;
    }

    public String getSuccessText() {
        return message;
    }
}
