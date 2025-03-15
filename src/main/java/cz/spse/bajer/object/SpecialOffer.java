package cz.spse.bajer.object;

public class SpecialOffer extends TemplateObj {
    private float price;
    private Category category;

    public SpecialOffer(int id, String name, float price, Category category) {
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

    public void setPrice(int price) {
        this.price = price;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
