package cz.spse.bajer.app.interfaces;

import cz.spse.bajer.object.Category;

import java.util.ArrayList;
import java.util.HashMap;

public interface IObjectManager<T> {
    T create(T obj);
    T read(int id);
    boolean update(int id, T obj);
    T delete(int id);

    ArrayList<T> readAll();

}
