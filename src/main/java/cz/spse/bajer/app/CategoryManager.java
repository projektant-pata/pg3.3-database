package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.ICategoryManager;
import cz.spse.bajer.data.interfaces.ICategoryDS;
import cz.spse.bajer.object.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryManager implements ICategoryManager {
    private final ICategoryDS categoryDS;
    private HashMap<Integer, Category> categories;

    public CategoryManager(ICategoryDS categoryDS) {
        this.categoryDS = categoryDS;
        this.categories = new HashMap<>();
    }

    @Override
    public Category create(Category category) {
        Category rs = categoryDS.create(category);
        if(rs != null){
            categories.put(rs.getId(), rs);
            return rs;
        }else {
            return null;
        }
    }

    @Override
    public Category read(int id) {
        return categories.get(id);
    }

    @Override
    public boolean update(int id, Category category) {
        if(categoryDS.update(id, category)){
            categories.put(id, category);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Category delete(int id) {
        if (categoryDS.delete(id)) {
            return categories.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Category> readAll() {
        return new ArrayList<>(categories.values());
    }

}
