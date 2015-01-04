package pl.dziurdziak.rock.dao;

public interface DataSetFunction<T extends Point<? super T>> {

    double apply(double goodness);

}
