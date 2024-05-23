package Repository;
import mvp.model.db.dao.StationsDao;
import mvp.model.db.dto.StationsDto;
import mvp.model.db.repository.StationsRepository;
import mvp.model.db.tablepk.StationsPK;
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
public class StationsRepositoryTest {
    @Mock
    private StationsDao mock;

    @InjectMocks
    private StationsRepository repo;

    private StationsDto gareCentrale;
    private StationsDto deBrouckere;
    private StationsDto artsLoi;
    private List<StationsDto> dtoList;

    public StationsRepositoryTest() {
        gareCentrale = new StationsDto("GARE CENTRAL", 8022);
        deBrouckere = new StationsDto("DE BROUCKERE", 8012);
        artsLoi = new StationsDto("ARTS-LOI", 8042);

        dtoList = new ArrayList<>();
        dtoList.add(gareCentrale);
        dtoList.add(deBrouckere);
        dtoList.add(artsLoi);
    }

    @BeforeEach
    void init() {
        repo = new StationsRepository(mock);
        Mockito.lenient().when(mock.selectAll()).thenReturn(dtoList);

        StationsPK pkBk = new StationsPK(8012);
        StationsPK pkGc = new StationsPK(8022);
        StationsPK pkAl = new StationsPK(8042);
        Mockito.lenient().when(mock.select(pkBk)).thenReturn(deBrouckere);
        Mockito.lenient().when(mock.select(pkGc)).thenReturn(gareCentrale);
        Mockito.lenient().when(mock.select(pkAl)).thenReturn(artsLoi);
    }

    @Test
    public void testSelect() {
        assertEquals(deBrouckere, repo.get(new StationsPK(8012)));
        Mockito.verify(mock, Mockito.times(1)).select(new StationsPK(8012));
    }

    @Test
    public void testSelectAll() {
        List<StationsDto> stationsList = repo.getAll();
        assertEquals(dtoList, stationsList);
        Mockito.verify(mock, Mockito.times(1)).selectAll();
    }

    @Test
    public void testUpdate() throws Exception {
        repo.add(gareCentrale);
        Mockito.verify(mock, Mockito.times(1)).update(gareCentrale);
    }

    @Test
    public void testAdd() throws Exception {
        StationsDto dto = new StationsDto("RANDOM", 1222);
        repo.add(dto);
        Mockito.verify(mock, Mockito.times(1)).insert(dto);
    }

    @Test
    public void testDelete() {
        StationsPK pk = new StationsPK(8012);
        repo.delete(pk);

        Mockito.verify(mock, Mockito.times(1)).delete(pk);
    }
}
