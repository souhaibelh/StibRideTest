package mvp.model.db.repository;
import mvp.exceptions.arguments.NullStopsDto;
import mvp.exceptions.database.StopsTablePKViolation;
import mvp.model.db.tablepk.StopsPK;
import mvp.model.db.dao.StopsDao;
import mvp.model.db.dto.StopsDto;
import java.util.List;

public class StopsRepository implements Repository<StopsPK, StopsDto> {
    private final StopsDao dao;

    public StopsRepository() {
        dao = new StopsDao();
    }

    @Override
    public void add(StopsDto item) throws Exception {
        if (item == null) {
            throw new NullStopsDto("STOPSREPOSITORY : ADD : NULL STOPSDTO");
        }
        StopsPK pk = new StopsPK(item.getIdLine(), item.getIdStation());
        StopsDto dto = dao.select(pk);
        if (contains(pk)) {
            dao.update(dto);
        } else {
            dao.insert(dto);
        }
    }

    @Override
    public void delete(StopsPK key) throws Exception {
        dao.delete(key);
    }

    @Override
    public StopsDto get(StopsPK key) throws Exception {
        return dao.select(key);
    }

    @Override
    public List<StopsDto> getAll() {
        return dao.selectAll();
    }

    @Override
    public boolean contains(StopsPK key) throws Exception {
        StopsDto dto = get(key);
        return dto != null;
    }

    public StopsDto selectByLineOrder(Integer idLine, Integer idOrder) {
        return dao.selectByLineOrder(idLine, idOrder);
    }

    public List<StopsDto> selectByStation(Integer idStation) {
        return dao.selectByStation(idStation);
    }
}
