package org.example;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

/**
 * The PetSelectionButtons class handles the creation and functionality of buttons
 * for selecting a pet in the virtual pet game. It also manages the process of naming
 * the selected pet and initializing it with specific attributes.
 *
 *
 * @author Bashar Hamo
 *
 */
public class PetSelectionButtons {

    private final NavigationHandler navigationHandler;
    private String selectedPet; // Stores the selected pet
    private String selectedPetName; // Stores the entered name
    private Player player;
    private final VirtualPetGame virtualPetGame;

    /**
     * Constructs a new instance of PetSelectionButtons.
     *
     * @param navigationHandler a handler to manage scene navigation.
     * @param virtualPetGame the main game instance.
     */
    public PetSelectionButtons(NavigationHandler navigationHandler, VirtualPetGame virtualPetGame) {
        this.navigationHandler = navigationHandler;
        this.virtualPetGame = virtualPetGame;
    }

    /**
     * Sets the player instance for this pet selection process.
     *
     * @param player the player to set.
     */
    private void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Retrieves the player instance associated with the selected pet.
     *
     * @return the player instance.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Creates the pet selection buttons and adds them to the specified scene.
     *
     * @param currentScene the current scene where the buttons will be displayed.
     * @param currentStage the primary stage of the application.
     */
    public void createPetSelectionButton(Scene currentScene, Stage currentStage) {
        // Get the root node of the current scene (expected to be an AnchorPane)
        AnchorPane root = (AnchorPane) currentScene.getRoot();
        // Retrieve the HBox container where buttons will be added
        HBox buttonContainer = (HBox) root.lookup("#hbox_buttons");
        // Clear any existing buttons in the HBox to avoid duplicates
        buttonContainer.getChildren().clear();

        // Create the "New Game" button
        Button kuromametchi_button = new Button("kuromametchi_button");
        Button lovelitchi_button = new Button("lovelitchi_button");
        Button mimitchi_button = new Button("mimitchi_button");
        Button orenetchi_button = new Button("orenetchi_button");
        Button violetchi_button = new Button("violetchi_button");

        kuromametchi_button.getStyleClass().add("petButton");
        lovelitchi_button.getStyleClass().add("petButton");
        mimitchi_button.getStyleClass().add("petButton");
        orenetchi_button.getStyleClass().add("petButton");
        violetchi_button.getStyleClass().add("petButton");

        kuromametchi_button.setFocusTraversable(false);
        lovelitchi_button.setFocusTraversable(false);
        mimitchi_button.setFocusTraversable(false);
        orenetchi_button.setFocusTraversable(false);
        violetchi_button.setFocusTraversable(false);

        kuromametchi_button.setPrefHeight(150.0);
        kuromametchi_button.setPrefWidth(150.0);
        mimitchi_button.setPrefHeight(150.0);
        mimitchi_button.setPrefWidth(150.0);
        orenetchi_button.setPrefHeight(150.0);
        orenetchi_button.setPrefWidth(130.0);
        lovelitchi_button.setPrefHeight(150.0);
        lovelitchi_button.setPrefWidth(150.0);
        violetchi_button.setPrefHeight(150.0);
        violetchi_button.setPrefWidth(150.0);

        // Set actions for each button to prompt for a pet name
        kuromametchi_button.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            promptForPetName(currentStage,"Kuromametchi");
        });
        lovelitchi_button.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            promptForPetName(currentStage,"Lovelitchi");
        });
        mimitchi_button.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            promptForPetName(currentStage,"Mimitchi");
        });
        orenetchi_button.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            promptForPetName(currentStage, "Orenetchi");
        });
        violetchi_button.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            promptForPetName(currentStage,"Violetchi");
        });

        Tooltip.install(kuromametchi_button, new Tooltip("Kuromametchi is a steady and balanced pet,\nneeding moderate attention but minimal feeding and rest."));
        Tooltip.install(lovelitchi_button, new Tooltip("Lovelitchi is a lively companion with a hearty appetite\nand frequent need for interaction and rest."));
        Tooltip.install(mimitchi_button, new Tooltip("Mimitchi is energetic and demanding,\nrequiring frequent feeding but staying naturally cheerful."));
        Tooltip.install(orenetchi_button, new Tooltip("Orenetchi is low-maintenance,\nrarely needing food or attention but tiring quickly."));
        Tooltip.install(violetchi_button, new Tooltip("Violetchi is vibrant and expressive,\nrequiring frequent feeding and care to stay happy and healthy."));

        // Add all created buttons to the VBox container (buttonContainer)
        buttonContainer.getChildren().addAll(
                kuromametchi_button,
                lovelitchi_button,
                mimitchi_button,
                orenetchi_button,
                violetchi_button
        );

    }

    /**
     * Prompts the user to enter a name for the selected pet. Once a valid name is provided,
     * the selected pet is initialized, and the game transitions to the gameplay scene.
     *
     * @param parentStage the parent stage of the prompt dialog.
     * @param petName the name of the selected pet type.
     */
    private void promptForPetName(Stage parentStage, String petName) {
        // Create a new stage for the name prompt
        Stage namePromptStage = new Stage();
        namePromptStage.initOwner(parentStage);
        namePromptStage.initModality(Modality.WINDOW_MODAL);
        namePromptStage.initStyle(StageStyle.UNDECORATED); // Removes window decorations

        // Create UI components
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setStyle(
                "-fx-background-image: url('/Sprites/passwordBackground.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-repeat: no-repeat;"
        );

        Label instruction = new Label("Name Your Pet!");
        instruction.getStyleClass().add("promptLabel"); // Apply the label style

        TextField nameField = new TextField();
        nameField.setPromptText("Pet Name");
        nameField.getStyleClass().add("promptTextField"); // Apply the text field style

        // Buttons
        Button okButton = new Button("OK");
        okButton.getStyleClass().add("promptButton"); // Apply the button style

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("promptButton"); // Apply the button style

        // Layout buttons horizontally
        HBox buttonContainer = new HBox(10, okButton, cancelButton);
        buttonContainer.setAlignment(Pos.CENTER);

        // Add components to the container
        container.getChildren().addAll(instruction, nameField, buttonContainer);

        // Set the scene for the name prompt
        Scene namePromptScene = new Scene(container, 500, 200);
        namePromptScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Add the stylesheet
        namePromptStage.setScene(namePromptScene);

        // Button actions
        okButton.setDefaultButton(true);
        okButton.setOnAction(event -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            String enteredName = nameField.getText();
            if (!enteredName.isEmpty()) {
            // Store the selected pet and name
                this.selectedPet = petName;
                this.selectedPetName = enteredName;


                namePromptStage.close();
                switch (selectedPet){
                    case "Kuromametchi":
                        initializeKuromametchi(enteredName, selectedPet);
                        break;
                    case "Lovelitchi":
                        initializeLovelitchi(enteredName, selectedPet);
                        break;
                    case "Mimitchi":
                        initializeMimitchi(enteredName, selectedPet);
                        break;
                    case "Orenetchi":
                        initializeOrenetchi(enteredName, selectedPet);
                        break;
                    case "Violetchi":
                        initializeVioletchi(enteredName, selectedPet);
                        break;
                }

                virtualPetGame.setNewGame(true);
                navigationHandler.navigate("GamePlay");
            } else {
                // Highlight the text field if no name is entered
                nameField.setStyle(
                        "-fx-background-image: url('/Sprites/returnSky.png');" +
                                "-fx-background-size: cover;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 2;"
                );
            }
        });

        cancelButton.setOnAction(event -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            namePromptStage.close();
        });

        // Show the modal
        namePromptStage.showAndWait();
    }

    /**
     * Initializes the selected pet as a Kuromametchi with predefined attributes.
     *
     * @param name the name of the pet.
     * @param selectedPet the type of the pet.
     */
    private void initializeKuromametchi(String name, String selectedPet) {
        Sprite spriteKuromametchi = new Sprite();

        addSpriteState(spriteKuromametchi, "angry", "/Sprites/Kuromametchi/angry.png");
        addSpriteState(spriteKuromametchi, "default", "/Sprites/Kuromametchi/default.png");
        addSpriteState(spriteKuromametchi, "disappointed", "/Sprites/Kuromametchi/disappointed.png");
        addSpriteState(spriteKuromametchi, "dead", "/Sprites/Kuromametchi/dead.png");
        addSpriteState(spriteKuromametchi, "happy", "/Sprites/Kuromametchi/happy.png");
        addSpriteState(spriteKuromametchi, "play", "/Sprites/Kuromametchi/play.png");
        addSpriteState(spriteKuromametchi, "sad", "/Sprites/Kuromametchi/sad.png");
        addSpriteState(spriteKuromametchi, "shock", "/Sprites/Kuromametchi/shock.png");
        addSpriteState(spriteKuromametchi, "sleep", "/Sprites/Kuromametchi/sleep.png");

        Pet Kuromametchi = new Pet(selectedPet,name, spriteKuromametchi, 100.0,100.0,100.0,100.0,0.4, 0.8, 0.3);

        saveSelectedPet(Kuromametchi);
    }

    /**
     * Initializes the selected pet as a Lovelitchi with predefined attributes.
     *
     * @param name the name of the pet.
     * @param selectedPet the type of the pet.
     */
    private void initializeLovelitchi(String name, String selectedPet) {
        Sprite spriteLovelitchi = new Sprite();

        addSpriteState(spriteLovelitchi, "angry", "/Sprites/Lovelitchi/angry.png");
        addSpriteState(spriteLovelitchi, "default", "/Sprites/Lovelitchi/default.png");
        addSpriteState(spriteLovelitchi, "disappointed", "/Sprites/Lovelitchi/disappointed.png");
        addSpriteState(spriteLovelitchi, "dead", "/Sprites/Lovelitchi/dead.png");
        addSpriteState(spriteLovelitchi, "happy", "/Sprites/Lovelitchi/happy.png");
        addSpriteState(spriteLovelitchi, "play", "/Sprites/Lovelitchi/play.png");
        addSpriteState(spriteLovelitchi, "sad", "/Sprites/Lovelitchi/sad.png");
        addSpriteState(spriteLovelitchi, "shock", "/Sprites/Lovelitchi/shock.png");
        addSpriteState(spriteLovelitchi, "sleep", "/Sprites/Lovelitchi/sleep.png");

        Pet Lovelitchi = new Pet(selectedPet, name, spriteLovelitchi, 100.0,100.0,100.0,100.0,0.6, 0.8, 0.6);

        saveSelectedPet(Lovelitchi);
    }

    /**
     * Initializes the selected pet as a Mimitchi with predefined attributes.
     *
     * @param name the name of the pet.
     * @param selectedPet the type of the pet.
     */
    private void initializeMimitchi(String name, String selectedPet) {
        Sprite spriteMimitchi = new Sprite();

        addSpriteState(spriteMimitchi, "angry", "/Sprites/Mimitchi/angry.png");
        addSpriteState(spriteMimitchi, "default", "/Sprites/Mimitchi/default.png");
        addSpriteState(spriteMimitchi, "disappointed", "/Sprites/Mimitchi/disappointed.png");
        addSpriteState(spriteMimitchi, "dead", "/Sprites/Mimitchi/dead.png");
        addSpriteState(spriteMimitchi, "happy", "/Sprites/Mimitchi/happy.png");
        addSpriteState(spriteMimitchi, "play", "/Sprites/Mimitchi/play.png");
        addSpriteState(spriteMimitchi, "sad", "/Sprites/Mimitchi/sad.png");
        addSpriteState(spriteMimitchi, "shock", "/Sprites/Mimitchi/shock.png");
        addSpriteState(spriteMimitchi, "sleep", "/Sprites/Mimitchi/sleep.png");

        Pet Mimitchi = new Pet( selectedPet,name, spriteMimitchi, 100.0,100.0,100.0,100.0,0.9, 0.2, 0.5);
        saveSelectedPet(Mimitchi);
    }

    /**
     * Initializes the selected pet as an Orenetchi with predefined attributes.
     *
     * @param name the name of the pet.
     * @param selectedPet the type of the pet.
     */
    private void initializeOrenetchi(String name, String selectedPet) {
        Sprite spriteOrenetchi = new Sprite();

        addSpriteState(spriteOrenetchi, "angry", "/Sprites/Orenetchi/angry.png");
        addSpriteState(spriteOrenetchi, "default", "/Sprites/Orenetchi/default.png");
        addSpriteState(spriteOrenetchi, "disappointed", "/Sprites/Orenetchi/disappointed.png");
        addSpriteState(spriteOrenetchi, "dead", "/Sprites/Orenetchi/dead.png");
        addSpriteState(spriteOrenetchi, "happy", "/Sprites/Orenetchi/happy.png");
        addSpriteState(spriteOrenetchi, "play", "/Sprites/Orenetchi/play.png");
        addSpriteState(spriteOrenetchi, "sad", "/Sprites/Orenetchi/sad.png");
        addSpriteState(spriteOrenetchi, "shock", "/Sprites/Orenetchi/shock.png");
        addSpriteState(spriteOrenetchi, "shock", "/Sprites/Orenetchi/sleep.png");

        Pet Orenetchi = new Pet(selectedPet,name, spriteOrenetchi, 100.0,100.0,100.0,100.0,0.1, 0.3, 1.0);
        saveSelectedPet(Orenetchi);
    }

    /**
     * Initializes the selected pet as a Violetchi with predefined attributes.
     *
     * @param name the name of the pet.
     * @param selectedPet the type of the pet.
     */
    private void initializeVioletchi(String name, String selectedPet) {
        Sprite spriteVioletchi = new Sprite();

        addSpriteState(spriteVioletchi, "angry", "/Sprites/Violetchi/angry.png");
        addSpriteState(spriteVioletchi, "default", "/Sprites/Violetchi/default.png");
        addSpriteState(spriteVioletchi, "disappointed", "/Sprites/Violetchi/disappointed.png");
        addSpriteState(spriteVioletchi, "dead", "/Sprites/Violetchi/dead.png");
        addSpriteState(spriteVioletchi, "happy", "/Sprites/Violetchi/happy.png");
        addSpriteState(spriteVioletchi, "play", "/Sprites/Violetchi/play.png");
        addSpriteState(spriteVioletchi, "sad", "/Sprites/Violetchi/sad.png");
        addSpriteState(spriteVioletchi, "shock", "/Sprites/Violetchi/shock.png");
        addSpriteState(spriteVioletchi, "shock", "/Sprites/Violetchi/sleep.png");

        Pet Violetchi = new Pet(selectedPet,name, spriteVioletchi,100.0,100.0,100.0,100.0, 1.0, 0.6, 0.5);
        saveSelectedPet(Violetchi);
    }

    /**
     * Adds a new visual state to a sprite for the specified pet.
     *
     * @param sprite the sprite to which the state will be added.
     * @param state the name of the state (e.g., "happy", "sad").
     * @param resourcePath the path to the image resource representing the state.
     */
    private void addSpriteState(Sprite sprite, String state, String resourcePath) {
        URL imageUrl = getClass().getResource(resourcePath);
        if (imageUrl == null) {
            System.err.println("Image not found: " + resourcePath);
        } else {
            ImageView imageView = new ImageView(imageUrl.toExternalForm());
            imageView.setFitWidth(150); // Set width to 150
            imageView.setFitHeight(150); // Set height to 150
            imageView.setPreserveRatio(true); // Preserve the aspect ratio
            sprite.addState(state, imageView);
        }
    }

    /**
     * Saves the selected pet by associating it with a new player instance.
     *
     * @param pet the selected pet to save.
     */
    private void saveSelectedPet(Pet pet) {
        Player player = new Player();
        player.setPet(pet);
        setPlayer(player);
    }

}