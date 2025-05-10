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
