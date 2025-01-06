package org.example;

/**
 * The {@code GamePlayLoop} class handles the core game logic and state updates.
 * It periodically updates the pet's stats, replenishes inventory items, and checks the pet's state to
 * determine if the game is over.
 * @author Bashar Hamo
 * @author Alan Cruz
 */
public class GamePlayLoop {
    private Player player;
    private Pet pet;
    private Inventory inventory;

    private long lastUpdateTime; // Last update for depleting stats
    private long lastReplenishTime; // Last inventory replenishment time

    private static final long UPDATE_INTERVAL = 5_000_000_000L; // 5 seconds in nanoseconds
    private static final long REPLENISH_INTERVAL = 300_000L; // 5 minutes in milliseconds

    private boolean gameOver;
    private boolean gameStarted;

    /**
     * Creates a new {@code GamePlayLoop} for the specified player.
     *
     * @param player the player whose game state will be managed.
     */
    public GamePlayLoop(Player player) {
        this.player = player;
        this.pet = player.getPet();
        this.inventory = player.getInventory();
        this.lastUpdateTime = System.nanoTime();
        this.lastReplenishTime = System.currentTimeMillis();
        this.gameOver = false;
        this.gameStarted = true;
    }

    /**
     * Updates the game logic by periodically adjusting the pet's stats and replenishing inventory items.
     * Also checks the pet's state to determine if the game is over.
     */
    public void updateGameLogic() {
        if (gameOver) return;

        long currentTimeNano = System.nanoTime();
        long currentTimeMillis = System.currentTimeMillis();

        // Update pet stats periodically
        if (currentTimeNano - lastUpdateTime >= UPDATE_INTERVAL) {
            pet.updateStats(); // Deplete stats or regenerate sleep
            checkPetState();
            lastUpdateTime = currentTimeNano;
        }

        // Replenish inventory periodically
        if (currentTimeMillis - lastReplenishTime >= REPLENISH_INTERVAL) {
            replenishInventory();
            lastReplenishTime = currentTimeMillis;
        }
    }

    /**
     * Checks the state of the pet to determine if it is dead.
     * If the pet is dead, the game is marked as over.
     */
    private void checkPetState() {
        if (pet.isDead()) {
            gameOver = true;
            // Additional logic to handle game over (e.g., navigate to game over screen)
        }
    }

    /**
     * Replenishes the player's inventory with random food and gift items.
     * Adds two food items and two gift items to the inventory.
     */
    private void replenishInventory() {
        String[] foods = {"Apple", "Bread", "Cheese", "Banana", "Carrot"};
        String[] gifts = {"Toy", "Collar", "Ball", "Blanket"};
        for (int i = 0; i < 2; i++) {
            String foodName = foods[(int)(Math.random() * foods.length)];
            inventory.addItem(new Food(foodName, 20 + (int)(Math.random() * 10), 5));
        }
        for (int i = 0; i < 2; i++) {
            String giftName = gifts[(int)(Math.random() * gifts.length)];
            inventory.addItem(new Gift(giftName, 25 + (int)(Math.random() * 10), 5));
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Checks if the game has started.
     *
     * @return {@code true} if the game has started, {@code false} otherwise.
     */
    public boolean isGameStarted() {
        return gameStarted;
    }
}
