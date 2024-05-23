package Repository;

import mvp.exceptions.database.FavoritesTableUKViolation;
import mvp.model.db.dao.FavoritesDao;
import mvp.model.db.dto.FavoritesDto;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.repository.FavoritesRepository;
import mvp.model.db.tablepk.FavoritesPK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class FavoritesRepositoryTest {
    @Mock
    private FavoritesDao mock;
    private FavoritesDto home;
    private FavoritesDto school;
    private FavoritesDto sport;

    private List<FavoritesDto> all;

    @InjectMocks
    private FavoritesRepository repository;

    public FavoritesRepositoryTest() {
        home = new FavoritesDto(
                "home",
                new StationsDto("GARE DE L'OUEST", 8382),
                new StationsDto("COMTE DE FLANDRE", 8282)
        );
        home.setId(1);

        school = new FavoritesDto(
                "school",
                new StationsDto("COMTE DE FLANDRE", 8282),
                new StationsDto("PARC", 8032)
        );
        school.setId(2);

        sport = new FavoritesDto(
                "sport",
                new StationsDto("COMTE DE FLANDRE", 8282),
                new StationsDto("DELACROIX", 8372)
        );
        sport.setId(3);

        all = new ArrayList<>();
    }

    @BeforeEach
    void init() {
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(new FavoritesPK(1))).thenReturn(home);
        Mockito.lenient().when(mock.select(new FavoritesPK(2))).thenReturn(school);
        Mockito.lenient().when(mock.select(new FavoritesPK(3))).thenReturn(sport);
    }

    @Test
    public void testSelect() {
        FavoritesRepository rep = new FavoritesRepository(mock);

        all = new ArrayList<>();
        assertEquals(home, rep.get(new FavoritesPK(1)));
        Mockito.verify(mock, Mockito.times(1)).select(new FavoritesPK(1));
    }

    @Test
    public void testSelectAll() {
        FavoritesRepository rep = new FavoritesRepository(mock);

        assertEquals(all, rep.getAll());
        Mockito.verify(mock, Mockito.times(1)).selectAll();
    }

    @Test
    public void testUpdate() throws FavoritesTableUKViolation {
        FavoritesRepository rep = new FavoritesRepository(mock);
        FavoritesDto dto = new FavoritesDto(
                "souhaib",
                new StationsDto("VEEWEYDE", 8692),
                new StationsDto("PORTE DE HAL", 8342)
        );
        dto.setId(2);
        all.add(dto);

        int size = rep.getAll().size();

        FavoritesDto dtoUpdate = new FavoritesDto(
                "souhaibhassouni",
                new StationsDto("DELTA", 8232),
                new StationsDto("MERODE", 8072)
        );
        dtoUpdate.setId(2);
        rep.add(dtoUpdate);

        assertEquals(size, rep.getAll().size());
        Mockito.verify(mock, Mockito.times(1)).update(dtoUpdate);
    }

    @Test
    public void testInsert() throws FavoritesTableUKViolation {
        FavoritesRepository rep = new FavoritesRepository(mock);
        int size = all.size();

        FavoritesDto dto = new FavoritesDto(
                "random",
                new StationsDto("VANDERVELDE", 8132),
                new StationsDto("PORTE DE NAMUR", 8312)
        );
        all.add(dto);
        rep.add(dto);

        assertEquals(size + 1, all.size());
        Mockito.verify(mock, Mockito.times(1)).insert(dto);
    }

    @Test
    public void testRemove() {
        FavoritesRepository rep = new FavoritesRepository(mock);
        FavoritesPK pk = new FavoritesPK(1);
        rep.delete(pk);

        Mockito.verify(mock, Mockito.times(1)).delete(pk);
    }

    @Test
    public void updateAll() throws FavoritesTableUKViolation {
        FavoritesRepository rep = new FavoritesRepository(mock);

        FavoritesDto dto = new FavoritesDto(
                "home",
                new StationsDto("DE BROUCKERE", 8012),
                new StationsDto("VANDERVELD", 8132)
        );
        dto.setId(1);

        List<FavoritesDto> addList = new ArrayList<>();
        addList.add(dto);
        rep.addAll(addList);

        Mockito.verify(mock, Mockito.times(1)).updateAll(addList);
    }

    @Test
    public void insertAll() throws FavoritesTableUKViolation {
        FavoritesRepository rep = new FavoritesRepository(mock);

        FavoritesDto dto = new FavoritesDto(
                "asd",
                new StationsDto("PETILLON", 8212),
                new StationsDto("HANKAR", 8222)
        );
        dto.setId(4);
        List<FavoritesDto> all = new ArrayList<>();
        all.add(dto);

        rep.addAll(all);
        Mockito.verify(mock, Mockito.times(1)).insertAll(all);
    }
}
