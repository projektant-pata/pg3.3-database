package cz.spse.bajer.data.database;

import cz.spse.bajer.data.interfaces.ISpecialOfferDS;
import cz.spse.bajer.object.Category;
import cz.spse.bajer.object.SpecialOffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class SpecialOfferDB implements ISpecialOfferDS {
    private final Connection conn;

    public SpecialOfferDB(Connection conn) {
        this.conn = conn;
    }

    @Override
    public SpecialOffer create(SpecialOffer specialOffer) {
        String sql = "INSERT INTO special_offer (name, price, id_food) VALUES (?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, specialOffer.getName());
            stmt.setFloat(2, specialOffer.getPrice());
            stmt.setInt(3, specialOffer.getCategory().getId());
            stmt.executeUpdate();
            conn.commit();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new SpecialOffer(id, specialOffer.getName(), specialOffer.getPrice(), specialOffer.getCategory());
                } else {
                    throw new RuntimeException("Vložení speciální nabídky selhalo, nebylo vygenerováno ID.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpecialOffer read(int id) {
        String sql = "SELECT * FROM special_offer JOIN category ON special_offer.id_category = category.id WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new SpecialOffer(id, rs.getString("name"), rs.getFloat("price"), new Category(rs.getInt("category.id"), rs.getString("category.name")));
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(int id, SpecialOffer SpecialOffer) {
        String sql = "UPDATE special_offer SET name = ?, price = ?, id_food = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, SpecialOffer.getName());
            stmt.setFloat(2, SpecialOffer.getPrice());
            stmt.setInt(3, SpecialOffer.getCategory().getId());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM special_offer WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<Integer, SpecialOffer> readAll() {
        String sql = "SELECT * FROM special_offer JOIN category ON special_offer.id_category = category.id";
        HashMap<Integer, SpecialOffer> specialOffers = new HashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                specialOffers.put(rs.getInt("id"), new SpecialOffer(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), new Category(rs.getInt("category.id"), rs.getString("category.name"))));
            }
            return specialOffers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
