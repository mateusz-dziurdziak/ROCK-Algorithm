package pl.dziurdziak.rock.dao;

import java.util.List;

public interface PointDao<T extends Point<? super T>> {

    long count();

    List<T> getRandomPoints(int numberOfPoints);

    List<T> getAllPoints();

}
