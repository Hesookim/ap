package Studio.exercises;

import java.util.Scanner;

class Shape {
    public double area() {
        return 0;
    }
}

class Circle extends Shape{
    double radius;
    public Circle(double r) {
        radius = r;

    }@Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length;
    double width;
    public Rectangle(double l, double w) {
        length = l;
        width = w;
    }

    @Override
    public double area() {
        return length * width;
    }
}
public class areaCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which shapes area you want to calculate?");
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        int shape = scanner.nextInt();
        switch (shape) {
            case 1 :
                System.out.println("Enter radius: ");
                double r = scanner.nextInt();
                Circle myCircle = new Circle(r);
                System.out.println("Circle area: " + myCircle.area());
                break;

            case 2 :
                System.out.println("Enter width: ");
                double w = scanner.nextInt();
                System.out.println("Enter length: ");
                double l = scanner.nextInt();
                Rectangle myRect = new Rectangle(l,w);
                System.out.println("Rectangle area: " + myRect.area());
                break;
        }
    }
}