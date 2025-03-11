package cz.spse.bajer.facade;

import cz.spse.bajer.core.DatabaseConnection;
import cz.spse.bajer.object.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodFacade implements  FacadeInterface<Food>{
    public boolean create(Food food) {
        String sql = "INSERT INTO food (name, price, id_category) VALUES (?,?,?)";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, food.getName());
            stmt.setFloat(2, food.getPrice());
            stmt.setInt(3, food.getId_category());
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Food read(int id) {
        String sql = "SELECT * FROM food WHERE id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Food(id, rs.getString("name"), rs.getFloat("price"), rs.getInt("id_category"));
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(int id, Food food) {
        String sql = "UPDATE food SET name = ?, price = ?, id_category = ? WHERE id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, food.getName());
            stmt.setFloat(2, food.getPrice());
            stmt.setInt(3, food.getId_category());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM food WHERE id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Food> readAllFoodsByCategory(int id_category) {
        String sql = "SELECT * FROM food WHERE id_category = ?";
        List<Food> list = new ArrayList<>();
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id_category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Food(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getInt("id_category")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int translateCategoryNameToId(String name) {
        String sql = "SELECT id FROM category WHERE name = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt("id");
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
