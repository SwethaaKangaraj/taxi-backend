import java.util.*;
import java.io.*;
public class BookingService {
    private ArrayList<Taxi> taxis;
    private ArrayList<Booking> bookings;
    boolean isFree;
    int numTaxis;
    int dropTime;
    public BookingService(int numTaxis) {
        this.numTaxis = numTaxis;
        taxis = new ArrayList<Taxi>();
        for (int i = numTaxis; i >=1; i--) {
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
        System.out.println(booking.getId() + "\t\t" + booking.getCustomerId() + "\t\t" + booking.getPickupPoint() + "\t" + booking.getDropPoint() + "\t\t" + booking.getPickupTime() + "\t  " + booking.getDropTime() + "\t\tRs. " + booking.getAmount() + "\n");
        }
        }
    }
}
    public void fileHandling(){
        File file = new File("invoice.txt");
                 try{
                    file.createNewFile();
                    BufferedWriter bw = new BufferedWriter(new FileWriter("invoice.txt"));
                        for (Taxi taxi : taxis) {
                            bw.write("Taxi-" + taxi.getId() + "\tTotal Earnings: Rs. " + taxi.getEarnings() + "\n");
                            bw.write("BookingID\tCustomerID\tFrom\tTo\tPickupTime\tDropTime\tAmount\n");
                            for (Booking booking : bookings) {
                            if (booking.getId() == taxi.getId()) {
                            bw.write(booking.getId() + "\t\t" + booking.getCustomerId() + "\t\t" + booking.getPickupPoint() + "\t" + booking.getDropPoint() + "\t\t" + booking.getPickupTime() + "\t  " + booking.getDropTime() + "\t\tRs. " + booking.getAmount() + "\n");
                            }
                            }
                    }
                    bw.flush();
                    bw.close();
                 }
                 catch(Exception e){
                        System.out.println(e);
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

 