package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.IFoodManager;
import cz.spse.bajer.data.interfaces.IFoodDS;
import cz.spse.bajer.object.Food;

public class FoodManager extends AbstractPricedManager<Food> implements IFoodManager {

    public FoodManager(IFoodDS foodDS) {
        super(foodDS);
    }

    @Override
    protected int getId(Food obj) {
        return obj.getId();
    }

    @Override
    protected int getCategoryId(Food item) {
        return item.getCategory().getId();
    }
}