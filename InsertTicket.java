//import javax.swing.*;
//import java.awt.*;
//
//public class InsertTicket extends JFrame {
//    private SystemManager systemManager = new SystemManager();
//
//    public InsertTicket() {
//        setTitle("Insert Ticket");
//        setSize(500, 400);
//        setLayout(new GridLayout(6, 2, 10, 10));
//
//        // Passenger selection
//        JLabel passengerLabel = new JLabel("Passenger Name:");
//        JComboBox<String> passengerComboBox = new JComboBox<>();
//        if (systemManager.getPassengers().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "No passengers available. Please add passengers first.");
//        } else {
//            for (Passenger passenger : systemManager.getPassengers()) {
//                passengerComboBox.addItem(passenger.getName());
//            }
//        }
//
//        // Train selection
//        JLabel trainLabel = new JLabel("Train Code:");
//        JComboBox<Integer> trainComboBox = new JComboBox<>();
//        if (systemManager.getTrains().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "No trains available. Please add trains first.");
//        } else {
//            for (Train2 train : systemManager.getTrains()) {
//                trainComboBox.addItem(train.getTrainCode());
//            }
//        }
//
//        // Start and End Station selection
//        JLabel startStationLabel = new JLabel("Start Station:");
//        JComboBox<String> startStationComboBox = new JComboBox<>();
//        JLabel endStationLabel = new JLabel("End Station:");
//        JComboBox<String> endStationComboBox = new JComboBox<>();
//
//        // Load stations when a train is selected
//        trainComboBox.addActionListener(e -> {
//            startStationComboBox.removeAllItems();
//            endStationComboBox.removeAllItems();
//            Integer selectedTrainCode = (Integer) trainComboBox.getSelectedItem();
//            Train2 selectedTrain = systemManager.getTrainByCode(selectedTrainCode);
//
//            if (selectedTrain != null) {
//                for (Station station : selectedTrain.getStations()) {
//                    startStationComboBox.addItem(station.getName());
//                    endStationComboBox.addItem(station.getName());
//                }
//            }
//        });
//
//        // Number of tickets
//        JLabel ticketsLabel = new JLabel("Number of Tickets:");
//        JTextField ticketsField = new JTextField();
//
//        // Submit button
//        JButton bookButton = new JButton("Book Ticket");
//        bookButton.addActionListener(e -> {
//            try {
//                String passengerName = (String) passengerComboBox.getSelectedItem();
//                Integer trainCode = (Integer) trainComboBox.getSelectedItem();
//                String startStation = (String) startStationComboBox.getSelectedItem();
//                String endStation = (String) endStationComboBox.getSelectedItem();
//                int numTickets = Integer.parseInt(ticketsField.getText());
//
//                // Validation
//                if (numTickets > 4) {
//                    JOptionPane.showMessageDialog(this, "You cannot book more than 4 tickets at a time.");
//                    return;
//                }
//
//                Passenger passenger = systemManager.findPassengerByName(passengerName);
//                Train2 train = systemManager.getTrainByCode(trainCode);
//
//                if (passenger == null || train == null) {
//                    JOptionPane.showMessageDialog(this, "Invalid passenger or train selection.");
//                } else {
//                    systemManager.addReservation(new Reservation(passenger, train, numTickets, startStation, endStation));
//                    JOptionPane.showMessageDialog(this, "Ticket booked successfully!");
//                }
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(this, "Please enter a valid number of tickets.");
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
//            }
//        });
//
//        // Adding components
//        add(passengerLabel);
//        add(passengerComboBox);
//        add(trainLabel);
//        add(trainComboBox);
//        add(startStationLabel);
//        add(startStationComboBox);
//        add(endStationLabel);
//        add(endStationComboBox);
//        add(ticketsLabel);
//        add(ticketsField);
//        add(new JLabel());
//        add(bookButton);
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//}


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
