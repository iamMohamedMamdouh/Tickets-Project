//import java.util.*;
//
//public class SystemManager {
//    private List<Train2> trains;
//    private List<Reservation> reservations;
//    private List<Passenger> passengers;
//    private int reservationCounter;
//
//    public SystemManager() {
//        trains = new ArrayList<>();
//        reservations = new ArrayList<>();
//        passengers = new ArrayList<>();
//        reservationCounter = 1;
//    }
//
//    // Add a train
//    public void addTrain(Train2 train) {
//        trains.add(train);
//    }
//
//    // Find a train by its code
//    public Train2 getTrainByCode(int trainCode) {
//        for (Train2 train : trains) {
//            if (train.getTrainCode() == trainCode) {
//                return train;
//            }
//        }
//        return null;
//    }
//
//    // Add a passenger
//    public void addPassenger(Passenger passenger) {
//        passengers.add(passenger);
//    }
//
//    // Find a passenger by name
//    public Passenger findPassengerByName(String name) {
//        for (Passenger passenger : passengers) {
//            if (passenger.getName().equalsIgnoreCase(name)) {
//                return passenger;
//            }
//        }
//        return null;
//    }
//
//    // Add a reservation
//    public void addReservation(Reservation reservation) {
//        reservations.add(reservation);
//    }
//
//    // Cancel a reservation by ID
//    public boolean cancelReservation(int reservationId) {
//        return reservations.removeIf(reservation -> reservation.getReservationId() == reservationId);
//    }
//
//    // Get all reservations
//    public List<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public List<Passenger> getPassengers() {
//        return passengers;
//    }
//
//    public List<Train2> getTrains() {
//        return trains;
//    }
//}

//////////////////////////////////The Last Version////////////////////////////////////
import java.util.*;

public class SystemManager {
    private List<Train2> trains;
    private List<Reservation> reservations;
    private List<Passenger> passengers;

    public SystemManager() {
        trains = new ArrayList<>();
        reservations = new ArrayList<>();
        passengers = new ArrayList<>();
    }

    public void addTrain(Train2 train) {
        trains.add(train);
    }

    public Train2 getTrainByCode(int trainCode) {
        for (Train2 train : trains) {
            if (train.getTrainCode() == trainCode) {
                return train;
            }
        }
        return null;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public Passenger findPassengerByName(String name) {
        for (Passenger passenger : passengers) {
            if (passenger.getName().equalsIgnoreCase(name)) {
                return passenger;
            }
        }
        return null;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean cancelReservation(int reservationId) {
        return reservations.removeIf(reservation -> reservation.getReservationId() == reservationId);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}


