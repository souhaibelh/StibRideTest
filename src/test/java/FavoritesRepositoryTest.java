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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class FavoritesRepositoryTest {
    @Mock
    private FavoritesDao mock;
    private FavoritesDto home;
    private FavoritesDto school;
    private FavoritesDto sport;

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
    }

    @BeforeEach
    void init() {
        Mockito.lenient().when(mock.select(new FavoritesPK(1))).thenReturn(home);
        Mockito.lenient().when(mock.select(new FavoritesPK(2))).thenReturn(school);
        Mockito.lenient().when(mock.select(new FavoritesPK(3))).thenReturn(sport);
    }

    @Test
    public void testSelect() {
        FavoritesRepository rep = new FavoritesRepository(mock);

        assertEquals(home, rep.get(new FavoritesPK(1)));
        Mockito.verify(mock, Mockito.times(1)).select(new FavoritesPK(1));
    }
}
