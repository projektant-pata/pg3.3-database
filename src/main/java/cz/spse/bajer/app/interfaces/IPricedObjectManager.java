package cz.spse.bajer.app.interfaces;

import cz.spse.bajer.object.SpecialOffer;

import java.util.ArrayList;

public interface IPricedObjectManager<T> extends IObjectManager<T>{
    ArrayList<T> readAllByCategory(int categoryId);
}
