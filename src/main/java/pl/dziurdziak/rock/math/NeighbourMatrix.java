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

    public NeighbourMatrix(List<T> points, NeighbourFunction<T> neighbourFunction, double goodness) {
        this.points = points;
        this.matrix = createMatrix(neighbourFunction, goodness);
    }

    private Matrix createMatrix(NeighbourFunction<T> neighbourFunction, double goodness) {
        Matrix calculatedMatrix = new Basic2DMatrix(points.size(), points.size());
        for (int i=0; i<points.size(); i++) {
            for (int j=0; j<points.size(); j++) {
                if (i == j) {
                    calculatedMatrix.set(i, j, 0);
                } else {
                    calculatedMatrix.set(i, j, neighbourFunction.apply(points.get(i), points.get(j), goodness) ? 1.0 : 0.0);
                }
            }
        }
        return calculatedMatrix;
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
