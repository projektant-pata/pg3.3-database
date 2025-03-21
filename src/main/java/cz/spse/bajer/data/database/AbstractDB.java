package cz.spse.bajer.data.database;

import cz.spse.bajer.data.interfaces.IDataSource;

import java.sql.*;
import java.util.HashMap;

public abstract class AbstractDB<T> implements IDataSource<T> {
    protected final Connection conn;

    public AbstractDB(Connection conn) {
        this.conn = conn;
    }

    protected abstract String getInsertQuery();
    protected abstract void setInsertParameters(PreparedStatement stmt, T obj) throws SQLException;
    protected abstract T mapResultSet(ResultSet rs) throws SQLException;
    protected abstract String getTableName();

    @Override
    public T create(T obj) {
        System.out.println("DB creating");
        try (PreparedStatement stmt = conn.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            setInsertParameters(stmt, obj);
            stmt.executeUpdate();
            conn.commit();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("Vloženo ID: " + generatedKeys.getInt(1));
                    return mapGeneratedKeys(obj, generatedKeys);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Vložení objektu selhalo, nebylo vygenerováno ID.");
    }

    protected abstract T mapGeneratedKeys(T obj, ResultSet generatedKeys) throws SQLException;

    @Override
    public T read(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
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
    public boolean update(int id, T obj) {
        String sql = getUpdateQuery();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setUpdateParameters(stmt, obj, id);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getUpdateQuery();
    protected abstract void setUpdateParameters(PreparedStatement stmt, T obj, int id) throws SQLException;

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<Integer, T> readAll() {
        String sql = "SELECT * FROM " + getTableName();
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