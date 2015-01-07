package pl.dziurdziak.rock.dao;

import java.util.List;

public interface Cluster<T extends Point<? super T>> {

    /**
     * @return liczba punktów w grupie
     */
    int size();

    /**
     * @return wszystkiep punkty w grupie
     */
    List<T> getPoints();

    /**
     * Łączy grupę z inną zwracając nową (nie narusza istniejącej)
     *
     * @param secondCluster druga grupa
     * @return nowa grupa będąca połączenie istniejącej oraz przekazanej
     */
    Cluster<T> merge(Cluster<T> secondCluster);

}
