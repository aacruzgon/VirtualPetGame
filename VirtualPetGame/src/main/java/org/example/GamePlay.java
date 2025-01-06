package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * The {@code GamePlay} class manages the gameplay screen.
 * It extends {@code PetMath} and is responsible for loading the gameplay UI,
 * dynamically scaling components, and adjusting their positions based on the background image's size.
 * @author Arianna Song
 */
public class GamePlay extends PetMath{

    @FXML
    private ImageView gameplayBackground;
    @FXML
    private StackPane stack_gameplay;
    @FXML
    private AnchorPane anchor_pet, anchor_score;
    @FXML
    private VBox vbox_images, vbox_buttons, vbox_bars, vbox_states;
    @FXML
    private ProgressBar bar_health, bar_fullness, bar_happiness, bar_sleep;

    /**
     * Creates and initializes the gameplay scene.
     *
     * <p>This method loads the {@code GamePlay.fxml} file, binds the size of the
     * background image to the parent container, and adjusts the positions of
     * various UI components relative to the scaled background image.</p>
     *
     * @param primaryStage the primary stage of the application.
     * @return a {@code Scene} object representing the gameplay UI.
     * @throws Exception if an error occurs during FXML loading or scene creation.
     */
    @FXML
    public Scene createScene(Stage primaryStage) throws Exception{

        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GamePlay.fxml"));
        loader.setController(this);
        Pane root = loader.load();

        // Get the original dimensions of the background image (before scaling)
        double originalBackgroundWidth = gameplayBackground.getImage().getWidth();
        double originalBackgroundHeight = gameplayBackground.getImage().getHeight();


        // Bind the fitWidth and fitHeight properties to the parent size (root)
        gameplayBackground.fitWidthProperty().bind(root.widthProperty());
        gameplayBackground.fitHeightProperty().bind(root.heightProperty());

        // Listen for changes in the background's scaling
        gameplayBackground.fitWidthProperty().addListener((observable, oldValue, newValue) -> {
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight,0.0, 51.0, stack_gameplay);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 772.0, 654.0, anchor_pet);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 1588.0, 113.0, vbox_bars);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 800.0, 115.0, anchor_score);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 1718.0, 485.0, vbox_states);

        });

        gameplayBackground.fitHeightProperty().addListener((observable, oldValue, newValue) -> {
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 0.0, 51.0, stack_gameplay);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 772.0, 654.0, anchor_pet);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 1588.0, 113.0, vbox_bars);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 800.0, 115.0, anchor_score);
            updatePosition(gameplayBackground, originalBackgroundWidth, originalBackgroundHeight, 1718.0, 485.0, vbox_states);
        });

        // Get screen resolution and set scene
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        return new Scene(root, screenWidth, screenHeight);
    }


}