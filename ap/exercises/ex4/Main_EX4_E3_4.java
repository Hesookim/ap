package ap.exercises.ex4;

public class Main_EX4_E3_4 {
    public static void main(String[] args) {
        Circuit circuit = new Circuit();
        System.out.println("Current lamp state: " + circuit.getLampState()); // 1 (on)

        circuit.toggleFirstSwitch();
        System.out.println("After toggle1, lamp state is: " + circuit.getLampState()); // 0 (off)

        circuit.toggleSecondSwitch();
        System.out.println("After toggle2, lamp state is: " + circuit.getLampState()); // 1 (on)
    }
}
