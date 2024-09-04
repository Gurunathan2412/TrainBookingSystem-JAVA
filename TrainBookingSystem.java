import java.util.*;

class Train {
    String trainName;
    int trainNumber;
    int totalSeats;
    int availableSeats;
    double fare;

    public Train(String trainName, int trainNumber, int totalSeats, double fare) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare = fare;
    }
}

class Ticket {
    String passengerName;
    int trainNumber;
    int seatCount;
    double totalFare;

    public Ticket(String passengerName, int trainNumber, int seatCount, double totalFare) {
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.seatCount = seatCount;
        this.totalFare = totalFare;
    }

    @Override
    public String toString() {
        return "Passenger Name: " + passengerName + ", Train Number: " + trainNumber + ", Seats Booked: " + seatCount +
                ", Total Fare: " + totalFare;
    }
}

public class TrainBookingSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<Train> trains = new ArrayList<>();
    static List<Ticket> tickets = new ArrayList<>();

    public static void main(String[] args) {
        initializeTrains();
        int choice;

        do {
            System.out.println("\n--- Train Ticket Booking System ---");
            System.out.println("1. View Trains");
            System.out.println("2. Book Tickets");
            System.out.println("3. Cancel Tickets");
            System.out.println("4. View Booked Tickets");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTrains();
                    break;
                case 2:
                    bookTickets();
                    break;
                case 3:
                    cancelTickets();
                    break;
                case 4:
                    viewBookedTickets();
                    break;
                case 5:
                    System.out.println("Thank you for using the Train Ticket Booking System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
    }

    static void initializeTrains() {
        trains.add(new Train("Express A", 101, 100, 500.0));
        trains.add(new Train("Express B", 102, 150, 750.0));
        trains.add(new Train("Express C", 103, 200, 1000.0));
    }

    static void viewTrains() {
        System.out.println("\nAvailable Trains:");
        for (Train train : trains) {
            System.out.println("Train Name: " + train.trainName + ", Train Number: " + train.trainNumber +
                    ", Available Seats: " + train.availableSeats + ", Fare per Seat: " + train.fare);
        }
    }

    static void bookTickets() {
        System.out.print("\nEnter Train Number: ");
        int trainNumber = scanner.nextInt();
        Train selectedTrain = null;

        for (Train train : trains) {
            if (train.trainNumber == trainNumber) {
                selectedTrain = train;
                break;
            }
        }

        if (selectedTrain == null) {
            System.out.println("Invalid Train Number.");
            return;
        }

        System.out.print("Enter Passenger Name: ");
        scanner.nextLine(); // Consume newline
        String passengerName = scanner.nextLine();
        System.out.print("Enter Number of Seats to Book: ");
        int seatCount = scanner.nextInt();

        if (seatCount > selectedTrain.availableSeats) {
            System.out.println("Not enough seats available.");
            return;
        }

        double totalFare = seatCount * selectedTrain.fare;
        selectedTrain.availableSeats -= seatCount;
        tickets.add(new Ticket(passengerName, trainNumber, seatCount, totalFare));

        System.out.println("Tickets booked successfully. Total Fare: " + totalFare);
    }

    static void cancelTickets() {
        System.out.print("\nEnter Passenger Name: ");
        scanner.nextLine(); // Consume newline
        String passengerName = scanner.nextLine();

        boolean found = false;
        Iterator<Ticket> iterator = tickets.iterator();
        while (iterator.hasNext()) {
            Ticket ticket = iterator.next();
            if (ticket.passengerName.equals(passengerName)) {
                iterator.remove();
                for (Train train : trains) {
                    if (train.trainNumber == ticket.trainNumber) {
                        train.availableSeats += ticket.seatCount;
                        break;
                    }
                }
                System.out.println("Ticket canceled successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Ticket not found.");
        }
    }

    static void viewBookedTickets() {
        System.out.println("\nBooked Tickets:");
        if (tickets.isEmpty()) {
            System.out.println("No tickets booked yet.");
        } else {
            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }
        }
    }
}
