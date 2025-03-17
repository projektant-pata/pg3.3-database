package cz.spse.bajer.data.database;

import cz.spse.bajer.data.interfaces.ICategoryDS;
import cz.spse.bajer.object.Category;

import java.sql.*;

public class CategoryDB extends AbstractDB<Category> implements ICategoryDS {
    public CategoryDB(Connection conn) {
        super(conn);
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO category (name) VALUES (?)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Category obj) throws SQLException {
        stmt.setString(1, obj.getName());
    }

    @Override
    protected Category mapGeneratedKeys(Category obj, ResultSet generatedKeys) throws SQLException {
        return new Category(generatedKeys.getInt(1), obj.getName());
    }

    @Override
    protected String getTableName() {
        return "category";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE category SET name = ? WHERE id = ?";
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Category obj, int id) throws SQLException {
        stmt.setString(1, obj.getName());
        stmt.setInt(2, id);
    }

    @Override
    protected Category mapResultSet(ResultSet rs) throws SQLException {
        return new Category(rs.getInt("id"), rs.getString("name"));
    }
}
