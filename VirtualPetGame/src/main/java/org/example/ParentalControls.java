/**
 * Represents the parental controls view in the application.
 *
 * <p>The {@code ParentalControls} class is responsible for creating and managing
 * the parental controls scene. It dynamically adjusts the layout of the UI
 * elements based on the screen resolution and resizes the background image to
 * fit the parent pane.</p>
 *
 * @author JaeHoon Jung
 */
package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ParentalControls {

    @FXML
    private ImageView backgroundImage; // The background image for the parental controls view

    /**
     * Creates and initializes the parental controls scene.
     *
     * <p>This method loads the {@code ParentalControl.fxml} file, binds the background
     * image's dimensions to the parent container, and adjusts the layout to
     * dynamically fit the screen resolution.</p>
     *
     * @param primaryStage the primary stage of the application
     * @return the created {@code Scene} object for the parental controls view
     * @throws Exception if the {@code ParentalControl.fxml} file cannot be loaded
     */
    @FXML
    public Scene createScene(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParentalControl.fxml"));
        loader.setController(this);
        Pane root = loader.load();

        // Get the original dimensions of the background image (before scaling)
        double originalBackgroundWidth = backgroundImage.getImage().getWidth();
        double originalBackgroundHeight = backgroundImage.getImage().getHeight();

        // Bind the fitWidth and fitHeight properties to the parent size (root)
        backgroundImage.fitWidthProperty().bind(root.widthProperty());
        backgroundImage.fitHeightProperty().bind(root.heightProperty());

        // Get screen resolution and set the scene
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // Create and return the scene
        return new Scene(root, screenWidth, screenHeight);
    }
}
