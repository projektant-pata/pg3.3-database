package cz.spse.bajer.data.database;

import cz.spse.bajer.data.interfaces.IFoodDS;
import cz.spse.bajer.object.Food;
import cz.spse.bajer.object.Category;

import java.sql.*;

public class FoodDB extends AbstractPricedDB<Food> implements IFoodDS {
    public FoodDB(Connection conn) {
        super(conn);
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO food (name, price, id_category) VALUES (?, ?, ?)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Food obj) throws SQLException {
        stmt.setString(1, obj.getName());
        stmt.setFloat(2, obj.getPrice());
        stmt.setInt(3, obj.getCategory().getId());
    }

    @Override
    protected Food mapGeneratedKeys(Food obj, ResultSet generatedKeys) throws SQLException {
        return new Food(generatedKeys.getInt(1), obj.getName(), obj.getPrice(), obj.getCategory());
    }

    @Override
    protected String getTableName() {
        return "food";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE food SET name = ?, price = ?, id_category = ? WHERE id = ?";
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Food obj, int id) throws SQLException {
        stmt.setString(1, obj.getName());
        stmt.setFloat(2, obj.getPrice());
        stmt.setInt(3, obj.getCategory().getId());
        stmt.setInt(4, id);
    }

    @Override
    protected Food mapResultSet(ResultSet rs) throws SQLException {
        Category category = createCategoryFromResultSet(rs);

        return new Food(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getFloat("price"),
                category
        );
    }

    private Category createCategoryFromResultSet(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("id_category"),
                rs.getString("category_name")
        );
    }

}
