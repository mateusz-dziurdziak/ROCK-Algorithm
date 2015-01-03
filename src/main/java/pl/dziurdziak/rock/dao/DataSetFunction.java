package pl.dziurdziak.rock.dao;

public interface DataSetFunction<T extends Point> {

    double apply(double goodness);

}
