package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code LoadData} class manages the saving and loading of game data.
 * It handles game settings, saved games, and ensures the required file structure exists at runtime.
 * @author Alan Cruz
 */
public class LoadData {
    private static final String SAVE_FOLDER = "./SavedFiles"; // Writable runtime folder
    private static final String GAME_SETTINGS_FILE = SAVE_FOLDER + "/game_settings.json"; // Writable game settings path
    private static final String SAVED_GAMES_FOLDER = SAVE_FOLDER + "/SavedGames"; // Writable saved games folder
    private final Gson gson;

    /**
     * Constructs a new {@code LoadData} object and ensures the necessary folders and files exist.
     */
    public LoadData() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        // Ensure the save folders exist
        createFolderIfNotExists(SAVE_FOLDER);
        createFolderIfNotExists(SAVED_GAMES_FOLDER);

        // Ensure the game_settings.json file exists with default values
        ensureGameSettingsFileExists();
    }

    /**
     * Loads a saved game from the specified save file.
     *
     * @param saveName the name of the save file (without extension).
     * @return a {@code SavedGame} object if the file is found and valid, or {@code null} otherwise.
     * @throws JsonSyntaxException if the file's content cannot be parsed as a {@code SavedGame}.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public SavedGame loadSavedGame(String saveName) {
        // Construct the file path for the save file
        File file = new File(SAVED_GAMES_FOLDER, saveName + ".json");

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("Save file not found: " + file.getAbsolutePath());
            return null; // Return null if the file doesn't exist
        }

        try (FileReader reader = new FileReader(file)) {
            // Deserialize the JSON file into a SavedGame object using Gson
            return gson.fromJson(reader, SavedGame.class);
        } catch (IOException e) {
            System.err.println("Error reading save file: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing save file: " + e.getMessage());
        }

        return null; // Return null if an error occurs
    }

    /**
     * Ensures a folder exists, creating it if necessary.
     *
     * @param folderPath the path to the folder.
     */
    private void createFolderIfNotExists(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Folder created: " + folderPath);
            } else {
                System.err.println("Failed to create folder: " + folderPath);
            }
        }
    }

    /**
     * Ensures the {@code game_settings.json} file exists, creating a default one if necessary.
     */
    private void ensureGameSettingsFileExists() {
        File file = new File(GAME_SETTINGS_FILE);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                GameSettings defaultSettings = new GameSettings(); // Create default settings
                gson.toJson(defaultSettings, writer);
            } catch (IOException e) {
                System.err.println("Error creating default game_settings.json: " + e.getMessage());
            }
        }
    }

    /**
     * Loads the game settings from the {@code game_settings.json} file.
     *
     * @return a {@code GameSettings} object, or {@code null} if an error occurs.
     * @throws JsonSyntaxException if the file's content cannot be parsed as {@code GameSettings}.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public GameSettings loadGameSettings() {
        File file = new File(GAME_SETTINGS_FILE);
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, GameSettings.class);
        } catch (IOException e) {
            System.err.println("Error loading game settings: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves the specified game settings to the {@code game_settings.json} file.
     *
     * @param settings the {@code GameSettings} object to save.
     * @throws IOException if an I/O error occurs while writing the file.
     */
    public void saveGameSettings(GameSettings settings) {
        try (FileWriter writer = new FileWriter(GAME_SETTINGS_FILE)) {
            gson.toJson(settings, writer);
        } catch (IOException e) {
            System.err.println("Error saving game settings: " + e.getMessage());
        }
    }

    /**
     * Loads all saved games from the {@code SavedGames} folder.
     *
     * @return a list of {@code SavedGame} objects representing all valid saved games.
     * @throws JsonSyntaxException if any file's content cannot be parsed as a {@code SavedGame}.
     * @throws IOException if an I/O error occurs while reading a file.
     */
    public List<SavedGame> loadAllSavedGames() {
        List<SavedGame> savedGames = new ArrayList<>();
        File folder = new File(SAVED_GAMES_FOLDER);

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json")); // Filter JSON files
        if (files != null) {
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    SavedGame savedGame = gson.fromJson(reader, SavedGame.class);
                    savedGames.add(savedGame);
                } catch (IOException e) {
                    System.err.println("Error loading saved game from file: " + file.getName() + " - " + e.getMessage());
                }
            }
        }
        return savedGames;
    }

    /**
     * Saves a new game to the {@code SavedGames} folder with the specified name.
     *
     * @param savedGame the {@code SavedGame} object to save.
     * @param saveName  the name of the save file (without extension).
     * @throws IOException if an I/O error occurs while writing the file.
     */
    public void saveNewGame(SavedGame savedGame, String saveName) {
        File file = new File(SAVED_GAMES_FOLDER + "/" + saveName + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(savedGame, writer);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }
}
