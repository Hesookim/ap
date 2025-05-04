package Studio.exercises;

class Transport {
    public void brand() {
        System.out.println("Generic transport brand");
    }
}

class Car extends Transport {
    String speedLimit; // Speed Limit
    int year;

    public Car(String speedLimit, int year) {
        this.speedLimit = speedLimit;
        this.year = year;
    }

    @Override
    public void brand() {
        System.out.println("Car brand - Speed Limit: " + speedLimit + ", Year: " + year);
    }
}

class Bicycle extends Transport {
    int gearCount;
    String frameMaterial;

    public Bicycle(int gearCount, String frameMaterial) {
        this.gearCount = gearCount;
        this.frameMaterial = frameMaterial;
    }

    @Override
    public void brand() {
        System.out.println("Bicycle brand - Gears: " + gearCount + ", Frame: " + frameMaterial);
    }
}

public class Inher {
    public static void main(String[] args) {
        Car myCar = new Car("120km/h", 2022);
        Bicycle myBike = new Bicycle(6, "Aluminum");

        myCar.brand();
        myBike.brand();
    }
}