package ap.exercises.ex5;

import ap.exercises.html.StringCounter;

public class TestingStringCounter {
    public static void main(String[] args) {
        StringCounter counter = new StringCounter();

        counter.add("java");
        counter.add("python");
        counter.add("java");
        counter.add("c++");
        counter.add("python");
        counter.add("java");

        System.out.println("All strings:");
        counter.printAll();
    }
}

