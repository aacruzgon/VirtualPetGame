package org.example;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;



import java.util.List;
/**
 * VirtualPetGame serves as the main entry point for the virtual pet game application.
 * It extends the JavaFX Application class to create and manage the UI and game lifecycle.
 * <p>
 * The class handles scene navigation, application lifecycle events, and game state management.
 * </p>
 *
 *
 * @author Alan Cruz
 *
 */
public class VirtualPetGame extends Application {

    private Graph graph = new Graph(); // Graph to manage scenes
    private Stage stage;
    private GameSettings gameSettings = new GameSettings();
    private long sessionStartTime;
    private List<SavedGame> savedGames;
    private LoadGameButtons loadGameButtons = new LoadGameButtons(this::navigateTo, this);
    private ParentalControlButtons parentalControlButtons = new ParentalControlButtons(this::navigateTo);
    private SettingsButtons settingsButtons = new SettingsButtons(this::navigateTo);
    private MainMenuButtons mainMenuButtonManager = new MainMenuButtons(this::navigateTo, this);
    private PetSelectionButtons petSelectionButtons = new PetSelectionButtons(this::navigateTo, this);
    private GamePlayButtons gamePlayButtons = new GamePlayButtons(this::navigateTo, this);
    private Player currentPlayer;
    private Pet currentPet;
    private Music music = Music.getInstance();
    private SavedGame currentGame;
    private boolean isNewGame = true; // Default to true for new game
    private LoadData loadData;

    /**
     * Starts the JavaFX application. Configures the primary stage, preloads scenes,
     * sets up the navigation graph, and initializes the main menu as the starting scene.
     *
     * @param primaryStage the primary stage for this JavaFX application.
     * @throws Exception if an error occurs during application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        sessionStartTime = System.currentTimeMillis();
        loadData = new LoadData();

        // Load game settings
        gameSettings = loadData.loadGameSettings();




        this.stage = primaryStage;

        // Get the screen's usable dimensions
        // This retrieves the available screen area, excluding the taskbar and other system UI elements.
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Set the stage size to match the screen's usable bounds
        // Ensures the window fills the entire usable screen area.
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());

        // Position the stage at the top left corner of the screen
        // Aligns the window to start from the top-left corner of the usable screen area.
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());

        // Disable resizing (optional)
        // Prevents the user from resizing the application window.
        primaryStage.setResizable(false);

        // Preload scenes
        // Loads all scenes in advance to improve navigation performance.
        preloadScenes(primaryStage);

        // Set up navigation paths
        // Configures the navigation graph to define valid transitions between scenes.
        setupNavigationGraph();

        // Start with the Main Menu
        // Sets the initial scene to the Main Menu screen.
        navigateTo("MainMenu");

        // Show the stage
        // Displays the application window on the screen.
        stage.show();
    }



    /**
     * Handles logic for stopping the application. Updates game statistics and saves
     * game settings to persist data between sessions.
     */
    public void handleStopLogic() {
        try {
            // Calculate session end time and duration in minutes as a float
            long sessionEndTime = System.currentTimeMillis();
            float sessionDuration = (sessionEndTime - sessionStartTime) / 60000.0f;

            // Update the game statistics with the session duration
            GameSettings.PlayStatistics stats = gameSettings.getSettings().getPlayStatistics();
            stats.updateStatistics(sessionDuration);

            // Persist the updated statistics to the JSON file
            new LoadData().saveGameSettings(gameSettings);


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred during the stop process: " + e.getMessage());
        }
    }

    /**
     * Called when the application is about to stop. It ensures the application's
     * state is saved and necessary cleanup is performed.
     *
     * @throws Exception if an error occurs during the stop process.
     */
    @Override
    public void stop() throws Exception {
        handleStopLogic();
    }

    /**
     * Preloads all scenes required for the game and adds them to the navigation graph.
     * This ensures seamless scene transitions during gameplay.
     *
     * @param primaryStage the primary stage of the application, used for scene creation.
     * @throws Exception if an error occurs while loading scenes.
     */
    private void preloadScenes(Stage primaryStage) throws Exception {
        // Initialize the MainView (Main Menu screen)
        MainView mainView = new MainView();
        Scene mainMenuScene = mainView.createScene(primaryStage);

        // Load the font during application startup
        Font font = Font.loadFont(getClass().getResourceAsStream("/Sprites/PressStart2P-Regular.ttf"), 14);



        // Add a stylesheet to the Main Menu Scene
        // The stylesheet is used to style the buttons and other UI components specifically for the Main Menu
        String stylesheet = getClass().getResource("/styles.css").toExternalForm();
        mainMenuScene.getStylesheets().add(stylesheet);


        // Add the Main Menu Scene to the graph
        // This makes the Main Menu accessible as a node in the navigation graph
        graph.addScene("MainMenu", mainMenuScene);

        // Initialize the Settings screen
        // The Setting class creates the scene for the Settings screen
        Setting setting = new Setting();
        Scene settingsScene = setting.createScene(primaryStage);

        // Add the stylesheet to the Settings Scene
        settingsScene.getStylesheets().add(stylesheet);

        // Add the Settings Scene to the graph
        graph.addScene("Settings", settingsScene);

        /*
        LOOK AT THE IMPLEMENTATION ABOVE ^^^^^^ YOU WILL DO THE SAME LOGIC, FOLLOW IT FOR YOUR LOGIC
         */
        // Initialize the Tutorial screen
        Tutorial tutorial = new Tutorial();
        Scene tutorialScene = tutorial.createScene(primaryStage);
        tutorialScene.getStylesheets().add(stylesheet);
        // Add the tutorial Scene to the graph
        graph.addScene("Tutorial", tutorialScene);

        ParentalControls parentalControls = new ParentalControls();
        Scene parentalControlsScene = parentalControls.createScene(primaryStage);
        parentalControlsScene.getStylesheets().add(stylesheet);
        graph.addScene("ParentalControls", parentalControlsScene);

        // Initialize the LoadGame screen
        LoadGame loadGame = new LoadGame();
        Scene loadGameScene = loadGame.createScene(primaryStage);

        // Add the stylesheet to the LoadGame Scene
        loadGameScene.getStylesheets().add(stylesheet);

        // Add the LoadGame Scene to the graph
        graph.addScene("LoadGame", loadGameScene);

        PetSelection petSelection = new PetSelection();
        Scene petSelectionScene = petSelection.createScene(primaryStage);
        petSelectionScene.getStylesheets().add(stylesheet);
        graph.addScene("NewGame", petSelectionScene);

        GamePlay gamePlay = new GamePlay();
        Scene gamePlayScene = gamePlay.createScene(primaryStage);
        gamePlayScene.getStylesheets().add(stylesheet);
        graph.addScene("GamePlay", gamePlayScene);

        Setting mainGamePlaySetting = new Setting();
        Scene mainGamePlaySettingScene = mainGamePlaySetting.createScene(primaryStage);
        // Add the stylesheet to the Settings Scene
        mainGamePlaySettingScene.getStylesheets().add(stylesheet);
        // Add the Settings Scene to the graph
        graph.addScene("Settings1", mainGamePlaySettingScene);

        ParentalControls mainGamePlayParentalControls = new ParentalControls();
        Scene mainGamePlayParentalControlsScene = mainGamePlayParentalControls.createScene(primaryStage);
        mainGamePlayParentalControlsScene.getStylesheets().add(stylesheet);
        graph.addScene("ParentalControls2", mainGamePlayParentalControlsScene);

    }

    /**
     * Configures the navigation graph with the allowed transitions between scenes.
     * Defines the structure of the game's navigation paths.
     */
    private void setupNavigationGraph() {
        // Define navigation paths for the Main Menu:
        // From the "MainMenu", the user can navigate to:
        // - "NewGame": Starts a new game.
        // - "LoadGame": Loads a previously saved game.
        // - "Tutorial": Opens the tutorial screen.
        // - "ParentalControl": Opens the parental control settings.
        // - "Settings": Opens the settings screen.
        graph.addNavigation("MainMenu", List.of("NewGame", "LoadGame", "Tutorial", "Settings"));

        // Define navigation paths for the Settings screen:
        // From the "Settings" screen, the user can only navigate back to the "MainMenu".
        //From "Settings" opens ParentalControl screen.
        graph.addNavigation("Settings", List.of("MainMenu", "ParentalControls"));

        // Define navigation path for the Parental Control screen:
        // From the "ParentalControl" screen, the user can only navigate back to the "Settings" Screen.
        graph.addNavigation("ParentalControls", List.of("Settings"));

        // Define navigation paths for the New Game screen:
        // From the "NewGame" screen, the user can only navigate back to the "MainMenu".
        graph.addNavigation("NewGame", List.of("MainMenu", "GamePlay"));

        //Define navigation paths for the Tutorial screen:
        //From the "Tutorial" Screen, the user can only navigate back to the "MainMenu".
        graph.addNavigation("Tutorial", List.of("MainMenu"));

        //Define navigation paths for the LoadGame screen:
        //From the "LoadGame" Screen, the user can navigate FOR NOW ONLY back to the "MainMenu".
        //We will later implement the GamePlay Screen Navigation
        graph.addNavigation("loadGame", List.of("MainMenu", "GamePlay"));

        graph.addNavigation("Settings1", List.of("GamePlay", "ParentalControls2"));

        graph.addNavigation("ParentalControls2", List.of("Settings1"));

    }

    /**
     * Navigates to the specified scene by updating the stage's current scene.
     * This method dynamically configures the scene's content based on its type (e.g., Main Menu, Settings).
     *
     * @param sceneName The name of the scene to navigate to, as defined in the navigation graph.
     */
    private void navigateTo(String sceneName) {


        // Check if the specified scene exists in the navigation graph
        if (graph.hasScene(sceneName)) {
            // Retrieve the scene associated with the scene name
            Scene currentScene = graph.getScene(sceneName);


            // Perform actions specific to the scene being navigated to
            switch (sceneName) {
                case "MainMenu":
                    music.mainViewSound();
                    mainMenuButton(currentScene, gameSettings);
                    break;

                case "Settings":
                    settingsButton(currentScene, stage, "ParentalControls");
                    addReturnButton(currentScene, "MainMenu");
                    break;

                case "NewGame":
                    music.petSelectionSound();
                    petSelectionButtons(currentScene, stage);
                    addReturnButton(currentScene, "MainMenu");

                    break;

                case "Tutorial":
                    addReturnButton(currentScene, "MainMenu");
                    break;

                case "ParentalControls":
                    parentalControlsButtons(currentScene, gameSettings);
                    addReturnButton(currentScene, "Settings");
                    break;

                case "LoadGame":
                    // Load all saved games
                    savedGames = loadData.loadAllSavedGames();
                    loadGameButtons(currentScene, savedGames);
                    addReturnButton(currentScene, "MainMenu");
                    break;

                case "GamePlay":
                    music.gameplaySound();

                    if (isNewGame) {
                        // If it's a new game, initialize the new game instance
                        newSavedGameInstance();
                    }

                    // Proceed to the gameplay screen
                    gamePlayButtons(currentScene, stage, currentPlayer);
                    break;

                case "Settings1":
                    music.gameplaySound();
                    settingsButton(currentScene, stage, "ParentalControls2");
                    addReturnGamePlayButton(currentScene, "GamePlay");
                    break;

                case "ParentalControls2":
                    music.gameplaySound();
                    parentalControlsButtons(currentScene, gameSettings);
                    addReturnGamePlayButton(currentScene, "Settings1");

                default:
                    break;
            }

            // Set the retrieved scene as the current scene for the stage
            stage.setScene(currentScene);
        } else {
            // Log an error message if the specified scene is not found in the graph
            System.out.println("Scene not found: " + sceneName);
        }
    }

    /**
     * Configures the Main Menu screen with buttons for navigation and gameplay options.
     *
     * @param currentScene the scene object representing the Main Menu.
     * @param gameSettings the current game settings.
     */
    private void mainMenuButton(Scene currentScene, GameSettings gameSettings) {
        mainMenuButtonManager.createMainMenuButtons(currentScene, gameSettings);
    }

    /**
     * Configures the Pet Selection screen with buttons to choose a pet.
     *
     * @param currentScene the scene object representing the Pet Selection screen.
     * @param stage the primary stage of the application.
     */
    private void petSelectionButtons(Scene currentScene, Stage stage) {
        petSelectionButtons.createPetSelectionButton(currentScene, stage);
    }

    /**
     * Configures the Settings screen with buttons and navigation options.
     *
     * @param currentScene the scene object representing the Settings screen.
     * @param stage the primary stage of the application.
     * @param prevScreen the previous screen to return to.
     */
    private void settingsButton(Scene currentScene, Stage stage, String prevScreen) {
        settingsButtons.createSettingsButton(currentScene, stage, prevScreen);
    }

    /**
     * Configures the Parental Controls screen with the necessary buttons and UI elements.
     * Delegates the button creation and layout to the parentalControlButtons instance.
     *
     * @param currentScene The Scene object representing the Parental Controls screen.
     * @param gameSettings The GameSettings object containing the current parental controls and game settings.
     */
    private void parentalControlsButtons(Scene currentScene, GameSettings gameSettings) {
        parentalControlButtons.createParentalControlsButtons(currentScene, gameSettings);
    }

    /**
     * Configures the Load Game screen with buttons to display and load saved games.
     * Delegates the button creation and layout to the loadGameButtons instance.
     *
     * @param currentScene The Scene object representing the Load Game screen.
     * @param savedGames A List of SavedGame objects to display as selectable options.
     */
    private void loadGameButtons(Scene currentScene, List<SavedGame> savedGames) {
        loadGameButtons.createLoadGameButtons(currentScene, savedGames);
    }

    /**
     * Configures the GamePlay screen with interactive buttons and UI elements.
     *
     * @param currentScene the scene object representing the GamePlay screen.
     * @param stage the primary stage of the application.
     * @param player the current player in the game.
     */
    private void gamePlayButtons(Scene currentScene, Stage stage, Player player) {
        gamePlayButtons.createGamePlayButton(currentScene, stage, player);
    }

    /**
     * Dynamically positions the "Return" button at the bottom-left corner of the screen.
     *
     * @param currentScene The Scene object where the button will be added.
     * @param returnScene  The name of the scene to navigate to when the button is clicked.
     */
    private void addReturnButton(Scene currentScene, String returnScene) {
        // Get the root node of the current scene
        // The root is expected to be an AnchorPane
        AnchorPane root = (AnchorPane) currentScene.getRoot();

        // Check if the "Return" button already exists
        Button existingButton = (Button) root.lookup("#returnButton");
        if (existingButton != null) {
            return; // If the button already exists, exit the method
        }

        // Create the "Return" button
        Button returnButton = new Button("Return");
        returnButton.getStyleClass().add("returnMenuButton");
        returnButton.setId("returnButton"); // Assign an ID for future lookup

        // Set the button's action to navigate to the specified returnScene
        returnButton.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            navigateTo(returnScene);
        });

        // Add the button to the AnchorPane
        root.getChildren().add(returnButton);

        // Bind the button's position dynamically to the AnchorPane's size
        root.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            AnchorPane.setBottomAnchor(returnButton, 20.0); // 20px offset from the bottom
        });

        root.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            AnchorPane.setLeftAnchor(returnButton, 20.0); // 20px offset from the left
        });

        // Set initial position in case the size hasn't changed yet
        AnchorPane.setBottomAnchor(returnButton, 20.0);
        AnchorPane.setLeftAnchor(returnButton, 20.0);
    }

    /**
     * Adds a "Return" button specifically for returning to the GamePlay screen.
     *
     * @param currentScene the current scene to which the button is added.
     * @param returnScene the name of the GamePlay scene to navigate back to.
     */
    private void addReturnGamePlayButton(Scene currentScene, String returnScene) {
        // Get the root node of the current scene
        // The root is expected to be an AnchorPane
        AnchorPane root = (AnchorPane) currentScene.getRoot();

        // Check if the "Return" button already exists
        Button existingButton = (Button) root.lookup("#returnButton");
        if (existingButton != null) {
            return; // If the button already exists, exit the method
        }

        // Create the "Return" button
        Button returnButton = new Button("Return");
        returnButton.getStyleClass().add("returnMenuButton");
        returnButton.setId("returnButton"); // Assign an ID for future lookup

        // Set the button's action to navigate to the specified returnScene
        returnButton.setOnAction(e -> {
            // Play the sound effect
            Music.getInstance().playSoundEffect();
            navigateTo(returnScene);
        });

        // Add the button to the AnchorPane
        root.getChildren().add(returnButton);

        // Bind the button's position dynamically to the AnchorPane's size
        root.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            AnchorPane.setBottomAnchor(returnButton, 20.0); // 20px offset from the bottom
        });

        root.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            AnchorPane.setLeftAnchor(returnButton, 20.0); // 20px offset from the left
        });

        // Set initial position in case the size hasn't changed yet
        AnchorPane.setBottomAnchor(returnButton, 20.0);
        AnchorPane.setLeftAnchor(returnButton, 20.0);
    }

    /**
     * Initializes a new saved game instance and sets up initial gameplay state.
     */
    private void newSavedGameInstance() {
        this.currentPlayer = petSelectionButtons.getPlayer();
        this.currentPet = petSelectionButtons.getPlayer().getPet();


        // Create a new SavedGame instance for this session

        this.currentGame = new SavedGame();
        this.currentGame.playerInfo = new SavedGame.PlayerInfo();
        this.currentGame.petInfo = new SavedGame.PetInfo();


        // Populate initial game state
        this.currentGame.playerInfo.name = currentPet.getName();
        this.currentGame.playerInfo.score = currentPlayer.getScore();
        this.currentGame.petInfo.name = currentPet.getName();
        this.currentGame.petInfo.stats = new SavedGame.PetInfo.Stats();
        this.currentGame.petInfo.stats.health = (int) currentPet.getHealth();
        this.currentGame.petInfo.stats.fullness = (int) currentPet.getFullness();
        this.currentGame.petInfo.stats.happiness = (int) currentPet.getHappiness();
        this.currentGame.petInfo.stats.sleep = (int) currentPet.getSleep();
        this.currentGame.petInfo.stats.fullnessDepletionRate =  currentPet.getFullnessDepletionRate();
        this.currentGame.petInfo.stats.sleepDepletionRate =  currentPet.getSleepDepletionRate();
        this.currentGame.petInfo.stats.happinessDepletionRate =  currentPet.getHappinessDepletionRate();
        saveGame();

    }

    /**
     * Saves the current game state to a file.
     */
    public void saveGame() {
        updateGameplayState(); // Updates the `currentGame` object

        String saveName = currentPlayer.getPet().getName(); // Use pet's name as save file name

        LoadData loadData = new LoadData();
        loadData.saveNewGame(currentGame, saveName); // Save using LoadData

    }

    /**
     * Updates the gameplay state with the latest player and pet information.
     */
    private void updateGameplayState() {
        if (currentGame == null) {
            currentGame = new SavedGame();
            currentGame.playerInfo = new SavedGame.PlayerInfo();
            currentGame.petInfo = new SavedGame.PetInfo();
            currentGame.petInfo.stats = new SavedGame.PetInfo.Stats();
        }

        // Update player info
        currentGame.playerInfo.name = currentPlayer.getPet().getName(); // Player name is pet name
        currentGame.playerInfo.score = currentPlayer.getScore();
        currentGame.playerInfo.inventory = currentPlayer.getInventory().toSerializableMap();

        // Update pet info
        currentGame.petInfo.petType = currentPet.getPetType();
        currentGame.petInfo.name = currentPlayer.getPet().getName();
        currentGame.petInfo.stats.health = (int) currentPlayer.getPet().getHealth();
        currentGame.petInfo.stats.sleep = (int) currentPlayer.getPet().getSleep();
        currentGame.petInfo.stats.fullness = (int) currentPlayer.getPet().getFullness();
        currentGame.petInfo.stats.happiness = (int) currentPlayer.getPet().getHappiness();
        currentGame.petInfo.stats.fullnessDepletionRate =  currentPet.getFullnessDepletionRate();
        currentGame.petInfo.stats.sleepDepletionRate =  currentPet.getSleepDepletionRate();
        currentGame.petInfo.stats.happinessDepletionRate =  currentPet.getHappinessDepletionRate();
    }

    /**
     * Sets the current saved game object.
     *
     * @param savedGame the saved game to set as the current game.
     */
    public void setCurrentSavedGame(SavedGame savedGame) {
        this.currentGame = savedGame;
    }

    /**
     * Sets the current player object.
     *
     * @param player the player to set as the current player.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;

    }

    /**
     * Sets the current pet object.
     *
     * @param pet the pet to set as the current pet.
     */
    public void setCurrentPet(Pet pet) {
        this.currentPet = pet;
    }

    /**
     * Sets whether the game is a new game or not.
     *
     * @param isNewGame true if the game is a new game, false otherwise.
     */
    public void setNewGame(boolean isNewGame) {
        this.isNewGame = isNewGame;
    }


    /**
     * The main method to launch the JavaFX application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }


}

