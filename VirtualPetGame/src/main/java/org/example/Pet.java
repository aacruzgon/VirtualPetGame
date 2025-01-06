package org.example;

/**
 * The Pet class represents a virtual pet with attributes such as health, fullness,
 * happiness, and sleep. It manages the pet's state, behaviors, and interactions with
 * the player, such as feeding, playing, and sleeping.
 *
 *
 * @author Bashar Hamo
 *
 */
public class Pet {
    public String petType;
    private String name;
    private Sprite sprite;

    private double health;
    private double fullness;
    private double happiness;
    private double sleep;

    private double fullnessDepletionRate;
    private double happinessDepletionRate;
    private double sleepDepletionRate;

    private boolean isDead;
    private boolean isSleeping;
    private boolean isHungry;
    private boolean isAngry;

    // Penalty flags
    private boolean sleepPenaltyApplied;
    private boolean hungerPenaltyApplied;

    /**
     * Constructs a new Pet instance with specified attributes.
     *
     * @param petType the type of the pet (e.g., species or category).
     * @param name the name of the pet.
     * @param sprite the sprite representing the pet's visual states.
     * @param health the initial health level of the pet.
     * @param fullness the initial fullness level of the pet.
     * @param happiness the initial happiness level of the pet.
     * @param sleep the initial sleep level of the pet.
     * @param fullnessDepletionRate the rate at which fullness decreases over time.
     * @param happinessDepletionRate the rate at which happiness decreases over time.
     * @param sleepDepletionRate the rate at which sleep decreases over time.
     */
    public Pet(String petType, String name, Sprite sprite, double health, double fullness, double happiness, double sleep, double fullnessDepletionRate, double happinessDepletionRate, double sleepDepletionRate) {
        this.petType = petType;
        this.name = name;
        this.sprite = sprite;

        this.health = health;
        this.fullness = fullness;
        this.happiness = happiness;
        this.sleep = sleep;

        this.fullnessDepletionRate = fullnessDepletionRate;
        this.happinessDepletionRate = happinessDepletionRate;
        this.sleepDepletionRate = sleepDepletionRate;

        this.isDead = false;
        this.isSleeping = false;
        this.isHungry = false;
        this.isAngry = false;

        this.sleepPenaltyApplied = false;
        this.hungerPenaltyApplied = false;

        this.sprite.setState("default");
    }

    /**
     * Updates the pet's stats in the core loop, handling stat depletion, sleep regeneration,
     * and state updates based on current conditions.
     */
    public void updateStats() {
        if (isDead) return;

        if (!isSleeping) {
            depleteStats();
        } else {
            regenerateSleep();
        }

        updateState();
    }

    /**
     * Depletes the pet's stats such as fullness, happiness, and sleep based on their respective rates.
     */
    public void depleteStats() {
        health = Math.max(0, health);
        fullness = Math.max(0, fullness - fullnessDepletionRate);
        happiness = Math.max(0, happiness - happinessDepletionRate);
        sleep = Math.max(0, sleep - sleepDepletionRate);
    }

    /**
     * Regenerates the pet's sleep stat while it is sleeping.
     */
    private void regenerateSleep() {
        if (sleep < 100) {
            sleep += 5.0; // Adjust regeneration speed as needed
            Music.getInstance().playSleepSound();
        } else {
            isSleeping = false;
            sprite.setState("default");
        }
    }

    /**
     * Updates the pet's state based on its stats, such as setting it to "angry", "hungry",
     * "sleeping", or "dead".
     */
    private void updateState() {
        if (health <= 0) {
            die();
            return;
        }

        if (sleep <= 0 && !sleepPenaltyApplied) {
            health = Math.max(0, health - 20.0);
            isSleeping = true;
            sprite.setState("sleep");
            sleepPenaltyApplied = true;
        } else if (sleep > 0) {
            sleepPenaltyApplied = false;
        }

        if (fullness <= 0 && !hungerPenaltyApplied) {
            happinessDepletionRate += 0.5;
            health = Math.max(0, health - 5.0);
            isHungry = true;
            sprite.setState("sad");
            hungerPenaltyApplied = true;
        } else if (fullness > 0) {
            isHungry = false;
            hungerPenaltyApplied = false;
        }

        if (happiness <= 0) {
            isAngry = true;
            sprite.setState("angry");
        } else {
            isAngry = false;
        }

        if (!isSleeping && !isHungry && !isAngry && health > 0) {
            sprite.setState("default");
        }
    }

    /**
     * Marks the pet as dead and updates its sprite to reflect the "dead" state.
     */
    private void die() {
        isDead = true;
        sprite.setState("dead");
    }

    /**
     * Puts the pet to sleep, updating its state and sprite.
     */
    public void goToSleep() {
        if (isSleeping || isDead || isAngry) return;
        isSleeping = true;
        sprite.setState("sleep");
    }

    /**
     * Feeds the pet with the specified food, increasing fullness and reducing happiness depletion rate.
     *
     * @param food the food item to feed the pet.
     */
    public void feed(Food food) {
        if (isDead || isSleeping || isAngry) {
            return;
        }

        fullness = Math.min(100.0, fullness + food.getFullness());
        happinessDepletionRate = Math.max(0, happinessDepletionRate - 0.5);
        isHungry = false;
        Music.getInstance().playFoodSound(food.getName());

    }

    /**
     * Gives a gift to the pet, increasing its happiness.
     *
     * @param gift the gift item to give to the pet.
     */
    public void gift(Gift gift) {
        if (isDead || isSleeping) {
            return;
        }

        happiness = Math.min(100.0, happiness + gift.getValue());
        isAngry = happiness < 50;
        Music.getInstance().playGiftSound(gift.getName());
    }

    /**
     * Plays with the pet, increasing its happiness.
     */
    public void play() {
        if (isDead || isSleeping) {
            return;
        }
        happiness = Math.min(100.0, happiness + 20.0);
        Music.getInstance().playPlaySound();
    }

    /**
     * Exercises the pet, increasing its health and depleting fullness and sleep.
     */
    public void exercise() {
        if (isDead || isSleeping || isAngry) {
            return;
        }

        health = Math.min(100.0, health + 15.0);
        fullness = Math.max(0, fullness - 10.0);
        sleep = Math.max(0, sleep - 15.0);
        Music.getInstance().playGymSound();

    }

    // Getters

    /**
     * Checks if the pet is dead.
     *
     * @return {@code true} if the pet is dead, otherwise {@code false}.
     */
    public boolean isDead() { return isDead; }
    /**
     * Checks if the pet is sleeping.
     *
     * @return {@code true} if the pet is sleeping, otherwise {@code false}.
     */
    public boolean isSleeping() { return isSleeping; }
    /**
     * Checks if the pet is hungry.
     *
     * @return {@code true} if the pet is hungry, otherwise {@code false}.
     */
    public boolean isHungry() { return isHungry; }
    /**
     * Checks if the pet is angry.
     *
     * @return {@code true} if the pet is angry, otherwise {@code false}.
     */
    public boolean isAngry() { return isAngry; }
    /**
     * Gets the pet's current health level.
     *
     * @return the health level of the pet.
     */
    public double getHealth() { return health; }
    /**
     * Gets the pet's current fullness level.
     *
     * @return the fullness level of the pet.
     */
    public double getFullness() { return fullness; }
    /**
     * Gets the pet's current happiness level.
     *
     * @return the happiness level of the pet.
     */
    public double getHappiness() { return happiness; }
    /**
     * Gets the pet's current sleep level.
     *
     * @return the sleep level of the pet.
     */
    public double getSleep() { return sleep; }
    /**
     * Gets the sprite associated with the pet.
     *
     * @return the sprite object representing the pet.
     */
    public Sprite getSprite() { return sprite; }
    /**
     * Gets the name of the pet.
     *
     * @return the name of the pet.
     */
    public String getName() { return name; }
    /**
     * Gets the type of the pet.
     *
     * @return the type of the pet.
     */
    public String getPetType() { return petType; }
    /**
     * Gets the rate at which fullness depletes.
     *
     * @return the fullness depletion rate.
     */
    public double getFullnessDepletionRate() { return fullnessDepletionRate; }
    /**
     * Gets the rate at which happiness depletes.
     *
     * @return the happiness depletion rate.
     */
    public double getHappinessDepletionRate() { return happinessDepletionRate; }
    /**
     * Gets the rate at which sleep depletes.
     *
     * @return the sleep depletion rate.
     */
    public double getSleepDepletionRate() { return sleepDepletionRate; }

    // Setters

    /**
     * Sets the pet's health level. Clamps the value between 0 and 100.
     *
     * @param health the health level to set.
     */
    public void setHealth(double health) {
        this.health = Math.max(0, Math.min(100, health)); // Clamp between 0 and 100
        updateState();
    }
}