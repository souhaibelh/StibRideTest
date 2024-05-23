package mvp.model.util;

/**
 * Observable interface
 */
public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(ModelUpdate update);
}
