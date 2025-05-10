////////////////////////////////The Last Version////////////////////////////////////

public class Reservation {

    private static int idCounter = 1;
    private int reservationId;
    private Passenger passenger;
    private Train2 train;
    private int numberOfTickets;
    private String startStation;
    private String endStation;

    public Reservation(Passenger passenger, Train2 train, int numberOfTickets, String startStation, String endStation) {
        this.reservationId = idCounter++;
        this.passenger = passenger;
        this.train = train;
        this.numberOfTickets = numberOfTickets;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public Reservation(Passenger passenger, Train2 train) {
        this.passenger = passenger;
        this.train = train;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Train2    getTrain() {
        return train;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }
}


