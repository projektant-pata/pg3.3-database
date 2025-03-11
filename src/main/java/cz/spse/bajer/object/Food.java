package cz.spse.bajer.object;

public class Food {
    private int id;
    private String name;
    private float price;
    private int id_category;

    public Food(int id, String name, float price, int id_category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.id_category = id_category;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public float getPrice() {
        return price;
    }
    public int getId_category() {
        return id_category;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
}
