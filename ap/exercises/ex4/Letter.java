package ap.exercises.ex4;

public class Letter {
    private String sender;
    private String recipient;
    private String body;

    public Letter(String from, String to) {
        sender = from;
        recipient = to;
        body = "";
    }
    public void addLine(String line) {
        body = body.concat(line).concat("\n");
    }

    public String getText() {
        String letter = "Dear " + recipient + ": \n\n";
        letter = letter.concat(body);
        letter = letter.concat("\nSincerely,\n\n");
        letter = letter.concat(sender);
        return letter;
    }
}