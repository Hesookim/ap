package ap.exercises.midtermexam;

public class Case extends Product {
    private String tOC;
    private int nOc;

    public Case(String brand, double price, String tOC, int nOC) {
        super(brand, price);
        this.tOC = tOC;
        this.nOc = nOC;
    }

    public String getTOC() {
        return tOC;
    }

    public void setTOC(String tOC) {
        this.tOC = tOC;
    }

    public int getNOc() {
        return nOc;
    }

    public void setNOc(int nOc) {
        this.nOc = nOc;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "brand='" + getBrand() + '\'' +
                ", price=" + getPrice() +
                ", color='" + tOC + '\'' +
                ", model='" + nOc + '\'' +
                '}';
    }
}
