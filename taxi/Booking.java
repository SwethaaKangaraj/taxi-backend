public class Booking {
    private int id;
    private int customerId;
    private String pickupPoint;
    private String dropPoint;
    private int pickupTime;
    private int dropTime;
   double amount;
    private Taxi taxi;

    public Booking(int id, int customerId, String pickupPoint, String dropPoint, int pickupTime,int dropTime,double amount,Taxi taxi) {
        this.id = id;
        this.customerId = customerId;
        this.pickupPoint = pickupPoint;
        this.dropPoint = dropPoint;
        this.pickupTime = pickupTime;
        this.dropTime=dropTime;
        this.amount=amount;
        this.taxi = taxi;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public String getDropPoint() {
        return dropPoint;
    }

    public int getPickupTime() {
        return pickupTime;
    }

    public int getDropTime() {
        return dropTime;
    }

    public void setDropTime(int dropTime) {
        this.dropTime = dropTime;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public  void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
     public double getAmount() {
        return amount;
    }
}