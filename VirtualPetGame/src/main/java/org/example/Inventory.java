package org.example;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Inventory class manages a collection of items, organized by their names and categories.
 * It supports adding, retrieving, and using items, and can serialize and deserialize its contents
 * for saved game functionality.
 *
 *
 * @author Alan Cruz
 * @author Bashar Hamo
 *
 */
public class Inventory {
    // Map to store lists of items, with the item name as the key and a list of Item objects as the value
    private Map<String, List<Item>> items;

    /**
     * Constructs an Inventory instance with an empty items map and prepopulates it with default items.
     */
    public Inventory() {
        items = new HashMap<>(); // Initialize the items map as a HashMap
        initializeInventory(); // Prepopulate the inventory
    }

    /**
     * Constructs an Inventory instance with an empty items map, typically for saved games.
     *
     * @param SavedGame a marker to indicate the inventory is being initialized for a saved game.
     */
    public Inventory(String SavedGame){
        items = new HashMap<>();
    }

    /**
     * Adds an item to the inventory. If the item already exists, its quantity is updated.
     *
     * @param item the item to be added.
     */
    public void addItem(Item item) {

        //items.forEach((key, value) -> System.out.println(key + ": " + value.size() + " stacks"));

        items.computeIfAbsent(item.getName(), k -> new ArrayList<>())
                .stream()
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> {

                            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());

                        },
                        () -> {
                            items.get(item.getName()).add(item);
                        }
                );
    }



    /**
     * Retrieves a list of items with the specified name.
     *
     * @param name the name of the items to retrieve.
     * @return a list of items matching the specified name.
     */
    public List<Item> getItemsByName(String name) {
        return items.getOrDefault(name, new ArrayList<>());
    }

    /**
     * Retrieves a list of items by their category (e.g., "Food", "Gift").
     *
     * @param category the category of items to retrieve.
     * @return a list of items matching the specified category.
     */
    public List<Item> getItemsByCategory(String category) {
        List<Item> categorizedItems = new ArrayList<>();
        for (List<Item> itemList : items.values()) {
            if (!itemList.isEmpty() && itemList.get(0) instanceof Item) {
                Item firstItem = itemList.get(0);
                if (firstItem instanceof Food && category.equalsIgnoreCase("Food")) {
                    categorizedItems.addAll(itemList);
                } else if (firstItem instanceof Gift && category.equalsIgnoreCase("Gift")) {
                    categorizedItems.addAll(itemList);
                }
            }
        }
        return categorizedItems;
    }

    /**
     * Checks if the inventory contains at least one item with the specified name.
     *
     * @param name the name of the item to check for.
     * @return {@code true} if the inventory contains the item, otherwise {@code false}.
     */
    public boolean hasItem(String name) {
        List<Item> itemList = items.get(name);
        return itemList != null && !itemList.isEmpty();
    }

    /**
     * Returns a string representation of the inventory, including the names and quantities of all items.
     *
     * @return a string representing the inventory's contents.
     */
    @Override
    public String toString() {
        StringBuilder inventoryString = new StringBuilder("Inventory:\n");
        for (Map.Entry<String, List<Item>> entry : items.entrySet()) {
            String itemName = entry.getKey(); // The item's name
            List<Item> itemList = entry.getValue(); // All instances of this item
            int totalQuantity = 0;

            // Aggregate the total quantity for this item
            for (Item item : itemList) {
                totalQuantity += item.getQuantity();
            }

            inventoryString.append(itemName)
                    .append(" x")
                    .append(totalQuantity)
                    .append("\n");
        }
        return inventoryString.toString();
    }

    /**
     * Initializes the inventory with predefined items and their respective quantities.
     */
    public void initializeInventory() {
        addItem(new Food("Apple", 30, 5));
        addItem(new Food("Banana", 35, 5));
        addItem(new Food("Bread", 25, 5));
        addItem(new Food("Cheese", 15, 5));
        addItem(new Food("Coffee", 10, 5));
        addItem(new Gift("Collar", 25, 5));
        addItem(new Gift("Bowl", 30, 5));
        addItem(new Gift("Key Chain", 40, 5));
        addItem(new Gift("Portrait", 50, 5));
    }

    /**
     * Converts the inventory to a serializable map format for saving.
     *
     * @return a map representing the serialized inventory.
     */
    public Map<String, List<SavedGame.PlayerInfo.Item>> toSerializableMap() {
        Map<String, List<SavedGame.PlayerInfo.Item>> serializableMap = new HashMap<>();
        for (Map.Entry<String, List<Item>> entry : items.entrySet()) {
            List<SavedGame.PlayerInfo.Item> serializedItems = new ArrayList<>();
            for (Item item : entry.getValue()) {
                SavedGame.PlayerInfo.Item serializedItem = new SavedGame.PlayerInfo.Item();

                // Use the category instead of the item name for the serialized type
                if (item instanceof Food) {
                    serializedItem.type = ((Food) item).getCategory();
                } else if (item instanceof Gift) {
                    serializedItem.type = ((Gift) item).getCategory();
                } else {
                    serializedItem.type = "Unknown"; // Fallback type for items with no specific category
                }

                serializedItem.quantity = item.getQuantity(); // Get the quantity from the Item object
                serializedItems.add(serializedItem); // Add the serialized item to the list
            }
            serializableMap.put(entry.getKey(), serializedItems); // Add the list to the map
        }
        return serializableMap;
    }

    /**
     * Creates an Inventory instance from a serialized map.
     *
     * @param serializedMap the serialized inventory map.
     * @return an Inventory instance initialized with the deserialized items.
     */
    public static Inventory fromSerializableMap(Map<String, List<SavedGame.PlayerInfo.Item>> serializedMap) {
        Inventory inventory = new Inventory("SavedGame");
        for (Map.Entry<String, List<SavedGame.PlayerInfo.Item>> entry : serializedMap.entrySet()) {
            String itemName = entry.getKey(); // The item's name (e.g., "Apple", "Key Chain", "Portrait")
            List<SavedGame.PlayerInfo.Item> serializedItems = entry.getValue();

            for (SavedGame.PlayerInfo.Item serializedItem : serializedItems) {
                if ("Food".equals(serializedItem.type)) {
                    // Create a Food item and add it to the inventory
                    Food food = new Food(itemName, 30, serializedItem.quantity); // Replace 30 with the appropriate fullness if needed
                    inventory.addItem(food);
                } else if ("Gift".equals(serializedItem.type)) {
                    // Create a Gift item and add it to the inventory
                    Gift gift = new Gift(itemName, 25, serializedItem.quantity); // Replace 25 with the appropriate happiness if needed
                    inventory.addItem(gift);
                } else {
                    // Log unknown types for debugging purposes
                    System.out.println("Unknown item type: " + serializedItem.type + " for item: " + itemName);
                }
            }
        }

        return inventory;
    }

    /**
     * Retrieves the internal map of items.
     *
     * @return the map of items in the inventory.
     */
    public Map<String, List<Item>> getItems() {
        return items;
    }

    /**
     * Uses an item by decreasing its quantity by one. Removes the item if its quantity reaches zero.
     *
     * @param itemName the name of the item to use.
     */
    public void useItem(String itemName) {
        List<Item> itemList = items.get(itemName);
        if (itemList != null && !itemList.isEmpty()) {
            Item firstItem = itemList.get(0); // Get the first item in the stack
            firstItem.decreaseQuantity(1); // Decrease quantity by 1
            if (firstItem.isEmpty()) {
                itemList.remove(0); // Remove the item if quantity is zero
                if (itemList.isEmpty()) {
                    items.remove(itemName); // Remove the key if the stack is empty
                }
            }
        } else {
            System.out.println("No " + itemName + " available to use!");
        }
    }


}
