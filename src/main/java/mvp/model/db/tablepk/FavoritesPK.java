package mvp.model.db.tablepk;

public class FavoritesPK implements Key<FavoritesPK> {
    private Integer id;

    public FavoritesPK(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
