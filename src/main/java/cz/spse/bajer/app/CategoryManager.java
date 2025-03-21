package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.ICategoryManager;
import cz.spse.bajer.data.interfaces.ICategoryDS;
import cz.spse.bajer.object.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager extends AbstractManager<Category> implements ICategoryManager {

    public CategoryManager(ICategoryDS categoryDS) {
        super(categoryDS);
    }

    @Override
    protected int getId(Category obj) {
        return obj.getId();
    }

    @Override
    public Category getByName(String name) {
        ArrayList<Category> list = readAll();
        for (Category category : list) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }


}