package mvp.model.db.repository;
import mvp.model.db.tablepk.StopsPK;
import mvp.model.db.dao.StopsDao;
import mvp.model.db.dto.StopsDto;
import java.util.List;

public class StopsRepository implements Repository<StopsPK, StopsDto> {
    private final StopsDao dao;

    public StopsRepository() {
        dao = new StopsDao();
    }

    public StopsRepository(StopsDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(StopsDto item) throws Exception {
        StopsPK pk = new StopsPK(item.getIdLine(), item.getIdStation());
        StopsDto dto = dao.select(pk);
        if (dto == null) {
            dao.insert(item);
        } else {
            dao.update(item);
        }
    }

    @Override
    public void delete(StopsPK key) throws Exception {
        dao.delete(key);
    }

    @Override
    public StopsDto get(StopsPK key) {
        return dao.select(key);
    }

    @Override
    public List<StopsDto> getAll() {
        return dao.selectAll();
    }

    @Override
    public boolean contains(StopsPK key) {
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
