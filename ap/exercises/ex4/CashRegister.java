package ap.exercises.ex4;

public class CashRegister {
    private double total;
    private String receipt;

    public CashRegister() {
        total = 0;
        receipt = "「 Receipt:\n 」";
    }

    public void addItem(double price) {
        total += price;
        receipt = receipt.concat("Item: " + price + "\n"); // As per the given structure
    }

    public void printReceipt() {
        System.out.println(receipt);
        System.out.println("Total: $" + total);
    }
}