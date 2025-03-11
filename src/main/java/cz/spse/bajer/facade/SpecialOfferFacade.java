package cz.spse.bajer.facade;

import cz.spse.bajer.core.DatabaseConnection;
import cz.spse.bajer.object.SpecialOffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialOfferFacade implements FacadeInterface<SpecialOffer>{
    public boolean create(SpecialOffer specialOffer) {
        String sql = "INSERT INTO special_offer (name, price, id_category) VALUES (?, ?, ?)";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            stmt.setString(1, specialOffer.getName());
            stmt.setFloat(2, specialOffer.getPrice());
            stmt.setInt(3, specialOffer.getId_category());
            stmt.executeUpdate();
            conn.commit();

            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SpecialOffer read(int id) {
        String sql = "SELECT * FROM special_offer WHERE id = ?";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               return new SpecialOffer(id, rs.getString("name"), rs.getFloat("price"), rs.getInt("id_category"));
            }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(int id, SpecialOffer specialOffer) {
        String sql = "UPDATE special_offer SET name = ?, price = ?, id_category = ? WHERE id = ?";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            stmt.setString(1, specialOffer.getName());
            stmt.setFloat(2, specialOffer.getPrice());
            stmt.setInt(3, specialOffer.getId_category());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(int id) {
        String sql = "DELETE FROM special_offer WHERE id = ?";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SpecialOffer> readAllSpecialOffersByCategory(int id_category) {
        String sql = "SELECT * FROM special_offer WHERE id_category = ?";
        List<SpecialOffer> specialOffers = new ArrayList<>();
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            stmt.setInt(1, id_category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                specialOffers.add(new SpecialOffer(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getInt("id_category")));
            }
            return specialOffers;
        }catch (SQLException e){
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
