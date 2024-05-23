package mvp.util;

/**
 * Class that represents the events that model can use to contact with presenter.
 */
public enum ModelEvent {
    /**
     * Event to indicate that the shortest path was successfully connected
     */
    SHORTEST_PATH_CALCULATED,

    /**
     * Event to indicate that favorite ride was successfully deleted
     */
    DELETE_FAVORITE_RIDE_SUCCESS,

    /**
     * Event to indicate that we were successful to save the changes made to the favorites table
     */
    SAVE_FAVORITE_CHANGES_SUCCESS,

    FAVORITES_TABLE_UK_VIOLATION,
    /**
     * Event to indicate that a ride was saved successfully
     */
    SAVE_RIDE_SUCCESS
}
