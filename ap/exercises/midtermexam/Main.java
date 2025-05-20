package ap.exercises.midtermexam;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();

        store.addProduct(new Laptop("ASUS", 1000, "black", "TUF GAMING"));
        store.addProduct(new Case("HP", 2000, "corei7", 2));
        store.addProduct(new Laptop("Dell", 1200, "silver", "XPS 15"));
        store.addProduct(new Case("Lenovo", 1500, "corei5", 1));

        System.out.println("Product list:");
        store.printAllProducts();

        System.out.println("\n___________\n");

        System.out.println("Laptop products list:");
        store.printLaptops();

        System.out.println("\n___________\n");

        System.out.println("Case products list:");
        store.printCases();

        System.out.println("Search by name:");
        store.search("Dell");
    }
}