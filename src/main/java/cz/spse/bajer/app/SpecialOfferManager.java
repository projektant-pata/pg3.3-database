package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.ISpecialOfferManager;
import cz.spse.bajer.data.interfaces.ISpecialOfferDS;
import cz.spse.bajer.object.SpecialOffer;

import java.util.ArrayList;
import java.util.HashMap;

public class SpecialOfferManager implements ISpecialOfferManager {
    private final ISpecialOfferDS specialOfferDS;
    private HashMap<Integer, SpecialOffer> specialOffers;

    public SpecialOfferManager(ISpecialOfferDS specialOfferDS) {
        this.specialOfferDS = specialOfferDS;
        this.specialOffers = specialOfferDS.readAll();
    }

    @Override
    public SpecialOffer create(SpecialOffer specialOffer) {
        SpecialOffer rs = specialOfferDS.create(specialOffer);
        if (rs != null) {
            specialOffers.put(rs.getId(), rs);
            return rs;
        } else {
            return null;
        }
    }

    @Override
    public SpecialOffer read(int id) {
        return specialOfferDS.read(id);
    }

    @Override
    public boolean update(int id, SpecialOffer specialOffer) {
        if (specialOfferDS.update(id, specialOffer)){
            specialOffers.put(id, specialOffer);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SpecialOffer delete(int id) {
        if (specialOfferDS.delete(id)){
            return specialOffers.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<SpecialOffer> readAll() {
        return new ArrayList<>(specialOffers.values());
    }

    @Override
    public ArrayList<SpecialOffer> readAllByCategory(int categoryId) {
        ArrayList<SpecialOffer> result = new ArrayList<>();
        for (SpecialOffer specialOffer : specialOffers.values()) {
            if (specialOffer.getCategory().getId() == categoryId) {
                result.add(specialOffer);
            }
        }
        return result;
    }
}
