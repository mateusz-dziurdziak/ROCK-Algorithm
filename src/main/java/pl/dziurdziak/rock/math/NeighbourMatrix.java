package pl.dziurdziak.rock.math;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import pl.dziurdziak.rock.dao.NeighbourFunction;
import pl.dziurdziak.rock.dao.Point;

import java.util.List;

/**
 * Macierz sasiedztwa pomiędzy punktami
 *
 * @param <T> typ puntów
 */
public class NeighbourMatrix<T extends Point<? super T>> {

    /**
     * Lista punktów
     */
    private List<T> initialPoints;

    /**
     * Macierz sąsiedztwa punktów
     */
    private Matrix matrix;

    /**
     * Druga potęga macierzy sąsiedztwa punktów
     */
    private Matrix secondPower;

    /**
     * Tworzy macierz sąsiedztwa punktów
     *
     * @param points            {@link #initialPoints}
     * @param neighbourFunction funkcja sąsiedztwa
     */
    public NeighbourMatrix(List<T> points, NeighbourFunction<T> neighbourFunction) {
        this.initialPoints = points;
        this.matrix = createMatrix(points, neighbourFunction);
    }

    /**
     * Tworzy macierz na podstawie listy punktów i funkcji sąsiedztwa
     *
     * @param points            lista punktów
     * @param neighbourFunction funkcja sąsiedztwa
     * @return macierz sąsiedztwa
     */
    private Matrix createMatrix(List<T> points, NeighbourFunction<T> neighbourFunction) {
        Matrix calculatedMatrix = new Basic2DMatrix(points.size(), points.size());
        for (int i = 0; i < points.size(); i++) {
            for (int j = i; j < points.size(); j++) {
                if (i == j) {
                    calculatedMatrix.set(i, j, 0);
                } else {
                    calculatedMatrix.set(i, j, neighbourFunction.apply(points.get(i), points.get(j)) ? 1.0 : 0.0);
                }
            }
        }

        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < i; j++) {
                calculatedMatrix.set(i, j, calculatedMatrix.get(j, i));
            }
        }

        return calculatedMatrix;
    }

    /**
     * @return lista punktów, która została wykorzystana do obliczenia macierzy
     */
    public List<T> getInitialPoints() {
        return this.initialPoints;
    }

    /**
     * @return {@link #matrix}
     */
    public Matrix getMatrix() {
        return matrix.copy();
    }

    /**
     * @return {@link #secondPower}
     */
    public Matrix secondPower() {
        if (secondPower == null) {
            secondPower = matrix.power(2);
        }
        return secondPower.copy();
    }

}
