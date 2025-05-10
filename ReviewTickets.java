//import javax.swing.*;
//import java.awt.*;
//
//public class ReviewTickets extends JFrame {
//    private SystemManager systemManager = new SystemManager();
//
//    public ReviewTickets() {
//        setTitle("Review Tickets");
//        setSize(500, 400);
//        setLayout(new BorderLayout());
//
//        JTextArea ticketArea = new JTextArea();
//        ticketArea.setEditable(false);
//
//        StringBuilder tickets = new StringBuilder();
//        for (Reservation reservation : systemManager.getReservations()) {
//            tickets.append("Reservation ID: ").append(reservation.getReservationId()).append("\n");
//            tickets.append("Passenger: ").append(reservation.getPassenger().getName()).append("\n");
//            tickets.append("Train: ").append(reservation.getTrain().getType()).append("\n");
//            tickets.append("Tickets: ").append(reservation.getNumberOfTickets()).append("\n");
//            tickets.append("Start Station: ").append(reservation.getStartStation()).append("\n");
//            tickets.append("End Station: ").append(reservation.getEndStation()).append("\n");
//            tickets.append("-------------------------------\n");
//        }
//
//        ticketArea.setText(tickets.toString());
//
//        JScrollPane scrollPane = new JScrollPane(ticketArea);
//        add(scrollPane, BorderLayout.CENTER);
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//}

////////////////////////////////The Last Version////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReviewTickets extends JFrame {
    public ReviewTickets() {
        setTitle("Review Tickets");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        setLocationRelativeTo(null); //نص الشاشة


        getContentPane().setBackground(new Color(226, 206, 184)); //الخلفية

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

        // Retrieve data from the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT TrainType, SUM(TicketsReserved) AS ReservedSeats, " +
                    "(100 - SUM(TicketsReserved)) AS AvailableSeats " +
                    "FROM Reservations GROUP BY TrainType";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                String trainType = rs.getString("TrainType");
                int reservedSeats = rs.getInt("ReservedSeats");
                int availableSeats = rs.getInt("AvailableSeats");

                result.append("Train: ").append(trainType).append("\n");
                result.append("Reserved Seats: ").append(reservedSeats).append("\n");
                result.append("Available Seats: ").append(availableSeats).append("\n\n");
            }

            textArea.setText(result.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            textArea.setText("Error: " + ex.getMessage());
        }

        setVisible(true);
    }
}
