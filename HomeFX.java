import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.stage.Stage;

public class HomeFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("سكة حديد مصر (س.ح.م)");

        // Background image
        ImageView background = new ImageView(new Image("file:D:\\java projects\\Tickets Project\\src\\OIP2.jpeg"));
        background.setFitWidth(320);
        background.setFitHeight(460);

        // VBox for buttons
        VBox buttonContainer = new VBox(15); // Spacing of 15 between buttons
//        buttonContainer.setAlignment(Pos.CENTER);

        // Create buttons
        Button insertButton = createStyledButton("Insert Ticket");
        Button cancelButton = createStyledButton("Cancel Ticket");
        Button inquireButton = createStyledButton("About Train");
        Button reviewButton = createStyledButton("Review Tickets");
        Button exitButton = createStyledButton("Exit");

        // Add buttons to container
        buttonContainer.getChildren().addAll(insertButton, cancelButton, inquireButton, reviewButton, exitButton);

        // Event handlers
        insertButton.setOnAction(e -> System.out.println("Insert Ticket Clicked"));
        cancelButton.setOnAction(e -> System.out.println("Cancel Ticket Clicked"));
        inquireButton.setOnAction(e -> System.out.println("About Train Clicked"));
        reviewButton.setOnAction(e -> System.out.println("Review Tickets Clicked"));
        exitButton.setOnAction(e -> System.exit(0));

        // StackPane for layering background and buttons
        StackPane root = new StackPane();
        root.getChildren().addAll(background, buttonContainer);

        // Scene setup
        Scene scene = new Scene(root, 320, 460);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 14));
        button.setStyle("-fx-background-color: #FFA500; " + // Orange background
                "-fx-text-fill: white; " +          // White text
                "-fx-background-radius: 20; " +     // Rounded corners
                "-fx-border-radius: 20;");         // Rounded border
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
