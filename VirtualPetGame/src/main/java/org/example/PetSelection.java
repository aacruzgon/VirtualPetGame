package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The PetSelection class handles the creation and configuration of the Pet Selection scene,
 * allowing players to choose a pet and displaying the corresponding animations and background.
 *
 * <p>
 * @author Bashar Hamo
 * </p>
 */
public class PetSelection extends PetMath{

    @FXML
    private ImageView background_select, kuromametchi, kuromametchi_flipped, lovelitchi, lovelitchi_flipped, mimitchi, mimitchi_flipped, orenetchi, orenetchi_flipped, violetchi, violetchi_flipped;

    @FXML
    private StackPane stack;


    private Animations animations;

    /**
     * Creates and configures the Pet Selection scene. This method sets up bindings
     * for responsive background scaling, initializes pet animations, and adjusts
     * the layout to fit the screen resolution.
     *
     * @param primaryStage the primary stage for the application.
     * @return the configured {@link Scene} for the Pet Selection screen.
     * @throws Exception if there is an issue loading the FXML or configuring the scene.
     */
    @FXML
    public Scene createScene(Stage primaryStage) throws Exception{

        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PetSelection.fxml"));
        loader.setController(this);
        Pane root = loader.load();

        // Get the original dimensions of the background image (before scaling)
        double originalBackgroundWidth = background_select.getImage().getWidth();
        double originalBackgroundHeight = background_select.getImage().getHeight();

        // Bind the fitWidth and fitHeight properties to the parent size (root)
        background_select.fitWidthProperty().bind(root.widthProperty());
        background_select.fitHeightProperty().bind(root.heightProperty());

        // Listen for changes in the background's scaling
        background_select.fitWidthProperty().addListener((observable, oldValue, newValue) -> {
            updatePosition(background_select, originalBackgroundWidth, originalBackgroundHeight,485, 537, stack);
        });

        background_select.fitHeightProperty().addListener((observable, oldValue, newValue) -> {
            updatePosition(background_select, originalBackgroundWidth, originalBackgroundHeight, 485, 537, stack);
        });

        // Initialize animations
        animations = new Animations(
                kuromametchi, kuromametchi_flipped, lovelitchi, lovelitchi_flipped, mimitchi,
                mimitchi_flipped, orenetchi, orenetchi_flipped, violetchi, violetchi_flipped
        );
        animations.play();

        // Get screen resolution and set scene
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        Scene newGameScene = new Scene(root, screenWidth, screenHeight);
        return newGameScene;
    }

}

