package pl.dziurdziak.rock.dao;

public interface Cluster<T extends Point> {

    void addPoint(T point);

    int size();

    Cluster<T> merge(Cluster<T> secondCluster);

}
