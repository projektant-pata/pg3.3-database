package cz.spse.bajer.data.database;

import cz.spse.bajer.data.interfaces.ICategoryDS;
import cz.spse.bajer.object.Category;

import java.sql.*;
import java.util.HashMap;

public class CategoryDB implements ICategoryDS {
    private final Connection conn;

    public CategoryDB(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Category create(Category category) {
        String sql = "INSERT INTO category (name) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
            conn.commit();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new Category(id, category.getName()); // Vrácení nové instance s ID
                } else {
                    throw new RuntimeException("Vložení kategorie selhalo, nebylo vygenerováno ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Category read(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(id, rs.getString("name"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(int id, Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM category WHERE id = ?";
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
    public HashMap<Integer, Category> readAll() {
        String sql = "SELECT * FROM category";
        HashMap<Integer, Category> categories = new HashMap<>();
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.put(rs.getInt("id"), new Category(rs.getInt("id"), rs.getString("name")));
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
