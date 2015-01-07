package pl.dziurdziak.rock.dao;

public interface NeighbourFunction<T extends Point<? super T>> {

    boolean apply(T first, T second);

}
