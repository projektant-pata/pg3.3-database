package cz.spse.bajer.data.interfaces;

import java.util.HashMap;

public interface IDataSource<T> {
    T create(T obj);
    T read(int id);
    boolean update(int id, T obj);
    boolean delete(int id);
    HashMap<Integer, T> readAll();
}
