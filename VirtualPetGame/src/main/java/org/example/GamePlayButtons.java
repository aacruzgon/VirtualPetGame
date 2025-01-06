package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GamePlayButtons class is responsible for creating and managing the buttons
 * and their associated logic in the gameplay screen of the virtual pet game.
 * This includes interactions such as feeding, gifting, playing with, and caring
 * for the pet, as well as handling inventory and settings.
 *
 * @author Alan Cruz
 * @author Bashar Hamo
 */
public class GamePlayButtons {

    private final NavigationHandler navigationHandler;

    private final VirtualPetGame virtualPetGame;

    /**
     * Constructs a GamePlayButtons instance.
     *
     * @param navigationHandler the handler for navigating between scenes.
     * @param virtualPetGame the main instance of the virtual pet game.
     */
    public GamePlayButtons(NavigationHandler navigationHandler, VirtualPetGame virtualPetGame) {
        this.navigationHandler = navigationHandler;
        this.virtualPetGame = virtualPetGame;
    }

    /**
     * Creates and sets up the gameplay buttons in the current scene.
     *
     * @param currentScene the scene where the buttons will be added.
     * @param stage the stage of the application.
     * @param player the player instance associated with the current gameplay.
     */
    public void createGamePlayButton(Scene currentScene, Stage stage, Player player) {
        // Cooldown tracking variables for play and vet
        final long PLAY_COOLDOWN = 15_000; // 15 seconds in milliseconds
        final long VET_COOLDOWN = 30_000; // 30 seconds in milliseconds
        final long[] lastPlayTime = {0}; // Array to allow modification inside lambda
        final long[] lastVetTime = {0};
        // Get the root node of the current scene
        VBox buttonContainer = (VBox) currentScene.getRoot().lookup("#vbox_buttons");
        // Get the Pet's AnchorPane
        AnchorPane anchor_pet = (AnchorPane) currentScene.getRoot().lookup("#anchor_pet");
        // Get the score's AnchorPane
        AnchorPane anchor_score = (AnchorPane) currentScene.getRoot().lookup("#anchor_score");
        // Get the bars for the stats
        ProgressBar bar_health = (ProgressBar) currentScene.getRoot().lookup("#bar_health");
        ProgressBar bar_fullness = (ProgressBar) currentScene.getRoot().lookup("#bar_fullness");
        ProgressBar bar_happiness = (ProgressBar) currentScene.getRoot().lookup("#bar_happiness");
        ProgressBar bar_sleep = (ProgressBar) currentScene.getRoot().lookup("#bar_sleep");
        // Check for nulls and handle gracefully
        if (bar_health == null || bar_fullness == null || bar_happiness == null || bar_sleep == null) {
            throw new IllegalStateException("One or more progress bars could not be found in the scene.");
        }
        // Clear any existing buttons in the HBox to avoid duplicates
        buttonContainer.getChildren().clear();
        //Create GamePlay Buttons
        Button inventory = new Button("Inventory");
        Button feed = new Button("Feed");
        Button gift = new Button("Gift");
        Button play = new Button("Play");
        Button exercise = new Button("Exercise");
        Button vet = new Button("Vet");
        Button sleep = new Button("Sleep");
        Button mainGamePlaySettings = new Button("mainGamePlaySettings");


        mainGamePlaySettings.getStyleClass().add("gameplayButton");
        inventory.getStyleClass().add("gameplayButton");
        feed.getStyleClass().add("gameplayButton");
        gift.getStyleClass().add("gameplayButton");
        play.getStyleClass().add("gameplayButton");
        exercise.getStyleClass().add("gameplayButton");
        vet.getStyleClass().add("gameplayButton");
        sleep.getStyleClass().add("gameplayButton");

        mainGamePlaySettings.setFocusTraversable(false);
        inventory.setFocusTraversable(false);
        feed.setFocusTraversable(false);
        gift.setFocusTraversable(false);
        play.setFocusTraversable(false);
        exercise.setFocusTraversable(false);
        vet.setFocusTraversable(false);
        sleep.setFocusTraversable(false);

        // Set size for buttons
        double buttonSize = 90.4;
        mainGamePlaySettings.setPrefSize(buttonSize, buttonSize);
        inventory.setPrefSize(buttonSize, buttonSize);
        feed.setPrefSize(buttonSize, buttonSize);
        gift.setPrefSize(buttonSize, buttonSize);
        play.setPrefSize(buttonSize, buttonSize);
        exercise.setPrefSize(buttonSize, buttonSize);
        vet.setPrefSize(buttonSize, buttonSize);
        sleep.setPrefSize(buttonSize, buttonSize);

        buttonContainer.setLayoutX(0.0);
        buttonContainer.setLayoutY(51.0);

        // Add tooltips for each button
        Tooltip.install(mainGamePlaySettings, new Tooltip("Settings"));
        Tooltip.install(inventory, new Tooltip("Check your inventory of items."));
        Tooltip.install(feed, new Tooltip("Feed your pet to keep it healthy."));
        Tooltip.install(gift, new Tooltip("Give a gift to your pet to increase happiness."));
        Tooltip.install(play, new Tooltip("Play with your pet to improve its mood."));
        Tooltip.install(exercise, new Tooltip("Help your pet exercise for fitness."));
        Tooltip.install(vet, new Tooltip("Take your pet to the vet for a check-up."));
        Tooltip.install(sleep, new Tooltip("Put your pet to sleep for rest."));

        //THIS IS WHERE YOU IMPLEMENT YOUR LOGIC, LEAVE THE ABOVE UNCHANGED
        inventory.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            showInventoryPopup(stage, player);
        });

        feed.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            String selectedItemName = showItemSelectionPopup("Food", player.getInventory()); // Prompt user to select food
            if (selectedItemName != null) {
                List<Item> selectedItems = player.getInventory().getItemsByName(selectedItemName);
                if (!selectedItems.isEmpty() && selectedItems.get(0) instanceof Food) {
                    Food selectedFood = (Food) selectedItems.get(0); // Get one unit of the food item
                    if (!player.getPet().isAngry() && !player.getPet().isSleeping() && !player.getPet().isDead()) {
                        player.feedPet(selectedFood); // Use the Player method to handle feeding
                    }
                }
            }
        });

        gift.setOnAction(e -> {
            Music.getInstance().playSoundEffect();
            String selectedItemName = showItemSelectionPopup("Gift", player.getInventory()); // Prompt user to select a gift
            if (selectedItemName != null) {
                List<Item> selectedItems = player.getInventory().getItemsByName(selectedItemName);
                if (!selectedItems.isEmpty() && selectedItems.get(0) instanceof Gift) {
                    Gift selectedGift = (Gift) selectedItems.get(0); // Get the selected gift
                    if (!player.getPet().isSleeping() && !player.getPet().isDead()) {
                        player.giftPet(selectedGift); // Use the Player method to handle gifting
                    }
                }
            }
        });


        play.setOnAction(e -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPlayTime[0] >= PLAY_COOLDOWN) {
                player.playWithPet(); // Perform play action
                lastPlayTime[0] = currentTime; // Update last play time
            } else {
                long remainingTime = (PLAY_COOLDOWN - (currentTime - lastPlayTime[0])) / 1000;
            }
        });

        vet.setOnAction(e -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastVetTime[0] >= VET_COOLDOWN) {
                player.takePetToVet(); // Perform vet action
                lastVetTime[0] = currentTime; // Update last vet time
            } else {
                long remainingTime = (VET_COOLDOWN - (currentTime - lastVetTime[0])) / 1000;
            }
        });

        exercise.setOnAction(e -> {player.exercisePet();});
        sleep.setOnAction(e -> {player.putPetToBed();});
        mainGamePlaySettings.setOnAction(e -> {
            MainGamePlaySettingsButtons mainGamePlaySettingsButton = new MainGamePlaySettingsButtons(navigationHandler,  virtualPetGame);
            mainGamePlaySettingsButton.createMainGamePlaySettingsButton(currentScene, stage);
        });
        currentScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // Reuse the same logic as the button
                MainGamePlaySettingsButtons mainGamePlaySettingsButton = new MainGamePlaySettingsButtons(navigationHandler, virtualPetGame);
                mainGamePlaySettingsButton.createMainGamePlaySettingsButton(currentScene, stage);
            }
        });

        // Keyboard shortcuts
        currentScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE -> {
                    Music.getInstance().playSoundEffect();
                    mainGamePlaySettings.fire();
                }
                case I -> {
                    Music.getInstance().playSoundEffect();
                    inventory.fire();
                }
                case F -> {
                    feed.fire();
                }
                case G -> {
                    gift.fire();
                }
                case P -> {
                    play.fire();
                }
                case E -> {
                    exercise.fire();
                }
                case V -> {
                    vet.fire();
                }
                case B -> {
                    sleep.fire();
                }
            }
        });

        //Add buttons to vbox container
        buttonContainer.getChildren().addAll(mainGamePlaySettings, inventory, feed, gift, play, exercise, vet, sleep);


        startGamePlay(currentScene ,player, anchor_pet, anchor_score, bar_health, bar_fullness, bar_happiness, bar_sleep);
    }

    /**
     * Displays the inventory in a popup window.
     *
     * @param parentStage the parent stage of the inventory popup.
     * @param player the player whose inventory will be displayed.
     */
    public void showInventoryPopup(Stage parentStage, Player player) {
        Stage inventoryStage = new Stage(); // new stage for the inventory popup
        inventoryStage.initOwner(parentStage);
        inventoryStage.initModality(Modality.WINDOW_MODAL);
        inventoryStage.initStyle(StageStyle.UNDECORATED);

        // Create a BorderPane to structure the layout
        BorderPane container = new BorderPane();
        container.getChildren().clear();
        container.getStyleClass().add("inventory");

        // Header (Title at the top)
        Label header = new Label("Inventory");
        header.getStyleClass().add("inventoryHeader"); // Apply custom style for header
        BorderPane.setAlignment(header, Pos.CENTER); // Center-align the header in the top
        container.setTop(header);

        // Create the TextArea to display inventory details
        TextArea inventoryText = new TextArea(player.getInventory().toString());
        inventoryText.getStyleClass().add("inventoryLabel");
        inventoryText.setEditable(false); // Prevent user from editing the inventory
        BorderPane.setAlignment(inventoryText, Pos.CENTER);
        container.setCenter(inventoryText); // Add the TextArea to the center region of the BorderPane

        // Close button (Bottom-right corner)
        Button closeButton = new Button("Close");
        closeButton.getStyleClass().add("inventoryCloseButton"); // Apply custom style for the button
        closeButton.setOnAction(event -> {
            Music.getInstance().playSoundEffect();
            inventoryStage.close();
        });
        BorderPane.setAlignment(closeButton, Pos.BOTTOM_RIGHT); // Align button to bottom-right
        container.setBottom(closeButton);

        // Padding for the close button to offset it slightly from the edges
        BorderPane.setMargin(closeButton, new Insets(10)); // Optional: Adjust the margins if needed

        // Scene
        Scene inventoryScene = new Scene(container, 500, 500);
        inventoryScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Add the stylesheet
        inventoryStage.setScene(inventoryScene);
        inventoryStage.showAndWait();
    }

    /**
     * Displays a popup window for selecting an item from the inventory.
     *
     * @param category the category of items to display (e.g., "Food", "Gift").
     * @param inventory the inventory from which items will be selected.
     * @return the name of the selected item, or {@code null} if no selection was made.
     */
    private String showItemSelectionPopup(String category, Inventory inventory) {
        final String[] selectedItem = {null}; // Store the selected item name

        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
        popupStage.initStyle(StageStyle.UNDECORATED); // Remove default window decorations
        popupStage.setTitle("Select an Item");

        // Create a BorderPane to structure the layout
        BorderPane container = new BorderPane();
        container.getStyleClass().add("inventory"); // Apply inventory styling

        // Header (Title at the top)
        Label header = new Label("Select a " + category + " to use:");
        header.getStyleClass().add("inventoryHeader"); // Apply custom style for header
        BorderPane.setAlignment(header, Pos.CENTER); // Center-align the header
        container.setTop(header);

        // Create a ListView to display the items
        ListView<String> listView = new ListView<>();
        listView.getStyleClass().add("inventoryListView"); // Apply custom styling to the list

        // Map to store aggregated item counts
        Map<String, Integer> itemCounts = new HashMap<>();

        // Retrieve categorized items and calculate quantities
        List<Item> categorizedItems = inventory.getItemsByCategory(category);
        for (Item item : categorizedItems) {
            itemCounts.put(item.getName(), itemCounts.getOrDefault(item.getName(), 0) + item.getQuantity());
        }

        // Populate the ListView with item names and their quantities
        itemCounts.forEach((itemName, count) -> listView.getItems().add(itemName + " (x" + count + ")"));

        // Add the ListView to the center of the BorderPane
        container.setCenter(listView);

        // Create buttons for selection and cancellation
        Button useButton = new Button("Use Item");
        Button closeButton = new Button("Close");
        useButton.getStyleClass().add("inventoryButton");
        closeButton.getStyleClass().add("inventoryButton");

        // HBox for organizing buttons
        HBox buttonContainer = new HBox(10, useButton, closeButton);
        buttonContainer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(buttonContainer, new Insets(10)); // Add padding around the buttons
        container.setBottom(buttonContainer); // Add the button container to the bottom of the BorderPane

        // Handle the "Use Item" button action
        useButton.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedItemName = selected.split(" \\(x")[0]; // Extract the item name
                if (inventory.hasItem(selectedItemName)) {
                    selectedItem[0] = selectedItemName; // Store the selected item for later use
                    popupStage.close(); // Close the popup after selection
                }
            }
        });

        // Handle the "Close" button action
        closeButton.setOnAction(e -> popupStage.close());

        // Create and set the scene for the popup
        Scene popupScene = new Scene(container, 500, 500);
        popupScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Add the stylesheet
        popupStage.setScene(popupScene);

        // Show the popup and wait for user interaction
        popupStage.showAndWait();

        // Return the selected item name (null if no selection was made)
        return selectedItem[0];
    }

    /**
     * Starts the gameplay loop, including updating the UI and managing game logic.
     *
     * @param currentScene the current scene for gameplay.
     * @param player the player instance associated with the game.
     * @param anchor_pet the AnchorPane for displaying the pet.
     * @param anchor_score the AnchorPane for displaying the score.
     * @param bar_health the ProgressBar for the pet's health.
     * @param bar_fullness the ProgressBar for the pet's fullness.
     * @param bar_happiness the ProgressBar for the pet's happiness.
     * @param bar_sleep the ProgressBar for the pet's sleep.
     */
    private void startGamePlay(Scene currentScene, Player player, AnchorPane anchor_pet, AnchorPane anchor_score,
                               ProgressBar bar_health, ProgressBar bar_fullness,
                               ProgressBar bar_happiness, ProgressBar bar_sleep) {
        GamePlayLoop gamePlayLoop = new GamePlayLoop(player);

        // Create a score label and add it to the anchor_score
        Label scoreLabel = new Label("Score: " + player.getScore());
        scoreLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: white;"); // Adjust style as needed
        anchor_score.getChildren().clear(); // Clear any previous content
        anchor_score.getChildren().add(scoreLabel);
        // Fetch state images
        ImageView stateNormal = (ImageView) currentScene.lookup("#state_normal");
        ImageView stateHungry = (ImageView) currentScene.lookup("#state_hungry");
        ImageView stateSleep = (ImageView) currentScene.lookup("#state_sleep");
        ImageView stateAngry = (ImageView) currentScene.lookup("#state_angry");
        ImageView stateDead = (ImageView) currentScene.lookup("#state_dead");

        // Use AnimationTimer to periodically call updateGameLogic()
        new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gamePlayLoop.isGameStarted() && !gamePlayLoop.isGameOver()) {
                    gamePlayLoop.updateGameLogic(); // Update game logic

                    // Update progress bars and sprite state based on pet stats
                    Pet pet = player.getPet();
                    if (pet != null) {
                        updateProgressBars(pet, bar_health, bar_fullness, bar_happiness, bar_sleep);
                        // Helper method to update state visibility
                        updatePetState(player.getPet(), stateNormal, stateHungry, stateSleep, stateAngry, stateDead);
                        // Clear and update the pet's sprite in the anchor pane
                        anchor_pet.getChildren().clear();
                        anchor_pet.getChildren().add(pet.getSprite().getCurrentState());
                    }

                    // Update score display
                    scoreLabel.setText("Score: " + player.getScore());
                } else {
                    stop(); // Stop the timer when the game ends
                    if (gamePlayLoop.isGameOver()) {
                        navigateToGameOverScreen(); // Example: Navigate to a game over screen
                    }
                }
            }
        }.start();
    }

    /**
     * Updates the progress bars for the pet's stats based on its current values.
     *
     * @param pet the pet whose stats will be displayed.
     * @param bar_health the ProgressBar for the pet's health.
     * @param bar_fullness the ProgressBar for the pet's fullness.
     * @param bar_happiness the ProgressBar for the pet's happiness.
     * @param bar_sleep the ProgressBar for the pet's sleep.
     */
    private void updateProgressBars(Pet pet, ProgressBar bar_health, ProgressBar bar_fullness,
                                    ProgressBar bar_happiness, ProgressBar bar_sleep) {
        bar_health.setProgress(Math.max(0, pet.getHealth() / 100.0)); // Normalize to 0.0 - 1.0
        bar_fullness.setProgress(Math.max(0, pet.getFullness() / 100.0));
        bar_happiness.setProgress(Math.max(0, pet.getHappiness() / 100.0));
        bar_sleep.setProgress(Math.max(0, pet.getSleep() / 100.0));

        // Update styles based on thresholds
        updateBarStyle(bar_health, pet.getHealth());
        updateBarStyle(bar_fullness, pet.getFullness());
        updateBarStyle(bar_happiness, pet.getHappiness());
        updateBarStyle(bar_sleep, pet.getSleep());
    }

    /**
     * Updates the style of a progress bar based on the value thresholds.
     *
     * @param bar the ProgressBar to update.
     * @param value the value of the stat to determine the style.
     */
    private void updateBarStyle(ProgressBar bar, double value) {
        if (value <= 25) {
            bar.setStyle("-fx-accent: red;"); // Critical (red)
        } else if (value <= 50) {
            bar.setStyle("-fx-accent: orange;"); // Warning (orange)
        } else {
            bar.setStyle("-fx-accent: green;"); // Normal (green)
        }
    }

    /**
     * Updates the pet's state display (e.g., normal, hungry, sleeping, angry, dead) based on its current condition.
     *
     * @param pet the pet whose state will be updated.
     * @param stateNormal the ImageView for the "normal" state.
     * @param stateHungry the ImageView for the "hungry" state.
     * @param stateSleep the ImageView for the "sleep" state.
     * @param stateAngry the ImageView for the "angry" state.
     * @param stateDead the ImageView for the "dead" state.
     */
    private void updatePetState(Pet pet, ImageView stateNormal, ImageView stateHungry, ImageView stateSleep, ImageView stateAngry, ImageView stateDead) {
        // Hide all states by default
        stateNormal.setVisible(false);
        stateHungry.setVisible(false);
        stateSleep.setVisible(false);
        stateAngry.setVisible(false);
        stateDead.setVisible(false);

        // Show the correct state based on the pet's condition
        if (pet.isDead()) {
            stateDead.setVisible(true);
            stateNormal.setVisible(false);
            stateHungry.setVisible(false);
            stateSleep.setVisible(false);
            stateAngry.setVisible(false);
        } else if (pet.isAngry()) {
            stateAngry.setVisible(true);
            stateNormal.setVisible(false);
        } else if (pet.isSleeping()) {
            stateSleep.setVisible(true);
            stateNormal.setVisible(false);
            stateHungry.setVisible(false);
            stateAngry.setVisible(false);
        } else if (pet.isHungry()) { // Assuming "hungry" is when fullness <= 25
            stateHungry.setVisible(true);
            stateNormal.setVisible(false);
        } else {
            stateNormal.setVisible(true);
        }
    }

    private void navigateToGameOverScreen() {
        // Placeholder for game over navigation logic
        System.out.println("Navigating to Game Over screen...");
    }

}
