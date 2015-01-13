package pl.dziurdziak.rock.dao;

import java.util.List;

/**
 * Interfejs opisujący DAO punktów czyli obiekt odpowiadający za pobieranie punktów podlegających przetwarzaniu
 * w algorytmie
 *
 * @param <T> typ punku
 */
public interface PointDao<T extends Point<? super T>> {

    /**
     * @return liczba wszystkich punktów
     */
    long count();

    /**
     * Zwraca listę zawierająca zadaną liczbę losowo wybranych punktów
     *
     * @param numberOfPoints liczba punktów do wyboru
     * @return lista zawierająco losowo wybrane punkty
     */
    List<T> getRandomPoints(int numberOfPoints);

    /**
     * @return zwraca wszystkie punky
     */
    List<T> getAllPoints();

}
