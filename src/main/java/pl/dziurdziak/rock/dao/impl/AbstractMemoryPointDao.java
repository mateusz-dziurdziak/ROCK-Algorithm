package pl.dziurdziak.rock.dao.impl;

import com.google.common.collect.ImmutableList;
import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.PointDao;
import pl.dziurdziak.rock.dao.impl.mushroom.MushroomPoint;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 *
 * @param <T>
 */
public abstract class AbstractMemoryPointDao<T extends Point<? super T>> implements PointDao<T> {

    private List<T> points;

    public AbstractMemoryPointDao(File file) {
        try {
            points = ImmutableList.copyOf(readPoints(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<T> readPoints(File file) throws IOException;

    @Override
    public long count() {
        return points.size();
    }

    @Override
    public List<T> getRandomPoints(int numberOfPoints) {
        checkArgument(numberOfPoints <= points.size());
        Random random = new Random();

        Set<Integer> ids = new HashSet<>(numberOfPoints);
        while(ids.size() < numberOfPoints) {
            ids.add(random.nextInt(points.size()));
        }

        return ids.stream().map(id -> points.get(id)).collect(Collectors.toList());
    }

    @Override
    public List<T> getAllPoints() {
        return ImmutableList.copyOf(points);
    }

}
