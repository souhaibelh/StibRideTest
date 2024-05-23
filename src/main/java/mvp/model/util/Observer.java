package mvp.model.util;

public interface Observer {
    /**
     * Receives a model event and an array with information concerning that event (if there is any)
     * @param update contains information about why the model is trying to notify us.
     */
    void update(ModelUpdate update);
}
