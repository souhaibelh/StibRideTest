package mvp.model.db.tablepk;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int multiplier = 57;
        int hash = multiplier * 7 + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        FavoritesPK pk = (FavoritesPK) object;
        return pk.getId().equals(this.getId());
    }
}
