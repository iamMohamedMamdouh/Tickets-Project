//import javax.swing.*;
//import java.awt.*;
//import java.util.Arrays;
//
//public class Train extends JFrame {
//    private SystemManager systemManager = new SystemManager();
//
//    public Train() {
//        setTitle("Add Train");
//        setSize(400, 300);
//        setLayout(new GridLayout(5, 2, 10, 10));
//
//        JLabel typeLabel = new JLabel("Train Type:");
//        JTextField typeField = new JTextField();
//        JLabel codeLabel = new JLabel("Train Code:");
//        JTextField codeField = new JTextField();
//        JLabel priceLabel = new JLabel("Price Per Station:");
//        JTextField priceField = new JTextField();
//
//        JButton addButton = new JButton("Add Train");
//        addButton.addActionListener(e -> {
//            String type = typeField.getText();
//            int trainCode = Integer.parseInt(codeField.getText());
//            int pricePerStation = Integer.parseInt(priceField.getText());
//
//            Train2 train = new Train2(type, trainCode, pricePerStation);
//            train.setStations(Arrays.asList("Aswan", "Luxor", "Cairo", "Alexandria"));
//            systemManager.addTrain(train);
//
//            JOptionPane.showMessageDialog(this, "Train added successfully!");
//        });
//
//        add(typeLabel);
//        add(typeField);
//        add(codeLabel);
//        add(codeField);
//        add(priceLabel);
//        add(priceField);
//        add(new JLabel());
//        add(addButton);
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//}


//////////////////////////////////The Last Version////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Train extends JFrame {
    private JTextField trainCodeField;
    private JTextArea resultArea;
    private JButton inquireButton;

    public Train() {
        setTitle("Train Inquiry");
        setSize(400, 300);
        setLayout(null);

        Train.ImagePanel backgroundPanel = new Train.ImagePanel("D:\\java projects\\Tickets Project\\src\\OIP4.png");
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        setLocationRelativeTo(null); //نص الشاشة


        getContentPane().setBackground(new Color(226, 206, 184)); //الخلفية

        JLabel trainCodeLabel = new JLabel("Enter Train Code:");
        trainCodeLabel.setBounds(30, 30, 150, 25);
        trainCodeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(trainCodeLabel);


        trainCodeField = new JTextField();
        trainCodeField.setBounds(180, 30, 150, 25);
        trainCodeField.setBackground(Color.WHITE);
        add(trainCodeField);


        resultArea = new JTextArea();
        resultArea.setBounds(30, 70, 320, 160);
        resultArea.setBackground(Color.WHITE);
        resultArea.setEditable(false);
        add(resultArea);

        inquireButton = new JButton("Inquire");
        inquireButton.setBounds(150, 230, 100, 30);
        inquireButton.setFont(new Font("Arial", Font.BOLD, 14));
        inquireButton.setBackground(new  Color(252, 236, 236)); // Light blue
        inquireButton.setForeground(Color.BLACK);
        add(inquireButton);

        inquireButton.addActionListener(e -> fetchTrainDetails());

        setVisible(true);
    }

    private void fetchTrainDetails() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Trains WHERE TrainCode = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(trainCodeField.getText()));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                resultArea.setText("Train Code: " + resultSet.getInt("TrainCode") +
                        "\nTrain Type: " + resultSet.getString("TrainType") +
                        "\nDeparture: " + resultSet.getTime("DepartureTime") +
                        "\nArrival: " + resultSet.getTime("ArrivalTime") +
                        "\nFeatures: " + resultSet.getString("Features"));
            } else {
                resultArea.setText("No train found with the given code.");
            }
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
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
