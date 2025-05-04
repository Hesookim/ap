package Studio.exercises;

import java.util.Scanner;

class University {
    private String name;

    public University(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Student {
    public String name;
    private int age;
    public String major;
    public University university;

    public Student(String name, int age, String major, University university) {
        this.name = name;
        this.age = age;
        this.major = major;
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMajor() {
        return major;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get university information
        System.out.println("Enter University Name:");
        String uniName = scanner.nextLine();
        University university = new University(uniName);

        // Get student information
        System.out.println("\nEnter Student Details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Major: ");
        String major = scanner.nextLine();

        // Create student
        Student student = new Student(name, age, major, university);

        // Display information
        System.out.println("\n--- Student Information ---");
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Major: " + student.getMajor());
        System.out.println("University: " + university.getName());
        scanner.close();
    }
}