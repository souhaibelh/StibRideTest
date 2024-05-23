package mvp.util;

public class ModelUpdate {
    private final ModelEvent type;
    private final Object[] information;

    public ModelUpdate(ModelEvent type) {
        this.type = type;
        information = null;
    }

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
