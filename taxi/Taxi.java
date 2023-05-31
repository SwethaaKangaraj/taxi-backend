class Taxi {
    private int id;
    private String location;
    private double earnings;

    public Taxi(int id, String location) {
        this.id = id;
        this.location = location;
        this.earnings = 0.0;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addEarnings(double amount) {
        this.earnings += amount;
    }
}