package mvp.model.db.tablepk;

import java.util.Objects;

public class StopsPK implements Key<StopsPK> {
    private final Integer id_line;
    private final Integer id_station;

    public StopsPK(Integer id_line, Integer id_station) {
        this.id_line = id_line;
        this.id_station = id_station;
    }

    public Integer getIdLine() {
        return id_line;
    }

    public Integer getIdStation() {
        return id_station;
    }

    @Override
    public int hashCode() {
        int multiplier = 100;
        int hash = multiplier * 7 + Objects.hashCode(this.getIdLine() + this.getIdStation());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        StopsPK pk = (StopsPK) o;
        return pk.getIdStation().equals(this.getIdStation()) &&
                pk.getIdLine().equals(this.getIdLine());
    }
}
