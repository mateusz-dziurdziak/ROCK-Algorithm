package pl.dziurdziak.rock.math;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.la4j.matrix.Matrix;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.test.TestPoint;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NeighbourMatrixTest {

    @Test
    public void testCreate() {
        TestPoint first = new TestPoint();
        TestPoint second = new TestPoint();
        TestPoint third = new TestPoint();
        List<TestPoint> testPoints = Lists.newArrayList(first, second, third);

        NeighbourFunction<TestPoint> neighbourFunction = mock(NeighbourFunction.class);
        when(neighbourFunction.apply(first, second)).thenReturn(true);
        when(neighbourFunction.apply(first, third)).thenReturn(false);
        when(neighbourFunction.apply(second, first)).thenReturn(true);
        when(neighbourFunction.apply(second, third)).thenReturn(true);
        when(neighbourFunction.apply(third, first)).thenReturn(false);
        when(neighbourFunction.apply(third, second)).thenReturn(true);

        NeighbourMatrix<TestPoint> neighbourMatrix = new NeighbourMatrix<>(testPoints, neighbourFunction, 0.0);
        Matrix matrix = neighbourMatrix.getMatrix();

        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(0.0, matrix.get(0, 0), 0.0);
        assertEquals(0.0, matrix.get(1, 1), 0.0);
        assertEquals(0.0, matrix.get(2, 2), 0.0);

        assertEquals(1.0, matrix.get(0, 1), 0.0);
        assertEquals(0.0, matrix.get(0, 2), 0.0);
        assertEquals(1.0, matrix.get(1, 0), 0.0);
        assertEquals(1.0, matrix.get(1, 2), 0.0);
        assertEquals(0.0, matrix.get(2, 0), 0.0);
        assertEquals(1.0, matrix.get(2, 1), 0.0);
    }

    @Test
    public void testPower() {
        TestPoint first = new TestPoint();
        TestPoint second = new TestPoint();
        TestPoint third = new TestPoint();
        TestPoint fourth = new TestPoint();
        List<TestPoint> testPoints = Lists.newArrayList(first, second, third, fourth);

        NeighbourFunction<TestPoint> neighbourFunction = mock(NeighbourFunction.class);
        when(neighbourFunction.apply(first, second)).thenReturn(true);
        when(neighbourFunction.apply(first, third)).thenReturn(false);
        when(neighbourFunction.apply(first, fourth)).thenReturn(true);
        when(neighbourFunction.apply(second, first)).thenReturn(true);
        when(neighbourFunction.apply(second, third)).thenReturn(true);
        when(neighbourFunction.apply(second, fourth)).thenReturn(true);
        when(neighbourFunction.apply(third, first)).thenReturn(false);
        when(neighbourFunction.apply(third, second)).thenReturn(true);
        when(neighbourFunction.apply(third, fourth)).thenReturn(true);
        when(neighbourFunction.apply(fourth, first)).thenReturn(true);
        when(neighbourFunction.apply(fourth, second)).thenReturn(true);
        when(neighbourFunction.apply(fourth, third)).thenReturn(true);

        NeighbourMatrix<TestPoint> neighbourMatrix = new NeighbourMatrix<>(testPoints, neighbourFunction, 0.0);
        Matrix secondPower = neighbourMatrix.secondPower();

        assertEquals(4, secondPower.rows());
        assertEquals(4, secondPower.columns());

        assertEquals(2.0, secondPower.get(0, 0), 0.0);
        assertEquals(1.0, secondPower.get(0, 1), 0.0);
        assertEquals(2.0, secondPower.get(0, 2), 0.0);
        assertEquals(1.0, secondPower.get(0, 3), 0.0);
        assertEquals(2.0, secondPower.get(1, 3), 0.0);
        assertEquals(1.0, secondPower.get(2, 3), 0.0);
    }

}
