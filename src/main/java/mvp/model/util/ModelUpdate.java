package mvp.model.util;

/**
 * Class the model uses to store information and update the presenter with it
 */
public class ModelUpdate {
    private final ModelEvent type;
    private final Object[] information;

    /**
     * Type of event, information is null if we have any we must set it with setter
     * @param type type of the event: save_ride_success...
     */
    public ModelUpdate(ModelEvent type) {
        this.type = type;
        information = null;
    }

    /**
     *  Constructor that takes in both a type and an array of information
     * @param type type of the event
     * @param information information of the event
     */
    public ModelUpdate(ModelEvent type, Object[] information) {
        this.type = type;
        this.information = information;
    }

    public ModelEvent getType() {
        return this.type;
    }

    public Object[] getInformation() {
        return this.information;
    }
}
