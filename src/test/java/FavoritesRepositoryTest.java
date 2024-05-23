import mvp.exceptions.database.FavoritesTablePKViolation;
import mvp.model.db.dao.FavoritesDao;
import mvp.model.db.dto.FavoritesDto;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.repository.FavoritesRepository;
import mvp.model.db.tablepk.FavoritesPK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FavoritesRepositoryTest {
    @Mock
    private FavoritesDao mock;

    @InjectMocks
    private FavoritesRepository repository;

    private FavoritesDto jacques_stockel;
    private FavoritesDto brouckere_elisabeth;
    private List<FavoritesDto> all;
    private final int JSKEY = 49;
    private final int BEKEY = 50;
    private FavoritesPK jacquesPk;

    public FavoritesRepositoryTest() {
        System.out.println("FAVORITESREPOSITORYTEST : CONSTRUCTOR : START");

        StationsDto origin = new StationsDto("JACQUES BREL", 8722);
        StationsDto destination = new StationsDto("STOCKEL", 8161);
        jacques_stockel = new FavoritesDto("js", origin, destination);
        jacques_stockel.setId(JSKEY);

        StationsDto origin2 = new StationsDto("DE BROUCKERE", 8012);
        StationsDto destination2 = new StationsDto("ELISABETH", 8472);
        brouckere_elisabeth = new FavoritesDto("be", origin2, destination2);
        brouckere_elisabeth.setId(BEKEY);

        all = new ArrayList<>();
        System.out.println("FAVORITESREPOSITORYTEST : CONSTRUCTOR : END");
    }

    @BeforeEach
    void init() {
        System.out.println("=== BEFORE EACH ===");
        jacquesPk = new FavoritesPK(jacques_stockel.getId());
        FavoritesPK brouckerePk = new FavoritesPK(brouckere_elisabeth.getId());

        // Mocking return values
        Mockito.lenient().when(mock.select(jacquesPk)).thenReturn(jacques_stockel);
        Mockito.lenient().when(mock.select(brouckerePk)).thenReturn(brouckere_elisabeth);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        // Mocking exception
        Mockito.lenient().when(mock.select(null)).thenThrow(new RuntimeException(new FavoritesTablePKViolation("Primary key violation")));
    }

    @Test
    public void testGetExists() throws Exception {
        System.out.println("METHOD TESTGETEXISTS START");

        FavoritesDto expected = jacques_stockel;
        FavoritesPK key = new FavoritesPK(JSKEY);
        FavoritesDto result = repository.get(key);

        System.out.println(expected);
        System.out.println(result);

        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(key);
        System.out.println("METHOD TESTGETEXISTS END");
    }
}
