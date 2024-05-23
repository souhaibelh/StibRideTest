package mvp.model.db.tablepk;

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
}
