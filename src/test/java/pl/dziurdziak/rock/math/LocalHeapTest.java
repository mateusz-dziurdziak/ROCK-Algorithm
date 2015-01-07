package pl.dziurdziak.rock.math;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.dziurdziak.rock.dao.Cluster;
import pl.dziurdziak.rock.dao.impl.GoodnessFunction;
import pl.dziurdziak.test.TestPoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class LocalHeapTest {

    @Mock
    private Cluster<TestPoint> heapCluster;

    @Mock
    private Links<TestPoint> links;

    @Mock
    private GoodnessFunction<TestPoint> goodnessFunction;

    private LocalHeap<TestPoint> localHeap;

    @Before
    public void setUp() {
        localHeap = new LocalHeap<>(heapCluster, links, goodnessFunction);
    }

    @Test
    public void testGetHeapCluster() {
        assertEquals(heapCluster, localHeap.getHeapCluster());
    }

    @Test
    public void testAdd() {
        when(links.getLinkCount(anyObject(), anyObject())).thenReturn(1);
        when(goodnessFunction.calculate(anyObject(), anyObject(), anyObject())).thenReturn(0.0);

        Cluster<TestPoint> first = mock(Cluster.class);
        Cluster<TestPoint> second = mock(Cluster.class);
        Cluster<TestPoint> third = mock(Cluster.class);
        localHeap.add(first);
        localHeap.add(second);
        localHeap.add(third);

        assertThat(localHeap.getAll()).hasSize(3)
                .contains(first, second, third);
    }

    @Test
    public void testOrderByFunction() {
        when(links.getLinkCount(anyObject(), anyObject())).thenReturn(1);
        when(goodnessFunction.calculate(anyObject(), anyObject(), anyObject())).thenReturn(0.0, -1.0, 1.0);

        Cluster<TestPoint> first = mock(Cluster.class);
        Cluster<TestPoint> second = mock(Cluster.class);
        Cluster<TestPoint> third = mock(Cluster.class);
        localHeap.add(first);
        localHeap.add(second);
        localHeap.add(third);

        assertThat(localHeap.getAll());
        assertEquals(third, localHeap.getMax());
        assertEquals(1.0, localHeap.getMaxValue(), 0.0);
    }

    @Test
    public void testDelete() {
        when(links.getLinkCount(anyObject(), anyObject())).thenReturn(1);
        when(goodnessFunction.calculate(anyObject(), anyObject(), anyObject())).thenReturn(0.0);

        Cluster<TestPoint> first = mock(Cluster.class);
        Cluster<TestPoint> second = mock(Cluster.class);
        Cluster<TestPoint> third = mock(Cluster.class);
        localHeap.add(first);
        localHeap.add(second);
        localHeap.add(third);

        localHeap.delete(second);

        assertThat(localHeap.getAll()).hasSize(2)
                .contains(first, third);
    }

    @Test
    public void testOrderAfterDelete() {
        when(links.getLinkCount(anyObject(), anyObject())).thenReturn(1);
        when(goodnessFunction.calculate(anyObject(), anyObject(), anyObject())).thenReturn(0.0, 2.0, 1.0);

        Cluster<TestPoint> first = mock(Cluster.class);
        Cluster<TestPoint> second = mock(Cluster.class);
        Cluster<TestPoint> third = mock(Cluster.class);
        localHeap.add(first);
        localHeap.add(second);
        localHeap.add(third);

        localHeap.delete(second);

        assertThat(localHeap.getAll());
        assertEquals(third, localHeap.getMax());
        assertEquals(1.0, localHeap.getMaxValue(), 0.0);
    }

}
