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

    }

    @Override
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

class Square extends Shape{
    double sl; // Side Length
    public Square(double s) {
        sl = s;

    }

    @Override
    public double area() {
        return sl * sl;
    }
}

class Triangle extends Shape{
    double base;
    double height;
    public Triangle(double b, double h) {
        base = b;
        height = h;
    }

    @Override
    public double area(){
        return ((double) 1 /2) * base * height;
    }
}

class Parallelogram extends Shape{
    double base;
    double height;
    public Parallelogram(double b, double h) {
        base = b;
        height = h;
    }

    @Override
    public double area(){
        return base * height;
    }
}

class Trapezoid extends Shape {
    double a;
    double b;
    double h;
    public Trapezoid(double a1, double b1, double h1) {
        a = a1;
        b = b1;
        h=h1;
    }

    @Override
    public double area() {
        return ((double) 1 /2) * (a + b) * h;
    }
}

public class areaCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which shapes area you want to calculate?");
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Square");
        System.out.println("4. Triangle");
        System.out.println("5. Parallelogram");
        System.out.println("6. Trapezoid");
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

            case 3 :
                System.out.println("Enter width: ");
                double s = scanner.nextInt();
                Square mySquare = new Square(s);
                System.out.println("Square area: " + mySquare.area());
                break;

            case 4 :
                System.out.println("Enter base: ");
                double b = scanner.nextInt();
                System.out.println("Enter height: ");
                double h = scanner.nextInt();
                Triangle myTriangle = new Triangle(b , h);
                System.out.println(" Triangle area: " + myTriangle.area());
                break;

            case 5 :
                System.out.println("Enter base: ");
                double bp = scanner.nextInt();
                System.out.println("Enter height: ");
                double hp = scanner.nextInt();
                Parallelogram myParallelogram = new Parallelogram(bp , hp);
                System.out.println("Parallelogram area: " + myParallelogram.area());
                break;

            case 6 :
                System.out.println("Enter first parallel side: ");
                double a1 = scanner.nextInt();
                System.out.println("Enter second parallel side: ");
                double b1 = scanner.nextInt();
                System.out.println("Enter height: ");
                double h1 = scanner.nextInt();
                Trapezoid myTrapezoid = new Trapezoid(a1 , b1, h1);
                System.out.println("Trapezoid area: " + myTrapezoid.area());
                break;
        }
    }
}