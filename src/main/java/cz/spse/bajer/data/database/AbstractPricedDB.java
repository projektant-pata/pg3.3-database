package cz.spse.bajer.data.database;

import java.sql.*;
import java.util.HashMap;

public abstract class AbstractPricedDB<T> extends AbstractDB<T> {

    public AbstractPricedDB(Connection conn) {
        super(conn);
    }

    @Override
    public T read(int id) {
        String sql = "SELECT o.*, c.name as category_name FROM " + getTableName() + " o " +
                "JOIN category c ON o.id_category = c.id " +
                "WHERE o.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<Integer, T> readAll() {
        String sql = "SELECT o.*, c.name as category_name FROM " + getTableName() + " o " +
                "JOIN category c ON o.id_category = c.id";
        HashMap<Integer, T> items = new HashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                T obj = mapResultSet(rs);
                items.put(rs.getInt("id"), obj);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
