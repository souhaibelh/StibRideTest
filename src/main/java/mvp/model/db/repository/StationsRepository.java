package mvp.model.db.repository;
import mvp.exceptions.arguments.NullStationDto;
import mvp.exceptions.database.StationsTablePKViolation;
import mvp.model.db.dao.StationsDao;
import mvp.model.db.tablepk.StationsPK;
import mvp.model.db.dto.StationsDto;
import java.util.List;

public class StationsRepository implements Repository<StationsPK, StationsDto> {
    private final StationsDao dao;

    public StationsRepository() {
        dao = new StationsDao();
    }

    @Override
    public void add(StationsDto item) throws Exception {
        if (item == null) {
            throw new NullStationDto("STATIONSREPOSITORY : ADD : NULL STATIONSDTO ITEM");
        }
        StationsPK key = new StationsPK(item.getIdStation());
        StationsDto dto = dao.select(new StationsPK(item.getIdStation()));
        if (contains(key)) {
            dao.update(dto);
        } else {
            dao.insert(dto);
        }
    }

    @Override
    public void delete(StationsPK key) {
        dao.delete(key);
    }

    @Override
    public StationsDto get(StationsPK key) {
        return dao.select(key);
    }

    @Override
    public List<StationsDto> getAll() {
        return dao.selectAll();
    }

    @Override
    public boolean contains(StationsPK key) {
        StationsDto dto = get(key);
        return dto != null;
    }
}
