package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoadDataTest {

    @Test
    void testSaveAndLoadNewGame() {
        // Initialize LoadData
        LoadData loadData = new LoadData();

        // Create PlayerInfo
        SavedGame.PlayerInfo playerInfo = new SavedGame.PlayerInfo();
        playerInfo.name = "TestPlayer"; // Set the name for PlayerInfo
        playerInfo.score = 100;

        // Create Inventory
        playerInfo.inventory = new HashMap<>();
        List<SavedGame.PlayerInfo.Item> items = new ArrayList<>();
        SavedGame.PlayerInfo.Item item = new SavedGame.PlayerInfo.Item();
        item.type = "Potion";
        item.quantity = 10;
        items.add(item);
        playerInfo.inventory.put("Potion", items);

        // Create PetInfo
        SavedGame.PetInfo petInfo = new SavedGame.PetInfo();
        petInfo.name = "TestPet";
        petInfo.petType = "Dog";
        SavedGame.PetInfo.Stats stats = new SavedGame.PetInfo.Stats();
        stats.health = 100;
        stats.sleep = 80;
        stats.fullness = 90;
        stats.happiness = 95;
        stats.fullnessDepletionRate = 1.5;
        stats.happinessDepletionRate = 1.0;
        stats.sleepDepletionRate = 0.5;
        petInfo.stats = stats;

        // Create SavedGame
        SavedGame savedGame = new SavedGame();
        savedGame.playerInfo = playerInfo;
        savedGame.petInfo = petInfo;

        // Save and load the game
        String saveName = "TestSave";
        loadData.saveNewGame(savedGame, saveName);
        SavedGame loadedGame = loadData.loadSavedGame(saveName);

        // Assertions
        assertNotNull(loadedGame, "Loaded game should not be null.");
        assertEquals("TestPlayer", loadedGame.getName(), "Player name should match.");
        assertEquals("TestPet", loadedGame.petInfo.name, "Pet name should match.");
        assertEquals(100, loadedGame.petInfo.stats.health, "Pet health should match.");
        assertEquals(1, loadedGame.playerInfo.inventory.get("Potion").size(), "Inventory should have one item.");
        assertEquals(10, loadedGame.playerInfo.inventory.get("Potion").get(0).quantity, "Item quantity should match.");
    }
}
