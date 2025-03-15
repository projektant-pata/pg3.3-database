package cz.spse.bajer.app.interfaces;

import cz.spse.bajer.object.SpecialOffer;

import java.util.ArrayList;


public interface ISpecialOfferManager extends IObjectManager<SpecialOffer> {
    ArrayList<SpecialOffer> readAllByCategory(int categoryId);
}
