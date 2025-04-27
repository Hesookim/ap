package ap.exercises.ex4;

public class Main_EX4_E3_5 {
    public static void main(String[] args) {
        Circuit circuit = new Circuit();

        System.out.println("TEST 1: Current State");
        printState(circuit);

        System.out.println("TEST 2: Toggle Switch1 " );
        circuit.toggleFirstSwitch();
        printState(circuit);

        System.out.println("TEST 3: Toggle Switch2 " );
        circuit.toggleSecondSwitch();
        printState(circuit);
    }

    private static void printState(Circuit circuit){
        System.out.println(
                "Switch 1: " + (circuit.getFirstSwitchState() == 1 ? "UP" : "DOWN") +
                        " | Switch 2: " + (circuit.getSecondSwitchState() == 1 ? "UP" : "DOWN") +
                        " | Lamp: " + (circuit.getLampState() == 1 ? "ON" : "OFF")
        );
    }
}
