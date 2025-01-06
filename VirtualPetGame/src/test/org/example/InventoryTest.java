package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void testAddItem() {
        // Add an item that's already prepopulated in the inventory
        Food apple = new Food("Apple", 30, 5);
        inventory.addItem(apple);

        // Verify the updated quantity in the inventory (prepopulated quantity + added quantity)
        List<Item> items = inventory.getItemsByName("Apple");
        assertFalse(items.isEmpty());
        assertEquals(10, items.get(0).getQuantity()); // Prepopulated quantity (5) + added quantity (5)
    }


    @Test
    void testGetItemsByName() {
        // Prepopulated item in inventory
        List<Item> items = inventory.getItemsByName("Apple");
        assertFalse(items.isEmpty());
        assertEquals(5, items.get(0).getQuantity());
    }

    @Test
    void testGetItemsByCategory() {
        // Get all Food items
        List<Item> foodItems = inventory.getItemsByCategory("Food");
        assertFalse(foodItems.isEmpty());

        // Get all Gift items
        List<Item> giftItems = inventory.getItemsByCategory("Gift");
        assertFalse(giftItems.isEmpty());
    }

    @Test
    void testHasItem() {
        // Verify prepopulated item exists
        assertTrue(inventory.hasItem("Apple"));

        // Verify non-existent item
        assertFalse(inventory.hasItem("NonExistentItem"));
    }

    @Test
    void testUseItem() {
        // Use a prepopulated item
        inventory.useItem("Apple");

        // Verify the quantity is decreased
        List<Item> items = inventory.getItemsByName("Apple");
        assertFalse(items.isEmpty());
        assertEquals(4, items.get(0).getQuantity());
    }

    @Test
    void testUseItemEmptyStack() {
        // Add an item with quantity 1
        Food singleApple = new Food("SingleApple", 30, 1);
        inventory.addItem(singleApple);

        // Use the item
        inventory.useItem("SingleApple");

        // Verify the item is removed from the inventory
        assertFalse(inventory.hasItem("SingleApple"));
    }

    @Test
    void testToString() {
        // Test the inventory's string representation
        String inventoryString = inventory.toString();
        assertTrue(inventoryString.contains("Apple x5"));
        assertTrue(inventoryString.contains("Key Chain x5"));
    }

    @Test
    void testToSerializableMap() {
        // Convert to a serializable map
        Map<String, List<SavedGame.PlayerInfo.Item>> serializableMap = inventory.toSerializableMap();

        // Verify the serialized data
        assertTrue(serializableMap.containsKey("Apple"));
        assertFalse(serializableMap.get("Apple").isEmpty());
        assertEquals(5, serializableMap.get("Apple").get(0).quantity);
    }

    @Test
    void testFromSerializableMap() {
        // Create a serialized map
        SavedGame.PlayerInfo.Item appleItem = new SavedGame.PlayerInfo.Item();
        appleItem.type = "Food";
        appleItem.quantity = 3;

        SavedGame.PlayerInfo.Item giftItem = new SavedGame.PlayerInfo.Item();
        giftItem.type = "Gift";
        giftItem.quantity = 2;

        Map<String, List<SavedGame.PlayerInfo.Item>> serializedMap = Map.of(
                "Apple", List.of(appleItem),
                "Gift", List.of(giftItem)
        );

        // Deserialize the map
        Inventory deserializedInventory = Inventory.fromSerializableMap(serializedMap);

        // Verify deserialized data
        List<Item> appleItems = deserializedInventory.getItemsByName("Apple");
        assertFalse(appleItems.isEmpty());
        assertEquals(3, appleItems.get(0).getQuantity());

        List<Item> giftItems = deserializedInventory.getItemsByName("Gift");
        assertFalse(giftItems.isEmpty());
        assertEquals(2, giftItems.get(0).getQuantity());
    }
}
