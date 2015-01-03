package pl.dziurdziak.rock.dao.impl;

import pl.dziurdziak.rock.dao.Point;
import pl.dziurdziak.rock.dao.PointDao;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractMemoryPointDao<T extends Point<? super T>> implements PointDao<T> {

    private List<T> points;

    public AbstractMemoryPointDao(File file) {
        try {
            points = readPoints(file);
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
        //TODO
        return null;
    }
}
