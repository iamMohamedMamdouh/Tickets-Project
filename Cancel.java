//import javax.swing.*;
//import java.awt.*;
//
//public class Cancel extends JFrame {
//    private SystemManager systemManager = new SystemManager();
//
//    public Cancel() {
//        setTitle("Cancel Reservation");
//        setSize(300, 200);
//        setLayout(new GridLayout(3, 1, 10, 10));
//
//        JLabel pnrLabel = new JLabel("Enter Reservation ID:");
//        JTextField pnrField = new JTextField();
//        JButton cancelButton = new JButton("Cancel Reservation");
//
//        cancelButton.addActionListener(e -> {
//            int reservationId = Integer.parseInt(pnrField.getText());
//            if (systemManager.cancelReservation(reservationId)) {
//                JOptionPane.showMessageDialog(this, "Reservation canceled successfully!");
//            } else {
//                JOptionPane.showMessageDialog(this, "Reservation not found!");
//            }
//        });
//
//        add(pnrLabel);
//        add(pnrField);
//        add(cancelButton);
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//}


////////////////////////////////The Last Version////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Cancel extends JFrame {
    private JTextField pnrField;
    private JButton cancelButton;

    public Cancel() {
        setTitle("اٍلغاء التذكرة");
        setSize(300, 200);
        setLayout(null);

        Cancel.ImagePanel backgroundPanel = new Cancel.ImagePanel("D:\\java projects\\Tickets Project\\src\\OIP4.png");
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        setLocationRelativeTo(null); //نص الشاشة

        getContentPane().setBackground(new Color(226, 206, 184)); //الخلفية

        JLabel pnrLabel = new JLabel("Enter PNR Number:");
        pnrLabel.setBounds(30, 30, 150, 25);
        pnrLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(pnrLabel);

        // text field style
        pnrField = new JTextField();
        pnrField.setBounds(180, 30, 100, 25);
        pnrField.setBackground(Color.WHITE);
        add(pnrField);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(70, 100, 150, 30);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(252, 236, 236)); // Light blue
        cancelButton.setForeground(Color.BLACK);
        add(cancelButton);

        cancelButton.addActionListener(e -> cancelReservation());

        setVisible(true);
    }


    private void cancelReservation() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Reservations WHERE ReservationID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(pnrField.getText()));
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Reservation canceled successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No reservation found with the given PNR.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error canceling reservation: " + e.getMessage());
        }
    }
    private static class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw the image to fill the panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
