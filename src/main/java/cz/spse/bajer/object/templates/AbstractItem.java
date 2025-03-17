package cz.spse.bajer.object.templates;

import cz.spse.bajer.object.Category;

public abstract class AbstractItem extends AbstractObj{
    private float price;
    private Category category;

    public AbstractItem(int id, String name, float price, Category category) {
        super(id, name);
        this.price = price;
        this.category = category;
    }

    public float getPrice() {
        return price;
    }
    public Category getCategory() {
        return category;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public void setCategory(Category category) {
        this.category = category;
    }


}
