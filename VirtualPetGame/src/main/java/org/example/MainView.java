/**
 * Represents the main view controller for the application.
 *
 * <p>The {@code MainView} class is responsible for initializing and managing the main
 * UI components of the application. It dynamically adjusts the layout, updates
 * component positions, and manages animations for interactive elements.</p>
 *
 * <p>The class extends {@code PetMath} to reuse methods for dynamically updating the
 * positions of UI components based on scaling.</p>
 *
 * @author Alan Cruz
 */
package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainView extends PetMath {

    @FXML
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9, imageView10, backgroundImage;

    @FXML
    private AnchorPane mimitchi, violetchi, lovelitchi, sitmimitchi, anchor_group;
    @FXML
    private AnchorPane virtual, pet, game, orenetchi;
    @FXML
    private HBox titleContainer;
    @FXML
    private VBox buttonContainer;


    private Animations animations;

    /**
     * Creates and initializes the main scene for the application.
     *
     * <p>This method loads the {@code MainView.fxml} file, binds the background
     * image's dimensions to the parent container, and initializes animations.
     * It dynamically adjusts the positions of UI elements based on screen resolution.</p>
     *
     * @param primaryStage the primary stage of the application
     * @return the created {@code Scene} object for the main view
     * @throws Exception if the {@code MainView.fxml} file cannot be loaded
     */
    public Scene createScene(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        loader.setController(this);
        Pane root = loader.load();

        double originalBackgroundWidth = backgroundImage.getImage().getWidth();
        double originalBackgroundHeight = backgroundImage.getImage().getHeight();

        backgroundImage.fitWidthProperty().bind(root.widthProperty());
        backgroundImage.fitHeightProperty().bind(root.heightProperty());

        // Run the position updates on the JavaFX application thread
        Platform.runLater(() -> {
            updatePositions(backgroundImage, originalBackgroundWidth, originalBackgroundHeight);
        });

        // Initialize animations for image views
        animations = new Animations(
                imageView1, imageView2, imageView3, imageView4, imageView5,
                imageView6, imageView7, imageView8, imageView9, imageView10
        );
        animations.play();

        // Get screen resolution and set scene dimensions
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        return new Scene(root, screenWidth, screenHeight);
    }

    /**
     * Updates the positions of UI elements based on the scaling of the background image.
     *
     * <p>This method uses the {@code updatePosition} method inherited from {@code PetMath}
     * to recalculate the positions of various components relative to the resized
     * background image.</p>
     *
     * @param backgroundImage  the background image used as a reference for scaling
     * @param originalWidth    the original width of the background image
     * @param originalHeight   the original height of the background image
     */
    private void updatePositions(ImageView backgroundImage, double originalWidth, double originalHeight) {
        updatePosition(backgroundImage, originalWidth, originalHeight, 338, 643, mimitchi);
        updatePosition(backgroundImage, originalWidth, originalHeight, 1474, 643, violetchi);
        updatePosition(backgroundImage, originalWidth, originalHeight, 106, 649, lovelitchi);
        updatePosition(backgroundImage, originalWidth, originalHeight, 1152, 669, sitmimitchi);
        updatePosition(backgroundImage, originalWidth, originalHeight, 174, 102, virtual);
        updatePosition(backgroundImage, originalWidth, originalHeight, 771, 102, pet);
        updatePosition(backgroundImage, originalWidth, originalHeight, 1084, 102, game);
        updatePosition(backgroundImage, originalWidth, originalHeight, 1387, 283, orenetchi);
        updatePosition(backgroundImage, originalWidth, originalHeight, 27, 128, titleContainer);
        updatePosition(backgroundImage, originalWidth, originalHeight, 699, 263, buttonContainer);
    }
}
