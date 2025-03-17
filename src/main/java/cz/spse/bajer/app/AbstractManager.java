package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.IObjectManager;
import cz.spse.bajer.data.interfaces.IDataSource;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractManager<T> implements IObjectManager<T> {
    protected final IDataSource<T> dataSource;
    protected HashMap<Integer, T> items;

    public AbstractManager(IDataSource<T> dataSource) {
        this.dataSource = dataSource;
        this.items = dataSource.readAll();
    }

    @Override
    public T create(T obj) {
        T result = dataSource.create(obj);
        if (result != null) {
            items.put(getId(result), result);
            return result;
        } else {
            return null;
        }
    }

    @Override
    public T read(int id) {
        return items.get(id);
    }

    @Override
    public boolean update(int id, T obj) {
        if (dataSource.update(id, obj)) {
            items.put(id, obj);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T delete(int id) {
        if (dataSource.delete(id)) {
            return items.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<T> readAll() {
        return new ArrayList<>(items.values());
    }

    protected abstract int getId(T obj);


}