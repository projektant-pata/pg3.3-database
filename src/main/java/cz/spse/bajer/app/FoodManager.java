package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.IFoodManager;
import cz.spse.bajer.data.interfaces.IFoodDS;
import cz.spse.bajer.object.Food;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodManager implements IFoodManager {
    private final IFoodDS foodDS;
    private HashMap<Integer, Food> foods;

    public FoodManager(IFoodDS foodDS) {
        this.foodDS = foodDS;
        this.foods = foodDS.readAll();
    }

    @Override
    public Food create(Food food) {
        Food rs = foodDS.create(food);
        if (rs != null) {
            foods.put(rs.getId(), rs);
            return rs;
        } else {
            return null;
        }
    }

    @Override
    public Food read(int id) {
        return foods.get(id);
    }

    @Override
    public boolean update(int id, Food food) {
        if (foodDS.update(id, food)){
            foods.put(id, food);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Food delete(int id) {
        if (foodDS.delete(id)){
            return foods.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Food> readAll() {
        return new ArrayList<>(foods.values());
    }

    @Override
    public ArrayList<Food> readAllByCategory(int categoryId) {
        ArrayList<Food> result = new ArrayList<>();
        for (Food food : foods.values()) {
            if (food.getCategory().getId() == categoryId) {
                result.add(food);
            }
        }
        return result;
    }
}
