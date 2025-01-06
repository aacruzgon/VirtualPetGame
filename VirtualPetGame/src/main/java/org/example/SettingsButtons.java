package org.example;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Handles the creation and functionality of settings buttons in the application.
 * @author Alan Cruz
 */
public class SettingsButtons {

    private final NavigationHandler navigationHandler;
    /**
     * Constructor to initialize the SettingsButtons with a NavigationHandler.
     *
     * @param navigationHandler The navigation handler to manage scene transitions.
     */
    public SettingsButtons(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

    /**
     * Creates the settings button UI and its associated functionalities.
     *
     * @param currentScene The current scene where the settings UI will be added.
     * @param stage        The main application stage.
     * @param prevScreen   The screen to return to
     */
    public void createSettingsButton(Scene currentScene, Stage stage, String prevScreen) {
        // Get the root node of the current scene (expected to be an AnchorPane)
        AnchorPane root = (AnchorPane) currentScene.getRoot();

        // Retrieve the VBox container for settings content
        VBox contentContainer = (VBox) root.lookup("#contentContainer");

        // Clear any existing content in the VBox to avoid duplicates
        contentContainer.getChildren().clear();

        // Retrieve the HBox container for the title
        HBox titleContainer = (HBox) root.lookup("#settingsTitleContainer");

        // Ensure both containers are centered horizontally in the root AnchorPane
        AnchorPane.setLeftAnchor(titleContainer, (root.getWidth() - titleContainer.getWidth()) / 2);
        AnchorPane.setRightAnchor(titleContainer, null); // Clear any conflicting right anchor
        titleContainer.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            AnchorPane.setLeftAnchor(titleContainer, (root.getWidth() - newWidth.doubleValue()) / 2);
        });

        AnchorPane.setLeftAnchor(contentContainer, (root.getWidth() - contentContainer.getWidth()) / 2);
        AnchorPane.setRightAnchor(contentContainer, null); // Clear any conflicting right anchor
        contentContainer.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            AnchorPane.setLeftAnchor(contentContainer, (root.getWidth() - newWidth.doubleValue()) / 2);
        });

        // Dynamically position the VBox container (contentContainer) below the HBox (titleContainer)
        titleContainer.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            AnchorPane.setTopAnchor(
                    contentContainer,
                    AnchorPane.getTopAnchor(titleContainer) + newHeight.doubleValue() + 50 // Add 50px spacing below the title
            );
        });

        // Set the initial position of the contentContainer below the titleContainer
        if (titleContainer.getHeight() > 0) {
            AnchorPane.setTopAnchor(
                    contentContainer,
                    AnchorPane.getTopAnchor(titleContainer) + titleContainer.getHeight() + 50 // Add 50px spacing below the title
            );
        }

        // Center-align the content within the VBox
        contentContainer.setAlignment(Pos.CENTER); // Center-align all controls within the VBox

        // Create a Slider to adjust the sound level
        Music music = Music.getInstance(); // Use singleton instance of Music
        Slider soundSlider = new Slider(0, 100, music.getVolume() * 100); // Min 0, Max 100, Default from current volume
        soundSlider.setShowTickLabels(true);
        soundSlider.setShowTickMarks(true);
        soundSlider.setMajorTickUnit(25);
        soundSlider.setBlockIncrement(5);
        soundSlider.getStyleClass().add("settingsSlider");

        // Limit the width of the slider
        soundSlider.setMaxWidth(300); // Set a maximum width for the slider

        // Label to display the current sound level
        Label soundLevelLabel = new Label("Sound Level: " + (int) (music.getVolume() * 100) + "%");
        soundLevelLabel.setStyle("-fx-font-family: 'Press Start 2P'; -fx-font-size: 20px; -fx-text-fill: black;");

        soundSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int soundLevel = newVal.intValue();
            soundLevelLabel.setText("Sound Level: " + soundLevel + "%");
            music.adjustSoundLevel(soundLevel); // Adjust the sound level
        });

        // Checkbox for muting button click sounds
        CheckBox muteClickCheckBox = new CheckBox("Mute Button Clicks");
        muteClickCheckBox.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        muteClickCheckBox.setSelected(music.isButtonClickMuted()); // Reflect the initial state
        muteClickCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            music.setMuteButtonClick(isSelected); // Toggle muting of button clicks
        });

        // Checkbox for muting all sounds
        CheckBox muteCheckBox = new CheckBox("Mute Sound");
        muteCheckBox.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        muteCheckBox.setSelected(music.isMuted()); // Reflect the initial state
        muteCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                music.muteSound(); // Mute the sound
            } else {
                music.unmuteSound(); // Unmute the sound
            }
        });

        // Create the "Parental Controls" button
        Button parentalControls = new Button("Parental Controls");
        parentalControls.getStyleClass().add("parentalControlButton"); // Apply a custom CSS style class

        // Add password prompt logic
        parentalControls.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            promptForPassword(stage, () -> navigationHandler.navigate("ParentalControls"));
        });

        // Add all controls to the contentContainer
        contentContainer.getChildren().addAll(soundSlider, soundLevelLabel, muteCheckBox, muteClickCheckBox, parentalControls);
    }
    /**
     * Prompts the user for a password before accessing sensitive settings like Parental Controls.
     *
     * @param parentStage       The parent stage for the modal dialog.
     * @param onPasswordSuccess The action to perform upon successful password entry.
     */
    private void promptForPassword(Stage parentStage, Runnable onPasswordSuccess) {
        // Create a new stage for the password prompt
        Stage passwordStage = new Stage();
        passwordStage.initOwner(parentStage);
        passwordStage.initModality(Modality.WINDOW_MODAL);
        passwordStage.initStyle(StageStyle.UNDECORATED); // Removes window decorations

        // Create UI components
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setStyle(
                "-fx-background-image: url('/Sprites/passwordBackground.png');" +
                        "-fx-background-size: cover;" +  // Ensures the image covers the container
                        "-fx-background-repeat: no-repeat;" // Prevents image repetition
        );

        Label instruction = new Label("Enter Password:");
        instruction.getStyleClass().add("promptLabel"); // Apply the label style

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("promptPasswordField"); // Apply the password field style

        // Buttons
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("promptButton"); // Apply the button style

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("promptButton"); // Apply the button style

        // Layout buttons horizontally
        HBox buttonContainer = new HBox(10, submitButton, cancelButton);
        buttonContainer.setAlignment(Pos.CENTER);

        // Add components to the container
        container.getChildren().addAll(instruction, passwordField, buttonContainer);

        // Set the scene for the password prompt
        Scene passwordScene = new Scene(container, 400, 200);
        passwordScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Add the stylesheet
        passwordStage.setScene(passwordScene);

        // Button actions
        submitButton.setOnAction(event -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            String enteredPassword = passwordField.getText();
            String correctPassword = "1234"; // Replace with your password logic

            if (enteredPassword.equals(correctPassword)) {
                passwordStage.close(); // Close the prompt
                onPasswordSuccess.run(); // Proceed to the ParentalControls scene
            } else {
                // Temporarily switch to a red background image
                passwordField.setStyle(
                        "-fx-background-image: url('/Sprites/returnSky.png');" +
                                "-fx-background-size: cover;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 2;"
                );

                // Force the style to apply immediately
                passwordField.applyCss();

                // Revert back to the original background image after a delay
                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(e -> {
                    passwordField.setStyle(
                            "-fx-background-image: url('/Sprites/passButton.png');" +
                                    "-fx-background-size: cover;" +
                                    "-fx-border-color: black;" +
                                    "-fx-border-width: 2;"
                    );
                    passwordField.applyCss(); // Ensure the reset style is applied
                });
                pause.play();
            }
        });

        cancelButton.setOnAction(event ->{
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            passwordStage.close();
        });

        // Show the modal
        passwordStage.showAndWait();
    }


}

