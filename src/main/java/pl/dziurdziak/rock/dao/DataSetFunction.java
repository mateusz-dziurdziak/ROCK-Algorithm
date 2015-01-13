package pl.dziurdziak.rock.dao;

/**
 * Funkcja wykorzystywana w procesie obliczania przewidywanej liczby sąsiadów grupy
 * @param <T> typ punktu
 */
public interface DataSetFunction<T extends Point<? super T>> {

    double apply(double goodness);

}
