package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * This class manages the main game play settings buttons.
 * It includes functionalities like returning to the game, saving the game,
 * navigating to settings or the main menu, and quitting the game.
 *
 * @author Hadeed Pall
 * @author Alan Cruz
 */

public class MainGamePlaySettingsButtons {

    private final NavigationHandler navigationHandler;
    private final VirtualPetGame virtualPetGame;

    public MainGamePlaySettingsButtons(NavigationHandler navigationHandler,VirtualPetGame virtualPetGame ) {
        this.navigationHandler = navigationHandler;
        this.virtualPetGame = virtualPetGame;
    }
    /**
     * Creates and displays the main game play settings popup with various buttons.
     *
     * @param currentScene The current active scene.
     * @param stage        The main application stage.
     */
    public void createMainGamePlaySettingsButton(Scene currentScene, Stage stage) {
        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED); // Removes window decorations

        // Create the popup layout
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        container.setStyle(
                "-fx-background-image: url('/Sprites/returnSky.png');" + // Example background
                        "-fx-background-size: cover;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;"
        );

        // Define button dimensions
        double buttonWidth = 200; // Adjust width to fit the full text
        double buttonHeight = 50; // Adjust height as needed

        // Add buttons to the popup
        Button returnToGame = new Button("Return to Game");
        returnToGame.setPrefWidth(buttonWidth);
        returnToGame.setPrefHeight(buttonHeight);
        returnToGame.getStyleClass().add("maingameplaysettingsbutton");

        Button saveGame = new Button("Save Game");
        saveGame.setPrefWidth(buttonWidth);
        saveGame.setPrefHeight(buttonHeight);
        saveGame.getStyleClass().add("maingameplaysettingsbutton");

        Button settings = new Button("Settings");
        settings.setPrefWidth(buttonWidth);
        settings.setPrefHeight(buttonHeight);
        settings.getStyleClass().add("maingameplaysettingsbutton");

        Button returnToMenu = new Button("Return to Main Menu");
        returnToMenu.setWrapText(true);
        returnToMenu.setAlignment(Pos.CENTER);
        returnToMenu.setStyle("-fx-text-alignment: center;");
        returnToMenu.setPrefWidth(buttonWidth);
        returnToMenu.setPrefHeight(buttonHeight);
        returnToMenu.getStyleClass().add("maingameplaysettingsbutton");

        Button quitGame = new Button("Quit Game");
        quitGame.setPrefWidth(buttonWidth);
        quitGame.setPrefHeight(buttonHeight);
        quitGame.getStyleClass().add("maingameplaysettingsbutton");

        // Add button actions
        returnToGame.setOnAction(e -> popupStage.close());

        saveGame.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            try {
                // Update the game state and save it
                virtualPetGame.saveGame();
                showCustomSaveConfirmation(stage);
                popupStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Failed to save the game.");
            }
        });

        settings.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            navigationHandler.navigate("Settings1"); // Navigate to settings screen
            popupStage.close();
        });

        returnToMenu.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            navigationHandler.navigate("MainMenu"); // Navigate to main menu
            popupStage.close();
        });

        quitGame.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            try {
                virtualPetGame.handleStopLogic(); // Call the stop logic
                System.exit(0); // Exit the application
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }); // Exit the game

        // Add buttons to the container
        container.getChildren().addAll(returnToGame, saveGame, settings, returnToMenu, quitGame);

        // Set the popup scene
        Scene popupScene = new Scene(container, 400, 500); // Adjust popup size if needed
        popupScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Apply styles
        popupStage.setScene(popupScene);

        // Show the popup
        popupStage.showAndWait();
    }

    /**
     * Displays a custom popup to confirm that the game has been successfully saved.
     *
     * @param parentStage The parent stage to position the popup relative to.
     */
    private void showCustomSaveConfirmation(Stage parentStage) {
        Stage customPopupStage = new Stage();
        customPopupStage.initOwner(parentStage);
        customPopupStage.initModality(Modality.APPLICATION_MODAL);
        customPopupStage.initStyle(StageStyle.UNDECORATED);

        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        container.setStyle(
                "-fx-background-image: url('/Sprites/passButton.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;"
        );

        Label messageLabel = new Label("Your game has been successfully saved!");
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setStyle("-fx-text-alignment: center;");
        messageLabel.getStyleClass().add("inventoryLabel");

        Button okButton = new Button("OK");
        okButton.setPrefWidth(100);
        okButton.setPrefHeight(40);
        okButton.getStyleClass().add("promptButton");
        okButton.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            customPopupStage.close();
        });

        container.getChildren().addAll(messageLabel, okButton);

        Scene popupScene = new Scene(container, 400, 200);
        popupScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        customPopupStage.setScene(popupScene);

        customPopupStage.setX(parentStage.getX() + parentStage.getWidth() / 2 - 200);
        customPopupStage.setY(parentStage.getY() + parentStage.getHeight() / 2 - 100);

        customPopupStage.showAndWait();
    }

}
