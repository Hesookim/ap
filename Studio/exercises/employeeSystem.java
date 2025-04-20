package Studio.exercises;

import java.util.Scanner;

class Person {
    String name;
    Person(String name) {
        this.name = name;
    }
}

class Employee extends Person {
    int id;
    Employee(String name, int id) {
        super(name); // Calls Person(name)
        this.id = id;
    }
}
public class employeeSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a name: ");
        String name = scanner.nextLine();
        System.out.println("Enter a id: ");
        int idS = scanner.nextInt();
        Employee employee = new Employee(name, idS);
        System.out.println(employee.name +  " " + employee.id);
    }
}
