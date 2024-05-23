package Repository;
import mvp.model.db.dao.StopsDao;
import mvp.model.db.dto.StopsDto;
import mvp.model.db.repository.StopsRepository;
import mvp.model.db.tablepk.StopsPK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopsRepositoryTest {
    @Mock
    private StopsDao mock;

    private StopsDto jacquesBrel;
    private StopsDto etangsNoir;
    private List<StopsDto> dtoList;

    public StopsRepositoryTest() {
        jacquesBrel = new StopsDto(
            5, 8722, 9
        );

        etangsNoir = new StopsDto(
            1, 8292, 3
        );

        dtoList = new ArrayList<>();
        dtoList.add(new StopsDto(1,8012,6));
        dtoList.add(new StopsDto(5,8012,15));
    }

    @BeforeEach
    void init() {
        StopsPK pkJb = new StopsPK(5, 8722);
        Mockito.lenient().when(mock.select(pkJb)).thenReturn(jacquesBrel);

        StopsPK pkEn = new StopsPK(1, 8292);
        Mockito.lenient().when(mock.select(pkEn)).thenReturn(etangsNoir);

        StopsDto dto = new StopsDto(
                5, 8722, 9
        );
        Mockito.lenient().when(mock.selectByLineOrder(5, 9)).thenReturn(dto);
        Mockito.lenient().when(mock.selectByStation(8012)).thenReturn(dtoList);
    }

    @Test
    public void testSelect() {
        StopsRepository rep = new StopsRepository(mock);
        StopsPK pk = new StopsPK(5, 8722);
        StopsDto dto = rep.get(pk);

        assertEquals(jacquesBrel, dto);
        Mockito.verify(mock, Mockito.times(1)).select(pk);

    }

    @Test
    public void testSelectAll() {
        StopsRepository rep = new StopsRepository(mock);
        rep.getAll();

        Mockito.verify(mock, Mockito.times(1)).selectAll();
    }

    @Test
    public void testAdd() throws Exception {
        StopsRepository rep = new StopsRepository(mock);
        StopsDto dto = new StopsDto(
                2, 8764, 1
        );
        rep.add(dto);

        Mockito.verify(mock, Mockito.times(1)).insert(dto);

        StopsDto dtoOn = new StopsDto(
                5, 8722, 9
        );
        rep.add(dtoOn);

        Mockito.verify(mock, Mockito.times(1)).update(dtoOn);
    }

    @Test
    public void testRemove() throws Exception {
        StopsRepository rep = new StopsRepository(mock);
        StopsPK pk = new StopsPK(5, 8722);
        rep.delete(pk);

        Mockito.verify(mock, Mockito.times(1)).delete(pk);
    }

    @Test
    public void testSelectByLineOrder() {
        StopsRepository rep = new StopsRepository(mock);
        rep.selectByLineOrder(5, 9);

        assertEquals(jacquesBrel, rep.selectByLineOrder(5, 9));
        Mockito.verify(mock, Mockito.times(2)).selectByLineOrder(5, 9);
    }

    @Test
    public void testSelectByStation() {
        StopsRepository rep = new StopsRepository(mock);
        List<StopsDto> expected = rep.selectByStation(8012);

        assertEquals(expected, dtoList);
        Mockito.verify(mock, Mockito.times(1)).selectByStation(8012);
    }

    @Test
    public void testContains() {
        StopsRepository rep = new StopsRepository(mock);
        StopsPK pk = new StopsPK(5, 8722);
        boolean contains = rep.contains(pk);

        assertTrue(contains);
        Mockito.verify(mock, Mockito.times(1)).select(pk);
    }
}
