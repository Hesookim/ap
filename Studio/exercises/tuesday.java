package Studio.exercises;

import java.util.*;

class Book {
    String title;
    double price;

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }


    @Override
    public String toString() {
        return title + ", " + price;
    }
}

class Pen {
    String brand;
    double price;
    String color;

    public Pen(String brand, double price, String color) {
        this.brand = brand;
        this.price = price;
        this.color = color;
    }


    @Override
    public String toString() {
        return brand + ", " + price + ", " + color;
    }
}

public class tuesday {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<Book>(Arrays.asList(
                new Book("abs","145"),

        ));

        List<Pen> pens = new ArrayList<Pen>();


    }
}