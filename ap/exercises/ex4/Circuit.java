package ap.exercises.ex4;

public class Circuit {
    private int switch1;
    private int switch2;

    public Circuit(){
        switch1=0;
        switch2=0;
    }

    public int getFirstSwitchState() { // 0 for down, 1 for up
        return switch1;
    }

    public int getSecondSwitchState() { // 0 for down, 1 for up
        return switch1;
    }

    public int getLampState() { // 0 for off, 1 for on
        return (switch1 == switch2 ? 1 : 0);
    }

    public void toggleFirstSwitch(){
        switch1 = 1 - switch1; // like updated switch1
    }

    public void toggleSecondSwitch(){
        switch2 = 1 - switch2; // like updated switch2
    }
}
