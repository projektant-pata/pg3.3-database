package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.IPricedObjectManager;
import cz.spse.bajer.data.interfaces.IDataSource;

import java.util.ArrayList;

public abstract class AbstractPricedManager<T> extends AbstractManager<T> implements IPricedObjectManager<T> {

    public AbstractPricedManager(IDataSource<T> dataSource) {
        super(dataSource);
    }

    @Override
    public ArrayList<T> readAllByCategory(int categoryId) {
        ArrayList<T> result = new ArrayList<>();
        for (T item : items.values()) {
            if (getCategoryId(item) == categoryId) {
                result.add(item);
            }
        }
        return result;
    }

    protected abstract int getCategoryId(T item);
}