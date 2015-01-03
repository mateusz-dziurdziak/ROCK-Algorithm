package pl.dziurdziak.rock.math;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.Point;

import java.util.List;

public class NeighbourMatrix<T extends Point<? super T>> {

    private List<T> points;
    private Matrix matrix;
    private Matrix secondPower;

    public NeighbourMatrix(List<T> points, NeighbourFunction<T> neighbourFunction) {
        this.points = points;
        this.matrix = createMatrix();
    }

    private Matrix createMatrix() {
        // TODO
        return null;
    }

    public List<T> getInitialPoints() {
        return this.points;
    }

    public Matrix secondPower() {
        if (secondPower == null) {
            secondPower = matrix.power(2);
        }
        return secondPower.copy();
    }

}
