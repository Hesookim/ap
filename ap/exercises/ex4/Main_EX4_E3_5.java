package ap.exercises.ex4;

public class Main_EX4_E3_5 {
    public static void main(String[] args) {
        Circuit circuit;

        System.out.println("Testing all 4 switch combinations:");
        System.out.println("UP = 1, DOWN = 0");
        System.out.println("-------------------------------");

        // Combination 1: DOWN (0), DOWN (0)
        circuit = new Circuit(); // Reset circuit
        System.out.println("Combination 1: Switch1=DOWN, Switch2=DOWN");
        printState(circuit);

        // Combination 2: UP (1), DOWN (0)
        circuit = new Circuit();
        circuit.toggleFirstSwitch(); // Flips Switch1 to UP
        System.out.println("Combination 2: Switch1=UP, Switch2=DOWN");
        printState(circuit);

        // Combination 3: DOWN (0), UP (1)
        circuit = new Circuit();
        circuit.toggleSecondSwitch(); // Flips Switch2 to UP
        System.out.println("Combination 3: Switch1=DOWN, Switch2=UP");
        printState(circuit);

        // Combination 4: UP (1), UP (1)
        circuit = new Circuit();
        circuit.toggleFirstSwitch(); // Switch1=UP
        circuit.toggleSecondSwitch(); // Switch2=UP
        System.out.println("Combination 4: Switch1=UP, Switch2=UP");
        printState(circuit);
    }

    private static void printState(Circuit circuit) {
        System.out.println(
                "Switch 1: " + (circuit.getFirstSwitchState() == 1 ? "UP" : "DOWN") +
                        " | Switch 2: " + (circuit.getSecondSwitchState() == 1 ? "UP" : "DOWN") +
                        " | Lamp: " + (circuit.getLampState() == 1 ? "ON" : "OFF")
        );
    }
}