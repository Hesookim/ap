package ap.exercises.ex4;

public class Main_EX4_E3_10 {
        public static void main(String[] args) {
            CashRegister register = new CashRegister();

            register.addItem(3.50);
            register.addItem(2.25);
            register.addItem(4.99);

            register.printReceipt();
        }
    }