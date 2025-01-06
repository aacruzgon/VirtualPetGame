package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * Represents the tutorial view in the application. This class is responsible for 
 * creating and configuring the tutorial scene, including setting up the background image 
 * to adapt to the screen resolution.
 * @author Arianna Song
 */
public class Tutorial {
    /**
     * The background image for the tutorial, loaded from the FXML file.
     * It is dynamically resized to fit the scene's dimensions.
     */
    @FXML
    private ImageView backgroundImage;

    /**
     * Creates and configures the tutorial scene. Loads the layout from the associated 
     * FXML file and binds the background image dimensions to the parent pane's size.
     *
     * @param primaryStage the primary stage where the scene will be displayed.
     * @return a {@link Scene} configured for the tutorial view.
     * @throws Exception if an error occurs during FXML loading or scene creation.
     */
    @FXML
    public Scene createScene(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tutorial.fxml"));
        loader.setController(this);
        Pane root = loader.load();

        // Bind the fitWidth and fitHeight properties to the parent size (root)
        backgroundImage.fitWidthProperty().bind(root.widthProperty());
        backgroundImage.fitHeightProperty().bind(root.heightProperty());

        // Get screen resolution and set scene
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        // Create and return the scene
        return new Scene(root, screenWidth, screenHeight);
    }
}

