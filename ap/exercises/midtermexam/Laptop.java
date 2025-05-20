package ap.exercises.midtermexam;

public class Laptop extends Product {
    private String color;
    private String model;

    public Laptop(String brand, double price, String color, String model) {
        super(brand, price);
        this.color = color;
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "brand='" + getBrand() + '\'' +
                ", price=" + getPrice() +
                ", color='" + color + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
