package mvp.model.db.dto;

import mvp.model.db.tablepk.StopsPK;

/**
 * Class that represents a row in the STOPS table
 */
public class StopsDto extends StopsPK {
    private final int id_order;

    /**
     * Takes in an id line and an id station and an id order, and creates the instance
     * @param id_line
     * @param id_station
     * @param id_order
     */
    public StopsDto(int id_line, int id_station, int id_order) {
        super(id_line, id_station);
        this.id_order = id_order;
    }

    public int getOrderId() {
        return id_order;
    }
}
