package ap.exercises.ex4;

public class new_Circuit {
    // Use two boolean variables to store switch states
    private boolean switch1; // true = UP, false = DOWN
    private boolean switch2;

    public new_Circuit() {
        switch1 = false; // Both start DOWN
        switch2 = false;
    }

    // Get switch state (0=DOWN, 1=UP)
    public int getSwitchState(int switchNum) {
        if (switchNum == 1) {
            return switch1 ? 1 : 0;
        } else {
            return switch2 ? 1 : 0;
        }
    }

    // Get lamp state (1=ON, 0=OFF)
    public int getLampState() {
        return (switch1 == switch2) ? 1 : 0;
    }

    // Toggle the specified switch
    public void toggleSwitch(int switchNum) {
        if (switchNum == 1) {
            switch1 = !switch1;
        } else {
            switch2 = !switch2;
        }
    }
}