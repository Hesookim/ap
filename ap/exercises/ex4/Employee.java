package ap.exercises.ex4;

public class Employee {
    private String name;
    private double salary;

    public Employee(String employeeName, double currentSalary) {
        this.name = employeeName;
        this.salary = currentSalary;
    }

    public String getName(){
        return name;
    }

    public double getSalary(){
        return salary;
    }

    public void raiseSalary(double byPercent){
        salary = salary + (salary * byPercent / 100);
    }
}