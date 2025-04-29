package ap.exercises.ex4;

public class Main_EX4_E3_6 {
        public static void main(String[] args) {
            new_Circuit new_circuit = new new_Circuit();

            System.out.println("Switch States (0=DOWN, 1=UP) | Lamp (1=ON, 0=OFF)");
            System.out.println("-----------------------------------------------");

            printCircuit(new_circuit); // Default State

            // Test all combinations (modified)
            new_circuit.toggleSwitch(1); // After first toggle
            printCircuit(new_circuit);

            new_circuit.toggleSwitch(2); // After second toggle
            printCircuit(new_circuit);

            new_circuit.toggleSwitch(1); // After third toggle
            printCircuit(new_circuit);
        }

        private static void printCircuit(new_Circuit circuit) {
            System.out.println(
                    "Switch 1: " + (circuit.getSwitchState(1) == 1 ? 1 : 0) +
                            " | Switch 2: " + (circuit.getSwitchState(2) == 1 ? 1 : 0) +
                            " | Lamp: " + (circuit.getLampState() == 1 ? 1 : 0)
            );
        }
}
