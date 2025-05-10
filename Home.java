////////////////////////////////The Last Version////////////////////////////////////
import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {
    private ImagePanel backgroundPanel;
    private ImagePanel imagePanel;
    private RoundedButton insertButton, cancelButton, inquireButton, reviewButton, exitButton;

    public Home() {
        setTitle("سكة حديد مصر (س.ح.م)");
        setSize(320, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Background Panel for OIP4
        backgroundPanel = new ImagePanel("D:\\java projects\\Tickets Project\\src\\OIP4.png");
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        // Top Image Panel for OIP2
        imagePanel = new ImagePanel("D:\\java projects\\Tickets Project\\src\\OIP2.png");
        backgroundPanel.add(imagePanel);

        // Buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16); // Larger and bold font
        Color buttonBgColor = new Color(253, 210, 132); // Orange background color
        Color buttonFgColor = Color.BLACK; // White text color

        insertButton = new RoundedButton("Insert Ticket", buttonFont, buttonBgColor, buttonFgColor);
        backgroundPanel.add(insertButton);

        cancelButton = new RoundedButton("Cancel Ticket", buttonFont, buttonBgColor, buttonFgColor);
        backgroundPanel.add(cancelButton);

        inquireButton = new RoundedButton("About Train", buttonFont, buttonBgColor, buttonFgColor);
        backgroundPanel.add(inquireButton);

        reviewButton = new RoundedButton("Review Tickets", buttonFont, buttonBgColor, buttonFgColor);
        backgroundPanel.add(reviewButton);

        exitButton = new RoundedButton("Exit", buttonFont, buttonBgColor, buttonFgColor);
        backgroundPanel.add(exitButton);

        // Event Listeners
        insertButton.addActionListener(e -> new InsertTicket());
        cancelButton.addActionListener(e -> new Cancel());
        inquireButton.addActionListener(e -> new Train());
        reviewButton.addActionListener(e -> new ReviewTickets());
        exitButton.addActionListener(e -> System.exit(0));

        // responsive
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeComponents();
            }
        });

        setVisible(true);
    }

    private void resizeComponents() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        // Adjust the top image panel
        int imageHeight = frameHeight / 3; // Top image occupies 1/3 of the height
        //imagePanel.setBounds(0, 0, frameWidth, imageHeight);

        // Button dimensions and positions
        int buttonWidth = frameWidth / 2;
        int buttonHeight = frameHeight / 12;
        int buttonX = (frameWidth - buttonWidth) / 2; // Center buttons horizontally
        int buttonYStart = imageHeight + 20; // Start placing buttons below the top image
        int buttonGap = 15;

        insertButton.setBounds(buttonX, buttonYStart, buttonWidth, buttonHeight);
        cancelButton.setBounds(buttonX, buttonYStart + buttonHeight + buttonGap, buttonWidth, buttonHeight);
        inquireButton.setBounds(buttonX, buttonYStart + 2 * (buttonHeight + buttonGap), buttonWidth, buttonHeight);
        reviewButton.setBounds(buttonX, buttonYStart + 3 * (buttonHeight + buttonGap), buttonWidth, buttonHeight);
        exitButton.setBounds(buttonX, buttonYStart + 4 * (buttonHeight + buttonGap), buttonWidth, buttonHeight);
    }

    // Custom Rounded Button Class
    private static class RoundedButton extends JButton {
        public RoundedButton(String text, Font font, Color bgColor, Color fgColor) {
            super(text);
            setFont(font);
            setBackground(bgColor);
            setForeground(fgColor);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false); // Needed for custom painting
            setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor to hand
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill background with rounded rectangle
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            // Draw button text
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(getText());
            int stringHeight = fm.getAscent();
            g2.drawString(getText(),
                    (getWidth() - stringWidth) / 2,
                    (getHeight() + stringHeight) / 2 - 2);

            g2.dispose();
            super.paintComponent(g);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }
}
////////////////////////////////V2/////////////////////////////////////////
//public class Home extends JFrame {
//    public Home() {
//        setTitle("سكة حديد مصر (س.ح.م)");
//        setSize(320, 460);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//
//        // Background image
//        ImagePanel backgroundPanel = new ImagePanel("D:\\java projects\\Tickets Project\\src\\OIP2.jpeg");
//        backgroundPanel.setLayout(null);
//        setContentPane(backgroundPanel);
//
//        // Styling
//        Font buttonFont = new Font("Arial", Font.BOLD, 16); // Larger and bold font
//        Color buttonBgColor = new Color(250, 201, 112); // Orange background color
//        Color buttonFgColor = Color.WHITE; // White text color
//
//        // Buttons
//        RoundedButton insertButton = new RoundedButton("Insert Ticket", buttonFont, buttonBgColor, buttonFgColor);
//        insertButton.setBounds(50, 100, 200, 40);
//        add(insertButton);
//
//        RoundedButton cancelButton = new RoundedButton("Cancel Ticket", buttonFont, buttonBgColor, buttonFgColor);
//        cancelButton.setBounds(50, 160, 200, 40);
//        add(cancelButton);
//
//        RoundedButton inquireButton = new RoundedButton("About Train", buttonFont, buttonBgColor, buttonFgColor);
//        inquireButton.setBounds(50, 220, 200, 40);
//        add(inquireButton);
//
//        RoundedButton reviewButton = new RoundedButton("Review Tickets", buttonFont, buttonBgColor, buttonFgColor);
//        reviewButton.setBounds(50, 280, 200, 40);
//        add(reviewButton);
//
//        RoundedButton exitButton = new RoundedButton("Exit", buttonFont, buttonBgColor, buttonFgColor);
//        exitButton.setBounds(50, 340, 200, 40);
//        add(exitButton);
//
//        // Event Listeners
//        insertButton.addActionListener(e -> new InsertTicket());
//        cancelButton.addActionListener(e -> new Cancel());
//        inquireButton.addActionListener(e -> new Train());
//        reviewButton.addActionListener(e -> new ReviewTickets());
//        exitButton.addActionListener(e -> System.exit(0));
//
//        setVisible(true);
//    }
//
//    // Custom Rounded Button Class
//    private static class RoundedButton extends JButton {
//        public RoundedButton(String text, Font font, Color bgColor, Color fgColor) {
//            super(text);
//            setFont(font);
//            setBackground(bgColor);
//            setForeground(fgColor);
//            setFocusPainted(false);
//            setBorderPainted(false);
//            setOpaque(false); // Needed for custom painting
//            setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor to hand
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            Graphics2D g2 = (Graphics2D) g.create();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//            // Fill background with rounded rectangle
//            g2.setColor(getBackground());
//            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
//
//            // Draw button text
//            g2.setColor(getForeground());
//            FontMetrics fm = g2.getFontMetrics();
//            int stringWidth = fm.stringWidth(getText());
//            int stringHeight = fm.getAscent();
//            g2.drawString(getText(),
//                    (getWidth() - stringWidth) / 2,
//                    (getHeight() + stringHeight) / 2 - 1);
//
//            g2.dispose();
//            super.paintComponent(g);
//        }
//    }
//
//    private static class ImagePanel extends JPanel {
//        private Image backgroundImage;
//
//        public ImagePanel(String imagePath) {
//            try {
//                backgroundImage = new ImageIcon(imagePath).getImage();
//            } catch (Exception e) {
//                System.err.println("Error loading image: " + e.getMessage());
//            }
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            if (backgroundImage != null) {
//                // Draw the image to fill the entire panel
//                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Home::new);
//    }
//}
//
////////////////////////////////V1/////////////////////////////////////////
//import javax.swing.*;
//import java.awt.*;
//
//public class Home extends JFrame {
//    private JButton button1;
//
//    public Home() {
//        setTitle("سكة حديد مصر (س.ح.م)");
//        setSize(320, 460);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//
//        // background image
//        ImagePanel backgroundPanel = new ImagePanel("D:\\java projects\\Tickets Project\\src\\OIP2.jpeg");
//        backgroundPanel.setLayout(null);
//        setContentPane(backgroundPanel);
//
//        // Styling
//        Font buttonFont = new Font("Arial", Font.BOLD, 14);
//        Color buttonBgColor = new Color(252, 253, 253); // Light background color
//        Color buttonFgColor = Color.BLACK;
//
//        // Buttons
//        JButton insertButton = StyledButton("Insert Ticket", buttonFont, buttonBgColor, buttonFgColor);
//        insertButton.setBounds(50, 80, 200, 40);
//        add(insertButton);
//
//        JButton cancelButton = StyledButton("Cancel Ticket", buttonFont, buttonBgColor, buttonFgColor);
//        cancelButton.setBounds(50, 130, 200, 40);
//        add(cancelButton);
//
//        JButton inquireButton = StyledButton("About Train", buttonFont, buttonBgColor, buttonFgColor);
//        inquireButton.setBounds(50, 180, 200, 40);
//        add(inquireButton);
//
//
//        JButton reviewButton = StyledButton("Review Tickets", buttonFont, buttonBgColor, buttonFgColor);
//        reviewButton.setBounds(50, 230, 200, 40);
//        add(reviewButton);
//
//        JButton exitButton = StyledButton("Exit", buttonFont, buttonBgColor, buttonFgColor);
//        exitButton.setBounds(50, 280, 200, 40);
//        add(exitButton);
//
//        // Event Listeners
//        insertButton.addActionListener(e -> new InsertTicket());
//        cancelButton.addActionListener(e -> new Cancel());
//        inquireButton.addActionListener(e -> new Train());
//        reviewButton.addActionListener(e -> new ReviewTickets());
//        exitButton.addActionListener(e -> System.exit(0));
//
//        setVisible(true);
//    }
//
//    private JButton StyledButton(String text, Font font, Color bgColor, Color fgColor) {
//        JButton button = new JButton(text);
//        button.setFont(font);
//        button.setBackground(bgColor);
//        button.setForeground(fgColor);
//        button.setFocusPainted(false); // Removes focus border
//        button.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Adds a simple border
//        return button;
//    }
//
//    private static class ImagePanel extends JPanel {
//        private Image backgroundImage;
//
//        public ImagePanel(String imagePath) {
//            try {
//                backgroundImage = new ImageIcon(imagePath).getImage();
//            } catch (Exception e) {
//                System.err.println("Error loading image: " + e.getMessage());
//            }
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            if (backgroundImage != null) {
//                // Draw the image to fill the entire panel
//                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Home::new);
//    }
//}
