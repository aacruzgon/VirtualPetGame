package org.example;

import java.util.List;
import java.util.Map;


/**
 * Represents a saved game, including player information and pet information.
 * @author Alan Cruz
 * @author Arianna Song
 */
public class SavedGame {
    public PlayerInfo playerInfo;
    public PetInfo petInfo;

    /**
     * Retrieves the name of the saved game.
     *
     * @return the player's name if available; otherwise, "Unnamed Save".
     */
    public String getName() {
        // Return the player's name if available; otherwise, return a default value
        return (playerInfo != null && playerInfo.name != null) ? playerInfo.name : "Unnamed Save";
    }

    /**
     * Represents information about the player.
     */
    public static class PlayerInfo {
        public String name;
        public int score;
        public Map<String, List<Item>> inventory;

        /**
         * The player's inventory, organized as a map where the key is the item type and the value is a list of items.
         */
        public static class Inventory {
            public Map<String, List<Item>> items; // Same as in your Inventory class
        }

        /**
         * Represents an item in the player's inventory.
         */
        public static class Item {
            public String type;
            public int quantity;
        }
    }

    /**
     * Represents information about the pet.
     */
    public static class PetInfo {
        public String petType;
        public String name;
        public Stats stats;

        /**
         * Represents the stats of the pet, including various attributes and their depletion rates.
         */
        public static class Stats {
            public int health;
            public int sleep;
            public int fullness;
            public int happiness;
            public double fullnessDepletionRate;
            public double happinessDepletionRate;
            public double sleepDepletionRate;
        }
    }
}
