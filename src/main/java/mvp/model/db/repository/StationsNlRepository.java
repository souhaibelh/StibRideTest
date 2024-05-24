package mvp.model.db.repository;
import mvp.model.db.dao.StationsNlDao;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.tablepk.StationsPK;
import java.util.List;

public class StationsNlRepository implements Repository<StationsPK, StationsDto> {
    private final StationsNlDao dao;

    public StationsNlRepository() {
        this.dao = new StationsNlDao();
    }

    @Override
    public void add(StationsDto item) {

    }

    @Override
    public void delete(StationsPK key) {

    }

    @Override
    public StationsPK get(StationsPK key) {
        return dao.select(key);
    }

    @Override
    public List<StationsDto> getAll() {
        return dao.selectAll();
    }

    @Override
    public boolean contains(StationsPK key) {
        return false;
    }
}
