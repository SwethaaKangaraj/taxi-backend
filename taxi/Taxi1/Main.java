import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Taxi Booking System!");

        System.out.print("Enter the number of taxis in the fleet: ");
        int numTaxis = scanner.nextInt();
        BookingService bookingService = new BookingService(numTaxis);

        while (true) {
            System.out.println("\nEnter an option:");
            System.out.println("1. Book a taxi");
            System.out.println("2. Display taxi details");
            System.out.println("3. Exit");
            int option = scanner.nextInt();

            switch(option){
            
            case 1:
                System.out.print("Enter customer ID: ");
                int customerId = scanner.nextInt();
                System.out.print("Enter pickup point: ");
                String pickupPoint = scanner.next();
                System.out.print("Enter drop point: ");
                String dropPoint = scanner.next();
                System.out.print("Enter pickup time: ");
                int pickupTime = scanner.nextInt();

                String message = bookingService.bookTaxi(customerId, pickupPoint.toUpperCase() , dropPoint.toUpperCase(), pickupTime);
                System.out.println(message);
                break;
            case 2:
                bookingService.displayTaxiDetails();
                break;
                
            case 3:
                System.out.println("Thank you for using the Taxi Booking System!");
                break;
                
            default:
                System.out.println("Invalid option, please try again.");
                break;
            }
        }
    }
}









 class BookingService {
    private ArrayList<Taxi> taxis;
    private ArrayList<Booking> bookings;
    boolean isFree;
    int numTaxis;
	int dropTime;
    public BookingService(int numTaxis) {
        this.numTaxis = numTaxis;
        taxis = new ArrayList<Taxi>();
        for (int i = 1; i <= numTaxis; i++) {
            taxis.add(new Taxi(i, "A"));
        }
        bookings = new ArrayList<Booking>();
    }

    public String bookTaxi(int customerId, String pickupPoint, String dropPoint, int pickupTime) {
        Taxi taxi = getFreeTaxiAtPickupPoint(pickupPoint, pickupTime);
        if (taxi != null) {
            int distance = getDistance(pickupPoint,dropPoint);
            dropTime =pickupTime+( Math.abs((int) pickupPoint.charAt(0) - (int) dropPoint.charAt(0)));
            double tAmount=calculateFare(distance);
            taxi.addEarnings(tAmount);
            Booking booking = new Booking(taxi.getId(), customerId, pickupPoint, dropPoint, pickupTime,dropTime,tAmount,taxi);
            booking.setTaxi(taxi);
            bookings.add(booking);
            taxi.setLocation(dropPoint);
            return "Taxi can be allotted.\nTaxi-" + taxi.getId() + " is allotted";
        } else {
            return "Booking rejected";
        }
    }

    private Taxi getFreeTaxiAtPickupPoint(String pickupPoint, int pickupTime) {
//  When a customer books a Taxi, a free taxi at that point is allocated
// -If no free taxi is available at that point, a free taxi at the nearest point is allocated.
// -If two taxi’s are free at the same point, one with lower earning is allocated
        Taxi nearestTaxi = null;
        ArrayList<Taxi> freeTaxi = new ArrayList<>();
        boolean isFree;
        for(Taxi taxi: taxis){
			isFree = true;
            for(Booking booking : bookings){
                if (booking.getTaxi() == taxi && pickupTime >= booking.getDropTime() ){
                    freeTaxi.add(taxi);
					isFree = false;
					break;
                }
				else if(booking.getTaxi() == taxi){
					isFree = false;
					break;
				}
            }
			if(isFree){
				freeTaxi.add(taxi);
			}
            
        }
         
		if(freeTaxi.size()==0){
			return nearestTaxi;
		}
		Taxi taxi1 = freeTaxi.get(0);
        int minDistance=getDistance(taxi1.getLocation(), pickupPoint),distance;
		nearestTaxi = taxi1;
            for(int i = 1 ; i < freeTaxi.size() ; i++){
                
            Taxi taxi = freeTaxi.get(i);
            distance = getDistance(taxi.getLocation(), pickupPoint);
            if(distance<minDistance){
                nearestTaxi = taxi;
                minDistance=distance;
            }
            else if(distance==minDistance){
            
                if(nearestTaxi.getEarnings()>=taxi.getEarnings()){
                    nearestTaxi = taxi;
                    minDistance=distance;
                   
                }
                
            }
    }
	return nearestTaxi;
    }

    private int getDistance(String location1, String location2) {
        return Math.abs(location1.charAt(0) - location2.charAt(0)) * 15;
    }

    public void displayTaxiDetails() {
        
        for (Taxi taxi : taxis) {
        System.out.println("Taxi-" + taxi.getId() + "\tTotal Earnings: Rs. " + taxi.getEarnings() + "\n");
        System.out.println("BookingID\tCustomerID\tFrom\tTo\tPickupTime\tDropTime\tAmount\n");
        for (Booking booking : bookings) {
        if (booking.getId() == taxi.getId()) {
        System.out.println(booking.getId() + "\t\t" + booking.getCustomerId() + "\t\t" + booking.getPickupPoint() + "\t" + booking.getDropPoint() + "\t\t" + booking.getPickupTime() + "\t\t" + booking.getDropTime() + "\t\tRs. " + booking.getAmount() + "\n");
        }
        }
    }
        
        }
        
        private double calculateFare(double distance) {
        double fare = 100.0; 
        distance -= 5.0; 
        if (distance > 0) {
            fare += distance * 10.0; 
        }
        return fare;
    }
}

 class Booking {
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