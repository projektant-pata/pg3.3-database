package cz.spse.bajer.data.database;

import cz.spse.bajer.data.interfaces.ISpecialOfferDS;
import cz.spse.bajer.object.Category;
import cz.spse.bajer.object.SpecialOffer;

import java.sql.*;

public class SpecialOfferDB extends AbstractPricedDB<SpecialOffer> implements ISpecialOfferDS {
    public SpecialOfferDB(Connection conn) {
        super(conn);
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO special_offer (name, price, id_category) VALUES (?, ?, ?)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, SpecialOffer obj) throws SQLException {
        stmt.setString(1, obj.getName());
        stmt.setFloat(2, obj.getPrice());
        stmt.setInt(3, obj.getCategory().getId());
    }

    @Override
    protected SpecialOffer mapGeneratedKeys(SpecialOffer obj, ResultSet generatedKeys) throws SQLException {
        return new SpecialOffer(generatedKeys.getInt(1), obj.getName(), obj.getPrice(), obj.getCategory());
    }

    @Override
    protected String getTableName() {
        return "special_offer";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE special_offer SET name = ?, price = ?, id_category = ? WHERE id = ?";
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, SpecialOffer obj, int id) throws SQLException {
        stmt.setString(1, obj.getName());
        stmt.setFloat(2, obj.getPrice());
        stmt.setInt(3, obj.getCategory().getId());
        stmt.setInt(4, id);
    }

    @Override
    protected SpecialOffer mapResultSet(ResultSet rs) throws SQLException {
        Category category = createCategoryFromResultSet(rs);

        return new SpecialOffer(
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