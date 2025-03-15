package cz.spse.bajer.data.database;

import cz.spse.bajer.data.interfaces.IFoodDS;
import cz.spse.bajer.object.Category;
import cz.spse.bajer.object.Food;

import java.sql.*;
import java.util.HashMap;

public class FoodDB implements IFoodDS {
    private final Connection conn;

    public FoodDB(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Food create(Food food) {
        String sql = "INSERT INTO food (name, price, id_category) VALUES (?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, food.getName());
            stmt.setFloat(2, food.getPrice());
            stmt.setInt(3, food.getCategory().getId());
            stmt.executeUpdate();
            conn.commit();

            // Získání vygenerovaného ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Food(id, food.getName(), food.getPrice(), food.getCategory());
                } else {
                    throw new RuntimeException("Vložení jídla selhalo, nebylo vygenerováno ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Food read(int id) {
        String sql = "SELECT * FROM food JOIN category ON food.id_category = category.id WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Food(id, rs.getString("food.name"), rs.getFloat("food.price"), new Category(rs.getInt("category.id"), rs.getString("category.name")));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(int id, Food food) {
        String sql = "UPDATE food SET name = ?, price = ?, id_category = ? WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, food.getName());
            stmt.setFloat(2, food.getPrice());
            stmt.setInt(3, food.getCategory().getId());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM food WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<Integer, Food> readAll() {
        String sql = "SELECT * FROM food JOIN category ON food.id_category = category.id";
        HashMap<Integer, Food> foods = new HashMap<>();
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                foods.put(rs.getInt("id"), new Food(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), new Category(rs.getInt("category.id"), rs.getString("category.name"))));
            }
            return foods;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
