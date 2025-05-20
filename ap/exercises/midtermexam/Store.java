package ap.exercises.midtermexam;

import java.util.ArrayList;
import java.util.List;

class Store {
    private List<Product> products;

    public Store() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void printAllProducts() {
        System.out.println("All Products in Store:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void printLaptops() {
        System.out.println("Laptops in Store:");
        for (Product product : products) {
            if (product instanceof Laptop) {
                System.out.println(product);
            }
        }
    }

    public void printCases() {
        System.out.println("Cases in Store:");
        for (Product product : products) {
            if (product instanceof Case) {
                System.out.println(product);
            }
        }
    }

    public void search(String name) {
        for (Product product : products) {
            if (product.getBrand().equals(name)) {
                System.out.println("found");
                System.out.println(product);
            }
        }
    }
}