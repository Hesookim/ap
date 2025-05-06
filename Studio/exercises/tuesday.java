package Studio.exercises;

import java.util.*;

class Books {
    String title;
    double price;

    public Books(String title, double price) {
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
        List<Books> books = new ArrayList<>(Arrays.asList(
                new Books("The Great Gatsby", 12.99),
                new Books("To Kill a Mockingbird", 9.99)
        ));

        List<Pen> pens = new ArrayList<>(Arrays.asList(
                new Pen("Parker", 5.99, "Blue"),
                new Pen("Pilot", 3.49, "Black")
        ));

        System.out.println("Books:");
        for (Books book : books) {
            System.out.println(book);
        }

        System.out.println("\nPens:");
        for (Pen pen : pens) {
            System.out.println(pen);
        }
    }
}