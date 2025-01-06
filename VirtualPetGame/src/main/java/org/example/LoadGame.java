package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The {@code LoadGame} class is responsible for managing the load game screen functionality,
 * including rendering the background and managing the save file UI components.
 *
 * @author Alan Cruz
 */
public class LoadGame extends PetMath {
    @FXML
    private ImageView backgroundImage; // The background image for the load game screen.

    @FXML
    private ScrollPane loadGameScrollPane; // Scroll pane for displaying save files.

    @FXML
    private VBox saveFileContainer, boxx; // Containers for save file entries and other UI elements.

    /**
     * Creates and initializes the load game scene.
     *
     * @param primaryStage The primary stage where the scene will be displayed.
     * @return A configured {@link Scene} object for the load game screen.
     * @throws Exception If an error occurs while loading the FXML file.
     */
    public Scene createScene(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoadGame.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();

        // Get the original dimensions of the background image (before scaling)
        double originalBackgroundWidth = backgroundImage.getImage().getWidth();
        double originalBackgroundHeight = backgroundImage.getImage().getHeight();

        // Bind the fitWidth and fitHeight properties to the parent size (root)
        backgroundImage.fitWidthProperty().bind(root.widthProperty());
        backgroundImage.fitHeightProperty().bind(root.heightProperty());

        // Listen for changes in the background's scaling
        backgroundImage.fitWidthProperty().addListener((observable, oldValue, newValue) -> {
            updatePositions(backgroundImage, originalBackgroundWidth, originalBackgroundHeight);
        });

        backgroundImage.fitHeightProperty().addListener((observable, oldValue, newValue) -> {
            updatePositions(backgroundImage, originalBackgroundWidth, originalBackgroundHeight);
        });

        // Get screen resolution and set scene
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // Create and return the scene
        return new Scene(root, screenWidth, screenHeight);
    }

    /**
     * Updates the positions of UI elements based on the background image's scaling.
     *
     * @param backgroundImage The background image being scaled.
     * @param originalWidth   The original width of the background image.
     * @param originalHeight  The original height of the background image.
     */
    private void updatePositions(ImageView backgroundImage, double originalWidth, double originalHeight) {
        updatePosition(backgroundImage, originalWidth, originalHeight, 102, 203, loadGameScrollPane);
        updatePosition(backgroundImage, originalWidth, originalHeight, 102, 203, boxx);
        updatePosition(backgroundImage, originalWidth, originalHeight, 102, 203, saveFileContainer);
    }
}

