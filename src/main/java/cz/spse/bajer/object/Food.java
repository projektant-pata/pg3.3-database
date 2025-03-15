package cz.spse.bajer.object;

public class Food extends TemplateObj {
    private float price;
    private Category category;

    public Food(int id, String name, float price, Category category) {
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
