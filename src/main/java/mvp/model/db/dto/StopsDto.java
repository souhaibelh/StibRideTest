package mvp.model.db.dto;

import mvp.model.db.tablepk.StopsPK;

public class StopsDto extends StopsPK {
    private final int id_order;

    public StopsDto(int id_line, int id_station, int id_order) {
        super(id_line, id_station);
        this.id_order = id_order;
    }

    public int getOrderId() {
        return id_order;
    }
}
