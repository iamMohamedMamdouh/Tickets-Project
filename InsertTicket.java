//////////////////////////////The Last Version////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertTicket extends JFrame {
    public InsertTicket() {
            setTitle("Insert Ticket");
            setSize(450, 500);
            setLayout(new GridLayout(9, 2, 5, 5));


            setLocationRelativeTo(null); //نص الشاشة


        getContentPane().setBackground(new Color(226, 206, 184)); //الخلفية

            // Components
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JComboBox<String> trainTypeComboBox = new JComboBox<>(new String[]{
                    "VIP Train", "Talgo Train - Category 1", "Talgo Train - Category 2", "Russian Train"
            });
            JComboBox<String> fromStationComboBox = new JComboBox<>(new String[]{
                    "Aswan", "Luxor", "Qena", "Sohag", "Assiut", "Minya", "Beni Suef", "Cairo", "Tanta", "Damanhour", "Alexandria"
            });
            JComboBox<String> toStationComboBox = new JComboBox<>(new String[]{
                    "Aswan", "Luxor", "Qena", "Sohag", "Assiut", "Minya", "Beni Suef", "Cairo", "Tanta", "Damanhour", "Alexandria"
            });
            JTextField ticketsField = new JTextField();

            JButton submitButton = new JButton("Ticket");
            JButton cancelButton = new JButton("Cancel");

            // Add components
            add(new JLabel("Passenger ID:"));
            add(idField);
            add(new JLabel("Passenger Name:"));
            add(nameField);
            add(new JLabel("Train Type:"));
            add(trainTypeComboBox);
            add(new JLabel("From Station:"));
            add(fromStationComboBox);
            add(new JLabel("To Station:"));
            add(toStationComboBox);
            add(new JLabel("Number of Tickets (1-4):"));
            add(ticketsField);
            add(submitButton);
            add(cancelButton);

            // Action Listeners
            submitButton.addActionListener(e -> {
                String id = idField.getText();
                String name = nameField.getText();
                String trainType = (String) trainTypeComboBox.getSelectedItem();
                String fromStation = (String) fromStationComboBox.getSelectedItem();
                String toStation = (String) toStationComboBox.getSelectedItem();
                String tickets = ticketsField.getText();

                if (id.isEmpty() || name.isEmpty() || tickets.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ticketCount;
                try {
                    ticketCount = Integer.parseInt(tickets);
                    if (ticketCount < 1 || ticketCount > 4) {
                        JOptionPane.showMessageDialog(this, "You can reserve between 1 to 4 tickets only!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number of tickets!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (fromStation.equals(toStation)) {
                    JOptionPane.showMessageDialog(this, "From and To stations must be different!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Calculate the number of stations between the selected stations
                int fromIndex = fromStationComboBox.getSelectedIndex();
                int toIndex = toStationComboBox.getSelectedIndex();
                int stationsCount = Math.abs(toIndex - fromIndex);

                if (stationsCount == 0) {
                    JOptionPane.showMessageDialog(this, "Invalid route selected!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Calculate ticket price
                int pricePerStation = switch (trainType) {
                    case "VIP Train" -> 80;
                    case "Talgo Train - Category 1" -> 65;
                    case "Talgo Train - Category 2" -> 50;
                    case "Russian Train" -> 40;
                    default -> 0;
                };
                int totalPrice = pricePerStation * stationsCount * ticketCount;

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO Reservations (PassengerID, PassengerName, TrainType, FromStation, ToStation, TicketsReserved, TotalPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, id);
                    stmt.setString(2, name);
                    stmt.setString(3, trainType);
                    stmt.setString(4, fromStation);
                    stmt.setString(5, toStation);
                    stmt.setInt(6, ticketCount);
                    stmt.setInt(7, totalPrice);

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Ticket reserved successfully! Total Price: " + totalPrice + " EGP");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelButton.addActionListener(e -> dispose());

            setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(InsertTicket::new);
        }
    }
