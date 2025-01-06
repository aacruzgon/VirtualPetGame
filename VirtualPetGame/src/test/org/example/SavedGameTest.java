package org.example; // Update this to match your package structure

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SavedGameTest {

    private SavedGame savedGame;

    @BeforeEach
    void setUp() {
        savedGame = new SavedGame();
    }

    @Test
    void testGetNameWhenPlayerInfoAndNameAreSet() {
        SavedGame.PlayerInfo playerInfo = new SavedGame.PlayerInfo();
        playerInfo.name = "Player1";
        savedGame.playerInfo = playerInfo;

        assertEquals("Player1", savedGame.getName());
    }

    @Test
    void testGetNameWhenPlayerInfoIsNull() {
        savedGame.playerInfo = null;

        assertEquals("Unnamed Save", savedGame.getName());
    }

    @Test
    void testGetNameWhenPlayerNameIsNull() {
        SavedGame.PlayerInfo playerInfo = new SavedGame.PlayerInfo();
        playerInfo.name = null;
        savedGame.playerInfo = playerInfo;

        assertEquals("Unnamed Save", savedGame.getName());
    }

    @Test
    void testPlayerInfoInventoryInitialization() {
        SavedGame.PlayerInfo playerInfo = new SavedGame.PlayerInfo();
        playerInfo.inventory = new HashMap<>();

        List<SavedGame.PlayerInfo.Item> potions = new ArrayList<>();
        SavedGame.PlayerInfo.Item potion = new SavedGame.PlayerInfo.Item();
        potion.type = "Potion";
        potion.quantity = 5;
        potions.add(potion);

        playerInfo.inventory.put("Potion", potions);
        savedGame.playerInfo = playerInfo;

        assertNotNull(savedGame.playerInfo.inventory);
        assertTrue(savedGame.playerInfo.inventory.containsKey("Potion"));
        assertEquals(5, savedGame.playerInfo.inventory.get("Potion").get(0).quantity);
    }

    @Test
    void testPetInfoInitialization() {
        SavedGame.PetInfo petInfo = new SavedGame.PetInfo();
        petInfo.petType = "Dog";
        petInfo.name = "Buddy";

        savedGame.petInfo = petInfo;

        assertEquals("Dog", savedGame.petInfo.petType);
        assertEquals("Buddy", savedGame.petInfo.name);
    }
}
