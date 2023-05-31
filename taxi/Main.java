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
                bookingService.fileHandling();
                System.out.println("Thank you for using the Taxi Booking System!");
                break;
           
            default:
                System.out.println("Invalid option, please try again.");
                break;
            }
            if(option==3){
                break;
            }
        }
    }
}