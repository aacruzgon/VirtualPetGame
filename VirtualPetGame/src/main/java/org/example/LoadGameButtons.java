package org.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;

/**
 * The LoadGameButtons class handles the creation and management of buttons
 * for loading saved games in the virtual pet game. It populates a UI container
 * with buttons corresponding to saved game files and handles the loading process
 * when a button is clicked.
 *
 *
 * @author Alan Cruz
 *
 */
public class LoadGameButtons {

    private final NavigationHandler navigationHandler;
    private final VirtualPetGame virtualPetGame;

    /**
     * Constructs a new LoadGameButtons instance.
     *
     * @param navigationHandler the handler for managing scene navigation.
     * @param virtualPetGame the main game instance.
     */
    public LoadGameButtons(NavigationHandler navigationHandler, VirtualPetGame virtualPetGame) {
        this.navigationHandler = navigationHandler;
        this.virtualPetGame = virtualPetGame;
    }

    /**
     * Creates buttons for each saved game and adds them to the specified scene's UI.
     *
     * @param currentScene the current scene where the buttons will be displayed.
     * @param savedGames a list of saved games to create buttons for.
     */
    public void createLoadGameButtons(Scene currentScene, List<SavedGame> savedGames) {
        // Access the root AnchorPane
        AnchorPane root = (AnchorPane) currentScene.getRoot();

        // Access the ScrollPane by fx:id
        ScrollPane scrollPane = (ScrollPane) root.lookup("#loadGameScrollPane");


        // Access the VBox inside the ScrollPane
        VBox saveFileContainer = (VBox) ((VBox) scrollPane.getContent()).getChildren().get(0);


        // Clear existing children (to avoid duplication if reloaded)
        saveFileContainer.getChildren().clear();

        // Populate the VBox with save file buttons
        if (savedGames.isEmpty()) {
            Label noSavedFilesLabel = new Label("No Saved Files");
            noSavedFilesLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black;");
            saveFileContainer.getChildren().add(noSavedFilesLabel);
        } else {
            for (SavedGame savedGame : savedGames) {
                Button saveFileButton = new Button(savedGame.getName());
                saveFileButton.setStyle(
                        "-fx-font-size: 18px; " +
                                "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-padding: 10px; " +
                                "-fx-cursor: hand;" +
                                "-fx-background-radius: 10px;"
                );

                // Set the button action to load the saved game
                saveFileButton.setOnAction(e -> {
                    // Play the sound effect
                    Music.getInstance().playSoundEffect();

                    // Deserialize the JSON file into a SavedGame object
                    LoadData loadData = new LoadData();
                    SavedGame loadedGame = loadData.loadSavedGame(savedGame.getName());

                    if (loadedGame != null) {
                        // Map inventory
                        Inventory newInventory = new Inventory("SavedGame");

                        if (loadedGame.playerInfo.inventory != null) {
                            newInventory = Inventory.fromSerializableMap(loadedGame.playerInfo.inventory);// Replace the inventory
                        }

                        Player player = new Player(newInventory);
                        player.setScore(loadedGame.playerInfo.score);


                        // Map pet info
                        SavedGame.PetInfo petInfo = loadedGame.petInfo;
                        Sprite sprite = initializeSprite(petInfo.petType);
                        if (petInfo != null) {
                            Pet pet = new Pet(
                                    petInfo.petType,
                                    petInfo.name,
                                    sprite, // Assume default sprite; set state after initialization
                                    petInfo.stats.health,
                                    petInfo.stats.fullness,
                                    petInfo.stats.happiness,
                                    petInfo.stats.sleep,
                                    petInfo.stats.fullnessDepletionRate, // Default depletion rates; adjust if needed
                                    petInfo.stats.happinessDepletionRate,
                                    petInfo.stats.sleepDepletionRate
                            );

                            // Assign the pet to the player
                            player.setPet(pet);
                        }


                        // Update VirtualPetGame state
                        virtualPetGame.setCurrentPlayer(player);
                        virtualPetGame.setCurrentPet(player.getPet());
                        virtualPetGame.setCurrentSavedGame(loadedGame);
                        virtualPetGame.setNewGame(false);

                        // Navigate to the GamePlay screen
                        navigationHandler.navigate("GamePlay");
                    } else {
                        System.out.println("Failed to load the saved game.");
                    }
                });
                saveFileButton.setPrefWidth(300); // Consistent width
                saveFileContainer.getChildren().add(saveFileButton);
            }
        }

        // Ensure the outer VBox (ScrollPane content) is also centered
        VBox outerWrapper = (VBox) scrollPane.getContent();
        outerWrapper.setAlignment(Pos.CENTER); // Center everything in the ScrollPane
    }

    /**
     * Initializes a {@link Sprite} object for a specific pet type by loading
     * its visual states from predefined resources.
     *
     * @param selectedPet the type of pet to initialize (e.g., "Kuromametchi").
     * @return the initialized {@link Sprite} object.
     * @throws IllegalArgumentException if an invalid pet type is specified.
     */
    private Sprite initializeSprite(String selectedPet) {
        switch (selectedPet) {
            case "Kuromametchi":
                return initializeKuromametchi();
            case "Lovelitchi":
                return initializeLovelitchi();
            case "Mimitchi":
                return initializeMimitchi();
            case "Orenetchi":
                return initializeOrenetchi();
            case "Violetchi":
                return initializeVioletchi();
            default:
                throw new IllegalArgumentException("Invalid pet selected: " + selectedPet);
        }
    }

    /**
     * Initializes the visual states of the Kuromametchi pet.
     *
     * @return the {@link Sprite} object representing Kuromametchi.
     */
    private Sprite initializeKuromametchi() {
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

        return spriteKuromametchi;
    }

    /**
     * Initializes the visual states of the Lovelitchi pet.
     *
     * @return the {@link Sprite} object representing Lovelitchi.
     */
    private Sprite initializeLovelitchi() {
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

        return  spriteLovelitchi;
    }

    /**
     * Initializes the visual states of the Mimitchi pet.
     *
     * @return the {@link Sprite} object representing Mimitchi.
     */
    private Sprite initializeMimitchi() {
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

        return  spriteMimitchi;
    }

    /**
     * Initializes the visual states of the Orenetchi pet.
     *
     * @return the {@link Sprite} object representing Orenetchi.
     */
    private Sprite initializeOrenetchi() {
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

        return spriteOrenetchi;
    }

    /**
     * Initializes the visual states of the Violetchi pet.
     *
     * @return the {@link Sprite} object representing Violetchi.
     */
    private Sprite initializeVioletchi() {
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

        return  spriteVioletchi;
    }

    /**
     * Adds a visual state to a {@link Sprite} for a specific pet.
     *
     * @param sprite the {@link Sprite} object to add the state to.
     * @param state the name of the state (e.g., "angry", "happy").
     * @param resourcePath the resource path to the image representing the state.
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








}

