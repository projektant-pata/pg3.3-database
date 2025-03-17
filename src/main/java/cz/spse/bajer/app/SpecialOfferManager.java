package cz.spse.bajer.app;

import cz.spse.bajer.app.interfaces.ISpecialOfferManager;
import cz.spse.bajer.data.interfaces.ISpecialOfferDS;
import cz.spse.bajer.object.SpecialOffer;

public class SpecialOfferManager extends AbstractPricedManager<SpecialOffer> implements ISpecialOfferManager {

    public SpecialOfferManager(ISpecialOfferDS specialOfferDS) {
        super(specialOfferDS);
    }

    @Override
    protected int getId(SpecialOffer obj) {
        return obj.getId();
    }

    @Override
    protected int getCategoryId(SpecialOffer item) {
        return item.getCategory().getId();
    }
}