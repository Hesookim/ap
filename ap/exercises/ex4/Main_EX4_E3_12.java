package ap.exercises.ex4;

public class Main_EX4_E3_12 {
    public static void main(String[] args) {
        Employee harry = new Employee("Hacker, Harry" , 50000);
        System.out.println("Employee Name: " + harry.getName());
        System.out.println("Current Salary: $" + harry.getSalary());

        harry.raiseSalary(10);

        System.out.println( harry.getName() + " has gotten a raise in salary!");
        System.out.println("After raise salary is: $ " + harry.getSalary());


        Employee minoo = new Employee("Namdar, Minoo" , 72000);
        System.out.println("Employee Name: " + minoo.getName());
        System.out.println("Current Salary: $" + minoo.getSalary());

        harry.raiseSalary(2.5);

        System.out.println( minoo.getName() + " has gotten a raise in salary!");
        System.out.println("After raise salary is: $ " + minoo.getSalary());
    }
}
