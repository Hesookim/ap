package ap.exercises.ex4;

public class Main_EX4_E3_15 {
    public static void main(String[] args) {
        Letter letter = new Letter("Lena", "Minoo");

        letter.addLine("Happy girls day!!!");
        letter.addLine("I wish you all the best.");

        System.out.println(letter.getText());
    }
}
