package ap.exercises.ex4;

public class CircuitTester {
    public static void main(String[] args) {
        System.out.println("Testing all switch combinations:");
        System.out.println("UP = 1, DOWN = 0");
        System.out.println("-------------------------------");

        // Test all 4 possible switch combinations
        testCombination(1, false, false); // DOWN, DOWN
        testCombination(2, true, false);  // UP, DOWN
        testCombination(3, false, true);  // DOWN, UP
        testCombination(4, true, true);   // UP, UP
    }

    private static void testCombination(int testNum, boolean switch1Up, boolean switch2Up) {
        Circuit circuit = new Circuit();

        if (switch1Up)
            circuit.toggleFirstSwitch();
        if (switch2Up)
            circuit.toggleSecondSwitch();

        System.out.printf("Combination %d: Switch1=%s, Switch2=%s%n",
                testNum,
                switch1Up ? "UP" : "DOWN",
                switch2Up ? "UP" : "DOWN");
        printState(circuit);
        System.out.println();
    }

    private static void printState(Circuit circuit) {
        System.out.println(
                "Switch 1: " + (circuit.getFirstSwitchState() == 1 ? "UP" : "DOWN") +
                        " || Switch 2: " + (circuit.getSecondSwitchState() == 1 ? "UP" : "DOWN") +
                        " || Lamp: " + (circuit.getLampState() == 1 ? "ON" : "OFF")
        );
    }
}
