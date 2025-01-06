/**
 * Represents the settings view in the application.
 *
 * <p>The {@code Setting} class is responsible for creating and managing
 * the settings scene. It dynamically adjusts the layout of the UI elements
 * based on the screen resolution and resizes the background image to fit
 * the parent container.</p>
 *
 * @author JaeHoon Jung
 */
package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Setting {

    @FXML
    private ImageView backgroundImage; // The background image for the settings view

    /**
     * Creates and initializes the settings scene.
     *
     * <p>This method loads the {@code Settings.fxml} file, binds the background
     * image's dimensions to the parent container, and dynamically adjusts the
     * layout based on the screen resolution.</p>
     *
     * @param primaryStage the primary stage of the application
     * @return the created {@code Scene} object for the settings view
     * @throws Exception if the {@code Settings.fxml} file cannot be loaded
     */
    public Scene createScene(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Settings.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();

        // Get the original dimensions of the background image (before scaling)
        double originalBackgroundWidth = backgroundImage.getImage().getWidth();
        double originalBackgroundHeight = backgroundImage.getImage().getHeight();

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
