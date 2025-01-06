package org.example;


import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * The Sprite class is responsible for managing and displaying different visual states
 * of a pet or character in the game. It maintains a map of states, where each state is
 * associated with an {@link ImageView}.
 *
 *
 * @author Bashar Hamo
 *
 */
public class Sprite {
    private ImageView currentState;
    private Map<String, ImageView> petStates;

    /**
     * Constructs a new Sprite instance with an empty state map.
     */
    public Sprite() {
        petStates = new HashMap<>();
    }

    /**
     * Gets the current state of the sprite.
     *
     * @return the current {@link ImageView} representing the sprite's state.
     */
    public ImageView getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state of the sprite.
     *
     * @param stateName the name of the state to set as the current state.
     * @throws IllegalArgumentException if the specified state does not exist in the state map.
     */
    public void setState(String stateName) {
        // Ensure the state exists in the map before setting
        if (petStates.containsKey(stateName)) {
            this.currentState = petStates.get(stateName);
        } else {
            throw new IllegalArgumentException("State '" + stateName + "' does not exist.");
        }
    }


    /**
     * Adds a new state to the sprite's state map.
     *
     * @param stateName the name of the state to add.
     * @param state the {@link ImageView} representing the visual state.
     * @throws IllegalArgumentException if the state name or image is null.
     */
    public void addState(String stateName, ImageView state) {
        if (stateName == null || state == null) {
            throw new IllegalArgumentException("State name and image cannot be null.");
        }
        petStates.put(stateName, state);
    }



}
