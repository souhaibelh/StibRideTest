package mvp.communication;

public enum Errors {
    NO_NAME_TEXT("Please input a rider name"),
    INVALID_NAME_TEXT("Invalid rider name, valid examples: 123r, 456RidEr789, rider..."),
    NULL_ORIGIN_STATION("Please select an origin"),
    NULL_DESTINATION_STATION("Please select a destination"),
    FAVORITES_TABLE_UK_VIOLATION("There is already a saved favorites ride with (origin, destination):");

    private final String error;

    Errors(String error) {
        this.error = error;
    }

    public String getErrorText() {
        return error;
    }
}
