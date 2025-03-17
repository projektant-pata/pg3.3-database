package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.ICategoryManager;
import cz.spse.bajer.data.interfaces.ICategoryDS;
import cz.spse.bajer.object.Category;

public class CategoryManager extends AbstractManager<Category> implements ICategoryManager {

    public CategoryManager(ICategoryDS categoryDS) {
        super(categoryDS);
    }

    @Override
    protected int getId(Category obj) {
        return obj.getId();
    }
}