package pl.dziurdziak.rock.dao;

/**
 * Funkcja określająca czy dwa punkty są "sąsiadami"
 * @param <T> typ punktu
 */
public interface NeighbourFunction<T extends Point<? super T>> {

    boolean apply(T first, T second);

}
