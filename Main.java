import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SystemManager systemManager = new SystemManager();
        Scanner scanner = new Scanner(System.in);

        // قائمة المحطات
        List<String> predefinedStations = Arrays.asList("Aswan", "Luxor", "Qena", "Sohag", "Assiut", "Minya", "Beni Suef", "Cairo", "Tanta", "Damanhour", "Alexandria");

        while (true) {
            System.out.println("\n===== Train Station System =====");
            System.out.println("1. Add Train");
            System.out.println("2. Add Passenger");
            System.out.println("3. Make Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View Reservations");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add Train
                    System.out.print("Enter train type: ");
                    String type = scanner.next();
                    System.out.print("Enter train code: ");
                    int trainCode = scanner.nextInt();
                    System.out.print("Enter price per station: ");
                    int pricePerStation = scanner.nextInt();

                    System.out.print("Is air-conditioned? (true/false): ");
                    boolean airConditioned = scanner.nextBoolean();
                    System.out.print("Is wheelchair accessible? (true/false): ");
                    boolean wheelchairAccessible = scanner.nextBoolean();
                    System.out.print("Is spacious? (true/false): ");
                    boolean spacious = scanner.nextBoolean();

                    Train2 train = new Train2(type, trainCode, pricePerStation, airConditioned, wheelchairAccessible, spacious);


                    // إضافة المحطات  إلى القطار
                    for (int i = 0; i < predefinedStations.size(); i++) {
                        int travelTime = (i < predefinedStations.size() - 1) ? 10 : 0; // زمن  10 دقيقة بين المحطات
                        train.addStation(new Station(predefinedStations.get(i), travelTime));
                    }

                    systemManager.addTrain(train);
                    System.out.println("Train added successfully with stations!");
                    break;

                case 2:
                    // Add Passenger
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.next();
                    System.out.print("Enter passenger phone number: ");
                    String phoneNumber = scanner.next();

                    Passenger passenger = new Passenger(passengerName, phoneNumber);
                    systemManager.addPassenger(passenger);
                    System.out.println("Passenger added successfully!");
                    break;

                case 3:
                    // Make Reservation
                    try {
                        System.out.print("Enter passenger name: ");
                        String reservationPassengerName = scanner.next();

                        System.out.print("Enter train code: ");
                        trainCode = scanner.nextInt();

                        System.out.print("Enter number of tickets: ");
                        int numTickets = scanner.nextInt();

                        //  عدد التذاكر
                        if (numTickets > 4) {
                            System.out.println("Error: You cannot book more than 4 tickets at a time!");
                            break;
                        }

                        Train2 reservationTrain = systemManager.getTrainByCode(trainCode);
                        if (reservationTrain == null) {
                            System.out.println("Error: Train not found!");
                            break;
                        }

                        List<Station> trainStations = reservationTrain.getStations();
                        if (trainStations.isEmpty()) {
                            System.out.println("Error: No stations available for this train!");
                            break;
                        }

                        System.out.println("Select start station:");
                        for (int i = 0; i < trainStations.size(); i++) {
                            System.out.println((i + 1) + ". " + trainStations.get(i).getName());
                        }
                        System.out.print("Enter start station number: ");
                        int startStationIndex = scanner.nextInt() - 1;

                        System.out.println("Select end station:");
                        for (int i = 0; i < trainStations.size(); i++) {
                            System.out.println((i + 1) + ". " + trainStations.get(i).getName());
                        }
                        System.out.print("Enter end station number: ");
                        int endStationIndex = scanner.nextInt() - 1;

                        // التحقق من صحة الأرقام
                        if (startStationIndex < 0 || startStationIndex >= trainStations.size() ||
                                endStationIndex < 0 || endStationIndex >= trainStations.size() ||
                                startStationIndex == endStationIndex) {
                            System.out.println("Error: Invalid station selection!");
                            break;
                        }

                        // التحقق من الترتيب وتحديد المحطتين
                        int minIndex = Math.min(startStationIndex, endStationIndex);
                        int maxIndex = Math.max(startStationIndex, endStationIndex);

                        String startStation = trainStations.get(startStationIndex).getName();
                        String endStation = trainStations.get(endStationIndex).getName();

                        // حساب وقت السفر بين المحطتين
                        int travelTime = 0;
                        for (int i = minIndex; i < maxIndex; i++) {
                            travelTime += trainStations.get(i).getTravelTime();
                        }

                        Passenger reservationPassenger = systemManager.findPassengerByName(reservationPassengerName);
                        if (reservationPassenger == null) {
                            System.out.println("Error: Passenger not found!");
                        } else {
                            Reservation reservation = new Reservation(reservationPassenger, reservationTrain, numTickets, startStation, endStation);
                            systemManager.addReservation(reservation);

                            LocalDateTime reservationTime = LocalDateTime.now();
                            LocalDateTime arrivalTime = reservationTime.plusMinutes(travelTime);

                            System.out.println("Reservation made successfully!");
                            System.out.println("Travel time: " + travelTime + " minutes");
                            System.out.println("Reservation Time: " + reservationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            System.out.println("Arrival Time: " + arrivalTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;



                case 4:
                    // Cancel Reservation
                    System.out.print("Enter reservation ID to cancel: ");
                    int reservationId = scanner.nextInt();
                    if (systemManager.cancelReservation(reservationId)) {
                        System.out.println("Reservation canceled successfully!");
                    } else {
                        System.out.println("Error: Reservation not found!");
                    }
                    break;

                case 5:
                    // View Reservations
                    if (systemManager.getReservations().isEmpty()) {
                        System.out.println("Error: No reservations available to display.");
                        break;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    for (Reservation reservation : systemManager.getReservations()) {
                        System.out.println("\n===== Reservation Details =====");
                        System.out.println("Reservation ID: " + reservation.getReservationId());
                        System.out.println("Passenger: " + reservation.getPassenger().getName());
                        System.out.println("Train: " + reservation.getTrain().getType());
                        System.out.println("Tickets: " + reservation.getNumberOfTickets());
                        System.out.println("Start Station: " + reservation.getStartStation());
                        System.out.println("End Station: " + reservation.getEndStation());
                        System.out.println("Reservation Time: " + LocalDateTime.now().format(formatter));
                    }
                    break;

                case 6:
                    // Exit
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}





//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//public class Main {
//    public static void main(String[] args) {
//        SystemManager systemManager = new SystemManager();
//        Scanner scanner = new Scanner(System.in);
//
//        List<String> predefinedStations = Arrays.asList("Aswan", "Luxor", "Qena", "Sohag", "Assiut", "Minya", "Beni Suef", "Cairo", "Tanta", "Damanhour", "Alexandria");
//
//        while (true) {
//            System.out.println("\n===== Train Station System =====");
//            System.out.println("1. Add Train");
//            System.out.println("2. Add Station to Train");
//            System.out.println("3. Add Passenger");
//            System.out.println("4. Make Reservation");
//            System.out.println("5. Cancel Reservation");
//            System.out.println("6. View Reservations");
//            System.out.println("7. Exit");
//            System.out.print("Enter your choice: ");
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    // Add Train
//                    System.out.print("Enter train type: ");
//                    String type = scanner.next();
//                    System.out.print("Enter train code: ");
//                    int trainCode = scanner.nextInt();
//                    System.out.print("Enter price per station: ");
//                    int pricePerStation = scanner.nextInt();
//
//                    Train train = new Train(type, trainCode, pricePerStation);
//                    systemManager.addTrain(train);
//                    System.out.println("Train added successfully!");
//                    break;
//
//                case 2:
//                    // Add Station to Train
//                    System.out.print("Enter train code: ");
//                    trainCode = scanner.nextInt();
//                    Train selectedTrain = systemManager.getTrainByCode(trainCode);
//
//                    if (selectedTrain == null) {
//                        System.out.println("Error: Train not found!");
//                    } else {
//                        System.out.println("Select a station from the list:");
//                        for (int i = 0; i < predefinedStations.size(); i++) {
//                            System.out.println((i + 1) + ". " + predefinedStations.get(i));
//                        }
//                        System.out.print("Enter station number: ");
//                        int stationChoice = scanner.nextInt();
//
//                        if (stationChoice < 1 || stationChoice > predefinedStations.size()) {
//                            System.out.println("Error: Invalid station choice!");
//                        } else {
//                            String stationName = predefinedStations.get(stationChoice - 1);
//                            System.out.print("Enter travel time to next station (in minutes): ");
//                            int travelTime = scanner.nextInt();
//
//                            selectedTrain.addStation(new Station(stationName, travelTime));
//                            System.out.println("Station added successfully!");
//                        }
//                    }
//                    break;
//
//                case 3:
//                    // Add Passenger
//                    System.out.print("Enter passenger name: ");
//                    String passengerName = scanner.next();
//                    System.out.print("Enter passenger phone number: ");
//                    String phoneNumber = scanner.next();
//
//                    Passenger passenger = new Passenger(passengerName, phoneNumber);
//                    systemManager.addPassenger(passenger);
//                    System.out.println("Passenger added successfully!");
//                    break;
//
//                case 4:
//                    // Make Reservation
//                    try {
//                        System.out.print("Enter passenger name: ");
//                        String reservationPassengerName = scanner.next();
//
//                        System.out.print("Enter train code: ");
//                        trainCode = scanner.nextInt();
//
//                        System.out.print("Enter number of tickets: ");
//                        int numTickets = scanner.nextInt();
//
//                        Train reservationTrain = systemManager.getTrainByCode(trainCode);
//                        if (reservationTrain == null) {
//                            System.out.println("Error: Train not found!");
//                            break;
//                        }
//
//                        List<Station> trainStations = reservationTrain.getStations();
//                        if (trainStations.isEmpty()) {
//                            System.out.println("Error: No stations available for this train!");
//                            break;
//                        }
//
//                        System.out.println("Select start station:");
//                        for (int i = 0; i < trainStations.size(); i++) {
//                            System.out.println((i + 1) + ". " + trainStations.get(i).getName());
//                        }
//                        System.out.print("Enter station number: ");
//                        int startStationIndex = scanner.nextInt() - 1;
//
//                        System.out.println("Select end station:");
//                        for (int i = 0; i < trainStations.size(); i++) {
//                            System.out.println((i + 1) + ". " + trainStations.get(i).getName());
//                        }
//                        System.out.print("Enter station number: ");
//                        int endStationIndex = scanner.nextInt() - 1;
//
//                        if (startStationIndex < 0 || endStationIndex < 0 ||
//                                startStationIndex >= trainStations.size() ||
//                                endStationIndex >= trainStations.size() ||
//                                startStationIndex >= endStationIndex) {
//                            System.out.println("Error: Invalid station selection!");
//                            break;
//                        }
//
//                        String startStation = trainStations.get(startStationIndex).getName();
//                        String endStation = trainStations.get(endStationIndex).getName();
//
//                        Passenger reservationPassenger = systemManager.findPassengerByName(reservationPassengerName);
//                        if (reservationPassenger == null) {
//                            System.out.println("Error: Passenger not found!");
//                        } else {
//                            int travelTime = reservationTrain.getTravelTime(startStation, endStation);
//                            if (travelTime == -1) {
//                                System.out.println("Error: Invalid stations selected!");
//                            } else {
//                                Reservation reservation = new Reservation(reservationPassenger, reservationTrain, numTickets, startStation, endStation);
//                                systemManager.addReservation(reservation);
//                                System.out.println("Reservation made successfully! Travel time: " + travelTime + " minutes.");
//                            }
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Error: " + e.getMessage());
//                    }
//                    break;
//
//
//                case 5:
//                    // Cancel Reservation
//                    System.out.print("Enter reservation ID to cancel: ");
//                    int reservationId = scanner.nextInt();
//                    if (systemManager.cancelReservation(reservationId)) {
//                        System.out.println("Reservation canceled successfully!");
//                    } else {
//                        System.out.println("Error: Reservation not found!");
//                    }
//                    break;
//
//                case 6:
//                    // View Reservations
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    for (Reservation reservation : systemManager.getReservations()) {
//                        System.out.println("\n===== Reservation Details =====");
//                        System.out.println("Reservation ID: " + reservation.getReservationId());
//                        System.out.println("Passenger: " + reservation.getPassenger().getName());
//                        System.out.println("Train: " + reservation.getTrain().getType());
//                        System.out.println("Tickets: " + reservation.getNumberOfTickets());
//                        System.out.println("Start Station: " + reservation.getStartStation());
//                        System.out.println("End Station: " + reservation.getEndStation());
//                        System.out.println("Reservation Time: " + LocalDateTime.now().format(formatter));
//                    }
//                    break;
//
//                case 7:
//                    // Exit
//                    System.out.println("Exiting system. Goodbye!");
//                    scanner.close();
//                    return;
//
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        }
//    }
//}







