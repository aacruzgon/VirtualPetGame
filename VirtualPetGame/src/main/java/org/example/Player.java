package org.example;

/**
 * The Player class represents a player in the virtual pet game. It manages the player's
 * inventory, pet, and score, as well as interactions with the pet such as feeding, playing,
 * and taking the pet to the vet.
 *
 *
 * @author Bashar Hamo
 *
 */
public class Player {
    private Inventory inventory;
    private Pet pet;
    private int score;

    /**
     * Constructs a new Player with a default inventory and an initial score of 0.
     */
    public Player() {
        inventory = new Inventory();
        score = 0;
    }

    /**
     * Constructs a new Player with a specified inventory.
     *
     * @param inventory the inventory to assign to the player.
     */
    public Player(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Sets the pet associated with this player.
     *
     * @param pet the pet to associate with the player.
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Sets the player's score.
     *
     * @param score the score to assign to the player.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets the player's inventory.
     *
     * @param inventory the inventory to assign to the player.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the pet associated with this player.
     *
     * @return the pet associated with the player.
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Gets the player's inventory.
     *
     * @return the player's inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the player's current score.
     *
     * @return the player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Feeds the player's pet with the specified food item.
     * The pet cannot be fed if it is angry, sleeping, or dead.
     *
     * @param food the food item to feed the pet.
     */
    public void feedPet(Food food) {
        if (pet.isAngry() || pet.isSleeping() || pet.isDead()) {
            return;
        }
        if (inventory.hasItem(food.getName())) {
            pet.feed(food);
            inventory.useItem(food.getName()); // Use `useItem` to decrement the quantity
            updateScore(20);
        }
    }

    /**
     * Gifts the player's pet with the specified gift item.
     * The pet cannot be gifted if it is sleeping or dead.
     *
     * @param gift the gift item to give to the pet.
     */
    public void giftPet(Gift gift) {
        if (pet.isSleeping() || pet.isDead()) {
            return;
        }
        if (inventory.hasItem(gift.getName())) {
            pet.gift(gift);
            inventory.useItem(gift.getName()); // Use `useItem` to decrement the quantity
            updateScore(30);
        }
    }

    /**
     * Takes the player's pet to the vet to improve its health.
     * The pet cannot be taken to the vet if it is dead, sleeping, or angry.
     * Reduces the player's score by 50.
     */
    public void takePetToVet() {
        if (pet.isDead() || pet.isSleeping() || pet.isAngry()) return;

        pet.setHealth(pet.getHealth() + 50);
        updateScore(-50);
        Music.getInstance().playVetSound();
    }

    /**
     * Plays with the player's pet to improve its happiness.
     * The pet cannot play if it is dead or sleeping.
     * Increases the player's score by 10.
     */
    public void playWithPet() {
        if(pet.isDead() || pet.isSleeping()) return;
        pet.play();
        updateScore(10);
    }

    /**
     * Exercises the player's pet to improve its physical fitness.
     * The pet cannot exercise if it is dead, sleeping, or angry.
     * Increases the player's score by 15.
     */
    public void exercisePet() {
        if (pet.isDead() || pet.isSleeping() || pet.isAngry()) return;
        pet.exercise();
        updateScore(15);
    }

    /**
     * Puts the player's pet to bed to restore its energy.
     * The pet cannot be put to bed if it is dead, sleeping, or angry.
     * Increases the player's score by 5.
     */
    public void putPetToBed() {
        if(pet.isDead() || pet.isSleeping() || pet.isAngry()) return;
        pet.goToSleep();
        updateScore(5);
    }

    /**
     * Updates the player's score by the specified amount. The score cannot go below zero.
     *
     * @param amount the amount to adjust the score by (positive or negative).
     */
    private void updateScore(int amount) {
        score = Math.max(0, score + amount);
    }
}
