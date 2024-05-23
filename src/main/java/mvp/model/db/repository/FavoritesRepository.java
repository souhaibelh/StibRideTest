package mvp.model.db.repository;
import mvp.model.db.dao.FavoritesDao;
import mvp.model.db.dto.FavoritesDto;
import mvp.exceptions.database.FavoritesTableUKViolation;
import mvp.model.db.tablepk.FavoritesPK;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository implements Repository<FavoritesPK, FavoritesDto> {
    private final FavoritesDao dao;

    public FavoritesRepository() {
        dao = new FavoritesDao();
    }

    public FavoritesRepository(FavoritesDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(FavoritesDto item) throws FavoritesTableUKViolation {
        FavoritesDto dto = dao.select(new FavoritesPK(item.getId()));
        if (dto == null) {
            dao.insert(item);
        } else {
            dao.update(item);
        }
    }

    @Override
    public void delete(FavoritesPK key) {
        dao.delete(key);
    }

    @Override
    public FavoritesDto get(FavoritesPK key) {
        return dao.select(key);
    }

    @Override
    public List<FavoritesDto> getAll() {
        return dao.selectAll();
    }

    @Override
    public boolean contains(FavoritesPK key) {
        FavoritesDto dto = dao.select(key);
        return dto != null;
    }

    public Object[] addAll(List<FavoritesDto> favDtoList) throws FavoritesTableUKViolation {
        List<FavoritesDto> updateList = new ArrayList<>();
        List<FavoritesDto> addList = new ArrayList<>();
        favDtoList.forEach((favDto) -> {
            FavoritesDto dto = dao.select(new FavoritesPK(favDto.getId()));
            if (dto == null) {
                addList.add(favDto);
            } else {
                updateList.add(favDto);
            }
        });
        int updateCount = dao.updateAll(updateList);
        int insertCount = dao.insertAll(addList);
        return new Object[]{updateCount, insertCount};
    }
}
