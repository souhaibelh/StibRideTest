package mvp.model.db.tablepk;

import java.util.Objects;

public class StationsPK implements Key<StationsPK> {
    private final Integer id_station;

    public StationsPK(Integer key) {
        this.id_station = key;
    }

    public Integer getIdStation() {
        return id_station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationsPK stationsPK = (StationsPK) o;
        return Objects.equals(this.id_station, stationsPK.id_station);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_station);
    }
}
