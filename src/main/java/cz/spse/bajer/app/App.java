package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.ICategoryManager;
import cz.spse.bajer.app.interfaces.IFoodManager;
import cz.spse.bajer.app.interfaces.ISpecialOfferManager;
import cz.spse.bajer.core.DatabaseConnection;
import cz.spse.bajer.data.database.CategoryDB;
import cz.spse.bajer.data.database.FoodDB;
import cz.spse.bajer.data.database.SpecialOfferDB;
import cz.spse.bajer.data.interfaces.ICategoryDS;
import cz.spse.bajer.data.interfaces.IFoodDS;
import cz.spse.bajer.data.interfaces.ISpecialOfferDS;
import cz.spse.bajer.gui.MainWindow;

import java.sql.Connection;

public class App {
    private ICategoryManager categoryManager;
    private IFoodManager foodManager;
    private ISpecialOfferManager specialOfferManager;

    public App() {
        Connection conn = DatabaseConnection.getConnection();
        ICategoryDS categoryDS = new CategoryDB(conn);
        IFoodDS foodDS = new FoodDB(conn);
        ISpecialOfferDS specialOfferDS = new SpecialOfferDB(conn);

        this.categoryManager = new CategoryManager(categoryDS);
        this.foodManager = new FoodManager(foodDS);
        this.specialOfferManager = new SpecialOfferManager(specialOfferDS);
    }

    public ICategoryManager getCategoryManager() {
        return categoryManager;
    }
    public IFoodManager getFoodManager() {
        return foodManager;
    }
    public ISpecialOfferManager getSpecialOfferManager() {
        return specialOfferManager;
    }

    public void start() {
        MainWindow dialog = new MainWindow(this);
        dialog.pack();
        dialog.setVisible(true);
    }
}
