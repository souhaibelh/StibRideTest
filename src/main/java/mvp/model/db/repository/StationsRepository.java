package mvp.model.db.repository;
import mvp.model.db.dao.StationsDao;
import mvp.model.db.tablepk.StationsPK;
import mvp.model.db.dto.StationsDto;
import org.eclipse.aether.RepositoryException;

import java.util.List;

public class StationsRepository implements Repository<StationsPK, StationsDto> {
    private final StationsDao dao;

    public StationsRepository() {
        dao = new StationsDao();
    }

    public StationsRepository(StationsDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(StationsDto item) throws Exception {
        if (item == null || item.getIdStation() == null) {
            throw new RepositoryException("StationsDto null OR key of StationsDto null");
        }
        StationsDto dto = dao.select(new StationsPK(item.getIdStation()));
        if (dto == null) {
            dao.insert(item);
        } else {
            dao.update(item);
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
    public boolean contains(StationsPK key) throws RepositoryException {
        if (key == null || key.getIdStation() == null) {
            throw new RepositoryException("StationsPK is null");
        }
        StationsDto dto = get(key);
        return dto != null;
    }
}
