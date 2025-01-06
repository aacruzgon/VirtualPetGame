package org.example;


import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;
/**
 * This class handles the creation and functionality of parental control buttons
 * in the game, including enabling/disabling parental controls, setting allowed play hours,
 * displaying session statistics, and managing related UI components.
 *
 * @author Alan Cruz
 */
public class ParentalControlButtons {



    private final NavigationHandler navigationHandler;
    /**
     * Constructor to initialize the parental control buttons.
     *
     * @param navigationHandler The navigation handler for managing screen transitions.
     */
    public ParentalControlButtons(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

    /**
     * Creates and displays the parental control buttons within the current scene.
     *
     * @param currentScene  The current active scene where buttons will be added.
     * @param gameSettings  The game settings object to access and modify parental control data.
     */
    public void createParentalControlsButtons(Scene currentScene, GameSettings gameSettings) {
        // Access the root AnchorPane
        AnchorPane root = (AnchorPane) currentScene.getRoot();

        // Access the HBox (title container)
        HBox titleContainer = (HBox) root.lookup("#settingsTitleContainer");

        // Create a CheckBox for enabling/disabling parental controls
        CheckBox parentalControlToggle = new CheckBox("Enable Parental Controls");
        parentalControlToggle.setSelected(gameSettings.getSettings().getParentalControls().isEnabled());
        parentalControlToggle.setStyle("-fx-font-size: 20px; -fx-text-fill: black;");

        // Add a listener to save the state when toggled
        parentalControlToggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            // Update the state in GameSettings
            gameSettings.getSettings().getParentalControls().setEnabled(isSelected);

            // Save the updated settings to persist changes
            new LoadData().saveGameSettings(gameSettings);

        });

        // Display statistics
        Label totalPlayTimeLabel = new Label("Total Play Time: " + (int) gameSettings.getSettings().getPlayStatistics().getTotalPlayTime() + " mins");
        totalPlayTimeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        Label averageSessionTimeLabel = new Label("Average Session Time: " + (int) gameSettings.getSettings().getPlayStatistics().getAverageSessionTime() + " mins");
        averageSessionTimeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        // Create input fields for allowed play hours
        TextField startTimeField = new TextField(gameSettings.getSettings().getParentalControls().getAllowedPlayHours().getStart());
        startTimeField.setPromptText("Start Time (HH:mm)");
        startTimeField.setStyle("-fx-font-size: 16px;");
        startTimeField.setPrefWidth(150);

        TextField endTimeField = new TextField(gameSettings.getSettings().getParentalControls().getAllowedPlayHours().getEnd());
        endTimeField.setPromptText("End Time (HH:mm)");
        endTimeField.setStyle("-fx-font-size: 16px;");
        endTimeField.setPrefWidth(150);

        // Reset Statistics Button
        Button resetStatsButton = new Button("Reset Statistics");
        resetStatsButton.setStyle("-fx-font-size: 18px; -fx-background-color: #FF9800; -fx-text-fill: white;");
        resetStatsButton.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            gameSettings.getSettings().getPlayStatistics().resetStatistics();
            new LoadData().saveGameSettings(gameSettings); // Persist the reset statistics
            totalPlayTimeLabel.setText("Total Play Time: 0 mins");
            averageSessionTimeLabel.setText("Average Session Time: 0 mins");
            updateLineChart(null, gameSettings); // Clear the chart
        });

        // Save Button
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();

            if (isValidTime(startTime) && isValidTime(endTime)) {
                // Update parental controls settings
                gameSettings.getSettings().getParentalControls().setEnabled(parentalControlToggle.isSelected());
                gameSettings.getSettings().getParentalControls().getAllowedPlayHours().setStart(startTime);
                gameSettings.getSettings().getParentalControls().getAllowedPlayHours().setEnd(endTime);

                // Save updated settings
                new LoadData().saveGameSettings(gameSettings);

                // Temporarily change button text to "Success!"
                String originalText = saveButton.getText();
                saveButton.setText("Success!");
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> saveButton.setText(originalText));
                pause.play();
            } else {
                saveButton.setText("Invalid Format!");
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> saveButton.setText("Save Changes"));
                pause.play();
            }
        });

        // Create the Line Chart for Session Durations
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Session Duration (mins)");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Probability Density");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Session Duration Distribution");
        lineChart.setPrefSize(600, 400);
        lineChart.setLegendVisible(false);

        // Populate Line Chart with existing data
        updateLineChart(lineChart, gameSettings);

        // Add ScrollPane for pets with health = 0
        ScrollPane petHealthScrollPane = createPetHealthCheckScrollPane();

        // Handle allowed play hours
        VBox playHoursCheckContainer = new VBox(10);
        Label playHoursLabel = new Label("Allowed Play Hours: " +
                gameSettings.getSettings().getParentalControls().getAllowedPlayHours().getStart() +
                " - " +
                gameSettings.getSettings().getParentalControls().getAllowedPlayHours().getEnd());
        playHoursLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        playHoursCheckContainer.getChildren().add(playHoursLabel);

        // Layout all elements in a VBox
        VBox leftContainer = new VBox(15, parentalControlToggle, totalPlayTimeLabel, averageSessionTimeLabel, startTimeField, endTimeField, resetStatsButton, saveButton, petHealthScrollPane, playHoursCheckContainer);
        leftContainer.setStyle("-fx-padding: 20px;");

        // Layout the line chart in a VBox
        VBox rightContainer = new VBox(lineChart);
        rightContainer.setStyle("-fx-padding: 20px;");

        // Create an HBox to place both containers side by side
        HBox mainContainer = new HBox(30, leftContainer, rightContainer);
        mainContainer.setStyle("-fx-padding: 20px;");

        // Position the VBox dynamically below the titleContainer
        titleContainer.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            AnchorPane.setTopAnchor(mainContainer, AnchorPane.getTopAnchor(titleContainer) + newHeight.doubleValue() + 20);
        });

        // Set an initial position (fallback in case height listener hasn't triggered yet)
        if (titleContainer.getHeight() > 0) {
            AnchorPane.setTopAnchor(mainContainer, AnchorPane.getTopAnchor(titleContainer) + titleContainer.getHeight() + 20);
        }

        AnchorPane.setLeftAnchor(mainContainer, 20.0);
        root.getChildren().add(mainContainer);
    }



    /**
     * Creates a scroll pane displaying buttons for pets with health = 0.
     *
     * @return A scroll pane containing the list of pets to revive.
     */
    // Create the ScrollPane for pets with health = 0
    private ScrollPane createPetHealthCheckScrollPane() {
        // Create a title label for "Death Pets"
        Label titleLabel = new Label("Death Pets");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black; -fx-padding: 5px;");

        // Create a container for buttons
        VBox buttonContainer = new VBox(10);
        buttonContainer.getStyleClass().add("inventory"); // Apply the retro inventory background styling
        buttonContainer.setStyle("-fx-padding: 10px;"); // Additional padding for spacing

        // Load saved games and create buttons for pets with health = 0
        LoadData loadData = new LoadData();
        List<SavedGame> savedGames = loadData.loadAllSavedGames();

        for (SavedGame savedGame : savedGames) {
            if (savedGame.petInfo != null && savedGame.petInfo.stats.health == 0) {
                Button petButton = new Button(savedGame.getName() + " (" + savedGame.petInfo.name + ")");
                petButton.getStyleClass().add("parentalControlsButton"); // Use retro button styling
                petButton.setOnAction(e -> {
                    // Play the sound effect
                    Music.getInstance().playSoundEffect();
                    // Confirmation dialog for reviving the pet
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "");
                    alert.setHeaderText("Do you want to revive the pet?");
                    alert.initModality(Modality.WINDOW_MODAL);
                    alert.initStyle(StageStyle.UNDECORATED);

                    // Load and apply CSS for the dialog
                    String css = getClass().getResource("/styles.css").toExternalForm();
                    alert.getDialogPane().getStylesheets().add(css);
                    alert.getDialogPane().getStyleClass().add("dialog-pane");

                    // Custom buttons
                    ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(confirmButton, cancelButton);

                    // Handle dialog result
                    alert.showAndWait().ifPresent(response -> {
                        // Play the sound effect
                        Music.getInstance().playSoundEffect();
                        if (response == confirmButton) {
                            savedGame.petInfo.stats.fullness = 100; // Revive pet
                            savedGame.petInfo.stats.health = 100; // Revive pet
                            savedGame.petInfo.stats.happiness = 100; // Revive pet
                            savedGame.petInfo.stats.sleep = 100; // Revive pet

                            loadData.saveNewGame(savedGame, savedGame.getName()); // Save changes
                            buttonContainer.getChildren().remove(petButton); // Remove button dynamically
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Pet revived!");
                            successAlert.setTitle("Success");
                            successAlert.setHeaderText(null);
                            successAlert.initModality(Modality.WINDOW_MODAL);
                            successAlert.initStyle(StageStyle.UNDECORATED);
                            successAlert.getDialogPane().getStylesheets().add(css);
                            successAlert.getDialogPane().getStyleClass().add("dialog-pane");
                            successAlert.show();
                        }
                    });
                });
                buttonContainer.getChildren().add(petButton);
            }
        }

        // Create a wrapper VBox to combine title and buttons
        VBox content = new VBox(10, titleLabel, buttonContainer);
        content.setStyle("-fx-padding: 10px;");

        // ScrollPane to contain the wrapper VBox
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background: transparent; -fx-padding: 10px;");

        return scrollPane;
    }
    /**
     * Updates the line chart with session duration data.
     *
     * @param lineChart    The line chart to populate.
     * @param gameSettings The game settings containing session data.
     */
    private void updateLineChart(LineChart<Number, Number> lineChart, GameSettings gameSettings) {
        if (lineChart == null) return; // Skip if lineChart is null

        // Clear previous data
        lineChart.getData().clear();

        // Prepare data
        List<Float> sessionDurations = gameSettings.getSettings().getPlayStatistics().getSessionDurations();
        if (sessionDurations.isEmpty()) return;

        double mean = sessionDurations.stream().mapToDouble(Float::doubleValue).average().orElse(0);
        double variance = sessionDurations.stream().mapToDouble(duration -> Math.pow(duration - mean, 2)).average().orElse(0);
        double stddev = Math.sqrt(variance);

        // Generate normal distribution points
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (double x = mean - 3 * stddev; x <= mean + 3 * stddev; x += 0.1) {
            double probabilityDensity = (1 / (stddev * Math.sqrt(2 * Math.PI))) * Math.exp(-0.5 * Math.pow((x - mean) / stddev, 2));
            series.getData().add(new XYChart.Data<>(x, probabilityDensity));
        }

        // Add series to the chart
        lineChart.getData().add(series);

        // Apply styling and spacing adjustments
        lineChart.setTitle("Session Durations Distribution");
        lineChart.setStyle("-fx-padding: 20px;"); // Add padding around the chart

        // Adjust axes styles
        lineChart.getXAxis().setLabel("Session Duration (mins)");
        lineChart.getXAxis().setStyle("-fx-font-size: 14px;");

// Reduce the number of X-axis tick labels
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setTickUnit(1); // Adjust this value to control the density of ticks
        xAxis.setTickLabelRotation(-30); // Rotate labels to avoid overlap

        lineChart.getYAxis().setLabel("Probability Density");
        lineChart.getYAxis().setStyle("-fx-font-size: 14px;");

// Adjust Y-axis label rotation and translation
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        yAxis.setLabel("Probability Density");
        yAxis.lookup(".axis-label").setStyle("-fx-translate-x: -40px;"); // Adjust translation and rotation

// Adjust the chart title style
        lineChart.lookup(".chart-title").setStyle("-fx-font-size: 16px; -fx-translate-y: -20px;");
    }


    /**
     * Validates the time format (HH:mm).
     *
     * @param time The time string to validate.
     * @return True if valid, false otherwise.
     */
    private boolean isValidTime(String time) {
        return time.matches("\\d{2}:\\d{2}");
    }



}


