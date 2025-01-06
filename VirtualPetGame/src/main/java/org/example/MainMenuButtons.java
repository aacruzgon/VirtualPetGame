package org.example;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.time.LocalTime;

/**
 * The {@code MainMenuButtons} class is responsible for creating and managing the main menu buttons.
 * It includes button actions for navigating to different screens
 * such as New Game, Load Game, Tutorial, Settings, and Exit.
 * @author Arianna Song
 * @author Alan Cruz
 */
public class MainMenuButtons {

    private final NavigationHandler navigationHandler;
    private final VirtualPetGame virtualPetGame; // Reference to VirtualPetGame

    /**
     * Constructs a new {@code MainMenuButtons} instance.
     *
     * @param navigationHandler the {@code NavigationHandler} used for screen navigation.
     * @param virtualPetGame the {@code VirtualPetGame} instance for handling application logic.
     */
    public MainMenuButtons(NavigationHandler navigationHandler, VirtualPetGame virtualPetGame) {
        this.navigationHandler = navigationHandler;
        this.virtualPetGame = virtualPetGame; // Initialize the reference
    }

    /**
     * Creates the main menu buttons and adds them to the specified {@code Scene}.
     * The buttons include: New Game, Load Game, Tutorial, Settings, Exit
     *
     * @param currentScene the {@code Scene} where the buttons will be added.
     * @param gameSettings the {@code GameSettings} used for parental control checks.
     */
    public void createMainMenuButtons(Scene currentScene, GameSettings gameSettings) {
        // Get the root node of the current scene (expected to be an AnchorPane)
        AnchorPane root = (AnchorPane) currentScene.getRoot();

        // Retrieve the VBox container with buttons from the .fxml
        VBox buttonContainer = (VBox) root.lookup("#buttonContainer");

        // Clear any existing buttons (if you plan to rebuild dynamically; otherwise, skip this)
        buttonContainer.getChildren().clear();

        // Create buttons with actions
        Button newGameButton = new Button("New Game");
        newGameButton.getStyleClass().add("mainMenuButton");
        newGameButton.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            if (!isWithinAllowedPlayHours(gameSettings)) {
                showPlayTimeRestrictedAlert();
                return;
            }
            navigationHandler.navigate("NewGame");
        });

        Button loadGameButton = new Button("Load Game");
        loadGameButton.getStyleClass().add("mainMenuButton");
        loadGameButton.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            if (!isWithinAllowedPlayHours(gameSettings)) {
                showPlayTimeRestrictedAlert();
                return;
            }
            navigationHandler.navigate("LoadGame");
        });

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.getStyleClass().add("mainMenuButton");
        tutorialButton.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            navigationHandler.navigate("Tutorial");
        });

        Button settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("mainMenuButton");
        settingsButton.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            navigationHandler.navigate("Settings");
        });

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("mainMenuButton");
        exitButton.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            virtualPetGame.handleStopLogic();
            System.exit(0);
        });

        // Add buttons to the container
        buttonContainer.getChildren().addAll(
                newGameButton,
                loadGameButton,
                tutorialButton,
                settingsButton,
                exitButton
        );

        // Ensure the buttonContainer is focused (optional)
        buttonContainer.requestFocus();
    }



    /**
     * Checks if the current time is within the allowed play hours based on parental controls.
     *
     * @param gameSettings the {@code GameSettings} containing parental control information.
     * @return {@code true} if the current time is within the allowed hours, otherwise {@code false}.
     */
    private boolean isWithinAllowedPlayHours(GameSettings gameSettings) {
        if (!gameSettings.getSettings().getParentalControls().isEnabled()) return true;

        LocalTime currentTime = LocalTime.now();
        LocalTime start = LocalTime.parse(gameSettings.getSettings().getParentalControls().getAllowedPlayHours().getStart());
        LocalTime end = LocalTime.parse(gameSettings.getSettings().getParentalControls().getAllowedPlayHours().getEnd());

        return start.isBefore(end)
                ? currentTime.isAfter(start) && currentTime.isBefore(end)
                : currentTime.isAfter(start) || currentTime.isBefore(end);
    }

    /**
     * Displays an alert when the user tries to play during restricted hours.
     */
    private void showPlayTimeRestrictedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Come Back Later!");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.UNDECORATED); // Remove the default alert decorations
        alert.setTitle("Restricted Play");
        alert.setHeaderText("Playtime Restricted!");

        // Apply custom CSS
        String css = getClass().getResource("/styles.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(css); // Load the stylesheet
        alert.getDialogPane().getStyleClass().add("custom-alert"); // Add the custom style class

        alert.showAndWait();
    }


}
