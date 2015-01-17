package pl.dziurdziak.rock.dao.impl;

import com.google.common.collect.ImmutableList;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * DAO przechowujące punkty w pamięci
 * @param <T> typ punktu
 */
public abstract class AbstractMemoryPointDao<T extends Point<? super T>> implements PointDao<T> {

    /**
     * Lista wszystkich punktów
     */
    private List<T> points;

    /**
     * Konstruktor tworzący DAO poprzez wczytanie danych z pliku.
     * @param file plik z danymi
     */
    public AbstractMemoryPointDao(File file) {
        try {
            points = ImmutableList.copyOf(readPoints(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Odczytuje listę punktów z pliku
     *
     * @param file plik
     * @return lista punktów
     * @throws IOException
     */
    protected abstract List<T> readPoints(File file) throws IOException;

    @Override
    public long count() {
        return points.size();
    }

    @Override
    public List<T> getRandomPoints(int numberOfPoints) {
        checkArgument(numberOfPoints <= points.size());
        Random random = new Random();

        Set<Integer> ids = new HashSet<>(Math.min(numberOfPoints, points.size() - numberOfPoints));
        while(ids.size() < Math.min(numberOfPoints, points.size() - numberOfPoints)) {
            ids.add(random.nextInt(points.size()));
        }

        int i = 0;
        List<T> returnPoints = new ArrayList<>(numberOfPoints);
        for (T point : points) {
            if (numberOfPoints >= points.size() / 2
                    && !ids.contains(i)) {
                returnPoints.add(point);
            } else if (numberOfPoints < points.size() / 2
                    && ids.contains(i)){
                returnPoints.add(point);
            }
            i++;
        }
        return returnPoints;
    }

    @Override
    public List<T> getAllPoints() {
        return ImmutableList.copyOf(points);
    }

}
