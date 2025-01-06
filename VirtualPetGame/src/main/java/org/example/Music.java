package org.example;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Singleton class to manage music and sound effects.
 * Provides methods to preload, play, and control various sound effects and background music.
 * @author Arianna Song
 * @author Alan Cruz
 */
public class Music {

    /** Singleton instance of the Music class. */
    private static Music instance;

    /** MediaPlayer to manage the current audio. */
    private MediaPlayer currentPlayer;

    /** The file path of the currently playing sound. */
    private String currentSoundFile;

    /** Volume level, ranging from 0.0 (silent) to 1.0 (full volume). */
    private double volume = 1.0;

    /** Indicates whether all sounds are muted. */
    private boolean isMuted = false;

    /** Indicates whether button click sounds are muted. */
    private boolean isButtonClickMuted = false;

    /** Pool for button click sound effects. */
    private final List<MediaPlayer> buttonClickSoundPool = new ArrayList<>();
    private int nextSoundIndex = 0;

    /** Pool for gift sound effects. */
    private final List<MediaPlayer> playSoundPool = new ArrayList<>();
    private int nextPlaySoundIndex = 0;

    /** Pool for gym sound effects. */
    private final List<MediaPlayer> playGymPool = new ArrayList<>();
    private int nextGymSoundIndex = 0;

    /** Pool for vet sound effects. */
    private final List<MediaPlayer> vetSoundPool = new ArrayList<>();
    private int nextVetSoundIndex = 0;

    /** Pool for sleep sound effects. */
    private final List<MediaPlayer> sleepSoundPool = new ArrayList<>();
    private int nextSleepSoundIndex = 0;

    /** Map for storing food-related sound effects by food name. */
    private final Map<String, MediaPlayer> foodSoundEffects = new HashMap<>();

    /** Map for storing gift-related sound effects by gift name. */
    private final Map<String, MediaPlayer> giftSoundEffects = new HashMap<>();

    /**
     * Preloads sound effects for food items.
     */
    private void preloadFoodSoundEffects() {
        preloadFoodSound("apple", "/Sounds/apple.wav");
        preloadFoodSound("cheese", "/Sounds/cheese.wav");
        preloadFoodSound("coffee", "/Sounds/coffee.wav");
        preloadFoodSound("bread", "/Sounds/bread.wav");
        preloadFoodSound("banana", "/Sounds/cheese.wav");
    }

    /**
     * Preloads sound effects for gift items.
     */
    private void preloadGiftSoundEffects() {
        preloadGiftSound("Key Chain", "/Sounds/collar.wav");
        preloadGiftSound("Portrait", "/Sounds/bowl.wav");
        preloadGiftSound("Bowl", "/Sounds/bowl.wav");
        preloadGiftSound("Collar", "/Sounds/collar.wav");
    }

    /**
     * Preloads a single food sound.
     *
     * @param foodName the name of the food.
     * @param soundFilePath the path to the sound file.
     */
    private void preloadFoodSound(String foodName, String soundFilePath) {
        String soundPath = getClass().getResource(soundFilePath).toExternalForm();
        Media sound = new Media(soundPath);
        MediaPlayer player = new MediaPlayer(sound);
        foodSoundEffects.put(foodName.toLowerCase(), player); // Store in the map with lowercase key
    }

    /**
     * Preloads a single gift sound.
     *
     * @param giftName the name of the gift.
     * @param soundFilePath the path to the sound file.
     */
    private void preloadGiftSound(String giftName, String soundFilePath) {
        String soundPath = getClass().getResource(soundFilePath).toExternalForm();
        Media sound = new Media(soundPath);
        MediaPlayer player = new MediaPlayer(sound);
        giftSoundEffects.put(giftName.toLowerCase(), player);
    }

    /**
     * Private constructor to prevent external instantiation.
     * Preloads sound effects and initializes sound pools.
     */
    private Music() {
        preloadButtonClickSound("/Sounds/buttonClick.wav", 5); // Preload pool with 5 instances
        preloadFoodSoundEffects(); // Load the specific food sounds
        preloadGiftSoundEffects(); // Load the specific gift sounds
        preloadPlaySound("/Sounds/playSound.wav", 5);
        preloadGymSound("/Sounds/gymSound.wav", 5);
        preloadVetSound("/Sounds/playSound.wav", 5);
        preloadSleepSound("/Sounds/sleepSound.wav", 5);
    }


    /**
     * Retrieves the singleton instance of the Music class.
     *
     * @return the single instance of Music.
     */
    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    /**
     * Preloads play sound effects into a pool.
     *
     * @param soundFilePath the path to the sound file.
     * @param poolSize the number of instances to preload.
     */
    private void preloadPlaySound(String soundFilePath, int poolSize) {
        String soundPath = getClass().getResource(soundFilePath).toExternalForm();
        Media sound = new Media(soundPath);

        for (int i = 0; i < poolSize; i++) {
            MediaPlayer player = new MediaPlayer(sound);
            playSoundPool.add(player);
        }
    }

    /**
    * Preloads a pool of gym sound effects.
    *
    * @param soundFilePath the file path of the gym sound effect.
    * @param poolSize the number of sound instances to preload.
    */
    private void preloadGymSound(String soundFilePath, int poolSize) {
        String soundPath = getClass().getResource(soundFilePath).toExternalForm();
        Media sound = new Media(soundPath);

        for (int i = 0; i < poolSize; i++) {
            MediaPlayer player = new MediaPlayer(sound);
            playGymPool.add(player);
        }
    }

    /**
    * Preloads a pool of vet sound effects.
    *
    * @param soundFilePath the file path of the vet sound effect.
    * @param poolSize the number of sound instances to preload.
    */
    private void preloadVetSound(String soundFilePath, int poolSize) {
        String soundPath = getClass().getResource(soundFilePath).toExternalForm();
        Media sound = new Media(soundPath);

        for (int i = 0; i < poolSize; i++) {
            MediaPlayer player = new MediaPlayer(sound);
            vetSoundPool.add(player);
        }
    }

    /**
    * Preloads a pool of sleep sound effects.
    *
    * @param soundFilePath the file path of the sleep sound effect.
    * @param poolSize the number of sound instances to preload.
    */
    private void preloadSleepSound(String soundFilePath, int poolSize) {
        String soundPath = getClass().getResource(soundFilePath).toExternalForm();
        Media sound = new Media(soundPath);

        for (int i = 0; i < poolSize; i++) {
            MediaPlayer player = new MediaPlayer(sound);
            sleepSoundPool.add(player);
        }
    }

    /**
     * Preloads a pool of MediaPlayers for a sound effect.
     *
     * @param soundFilePath the file path to the sound effect.
     * @param poolSize the number of MediaPlayers to preload.
     */
    private void preloadButtonClickSound(String soundFilePath, int poolSize) {
        String soundPath = getClass().getResource(soundFilePath).toExternalForm();
        Media sound = new Media(soundPath);

        for (int i = 0; i < poolSize; i++) {
            MediaPlayer player = new MediaPlayer(sound);
            buttonClickSoundPool.add(player);
        }
    }

    /**
     * Preloads play sound effects into a pool.
     *
     * @param soundFilePath the path to the sound file.
     * @param poolSize the number of instances to preload.
     */
    public void playPlaySound() {
        if (playSoundPool.isEmpty() || isButtonClickMuted) return;

        MediaPlayer player = playSoundPool.get(nextPlaySoundIndex);
        player.stop(); // Reset if already playing
        player.seek(javafx.util.Duration.ZERO);
        player.setVolume(isMuted ? 0.0 : volume);
        player.play();

        nextPlaySoundIndex = (nextPlaySoundIndex + 1) % playSoundPool.size();
    }

    /**
    * Plays the gym sound effect from the preloaded pool.
    */
    public void playGymSound() {
        if (playGymPool.isEmpty() || isButtonClickMuted) return;

        MediaPlayer player = playGymPool.get(nextGymSoundIndex);
        player.stop(); // Reset if already playing
        player.seek(javafx.util.Duration.ZERO);
        player.setVolume(isMuted ? 0.0 : volume);
        player.play();

        nextGymSoundIndex = (nextGymSoundIndex + 1) % playGymPool.size();
    }

    /**
    * Plays the vet sound effect from the preloaded pool.
    */
    public void playVetSound() {
        if (vetSoundPool.isEmpty() || isButtonClickMuted) return;

        MediaPlayer player = vetSoundPool.get(nextVetSoundIndex);
        player.stop(); // Reset if already playing
        player.seek(javafx.util.Duration.ZERO);
        player.setVolume(isMuted ? 0.0 : volume);
        player.play();

        nextVetSoundIndex = (nextVetSoundIndex + 1) % vetSoundPool.size();
    }

    /**
    * Plays the sleep sound effect from the preloaded pool.
    */
    public void playSleepSound() {
        if (sleepSoundPool.isEmpty() || isButtonClickMuted) return;

        MediaPlayer player = sleepSoundPool.get(nextSleepSoundIndex);
        player.stop(); // Reset if already playing
        player.seek(javafx.util.Duration.ZERO);
        player.setVolume(isMuted ? 0.0 : volume);
        player.play();

        nextSleepSoundIndex = (nextSleepSoundIndex + 1) % sleepSoundPool.size();
    }


    /**
     * Plays a preloaded button click sound effect.
     */
    public void playSoundEffect() {
        if (buttonClickSoundPool.isEmpty() || isButtonClickMuted) return;

        MediaPlayer player = buttonClickSoundPool.get(nextSoundIndex);
        player.stop(); // Reset if already playing
        player.seek(javafx.util.Duration.ZERO);
        player.setVolume(isMuted ? 0.0 : volume);
        player.play();

        // Move to the next player in the pool
        nextSoundIndex = (nextSoundIndex + 1) % buttonClickSoundPool.size();
    }

    /**
    * Plays a sound effect associated with a specific food item.
    *
    * @param foodName the name of the food item.
    */
    public void playFoodSound(String foodName) {
        if (foodSoundEffects.containsKey(foodName.toLowerCase())) {
            MediaPlayer player = foodSoundEffects.get(foodName.toLowerCase());
            player.stop();
            player.seek(javafx.util.Duration.ZERO);
            player.setVolume(isMuted ? 0.0 : volume);
            player.play();
        } else {
            System.out.println("No sound effect found for food: " + foodName);
        }
    }

    /**
    * Plays a sound effect associated with a specific gift item.
    *
    * @param giftName the name of the gift item.
    */
    public void playGiftSound(String giftName) {
        if (giftSoundEffects.containsKey(giftName.toLowerCase())) {
            MediaPlayer player = giftSoundEffects.get(giftName.toLowerCase());
            player.stop();
            player.seek(javafx.util.Duration.ZERO);
            player.setVolume(isMuted ? 0.0 : volume);
            player.play();
        } else {
            System.out.println("No sound effect found for gift: " + giftName);
        }
    }

    /**
    * Toggles muting of button click sound effects.
    *
    * @param mute {@code true} to mute button click sounds, {@code false} to unmute.
    */
    public void setMuteButtonClick(boolean mute) {
        isButtonClickMuted = mute;
    }

    /**
    * Stops any currently playing music or sound.
    */
    public void stopMusic() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer = null;
        }
    }

    /**
    * Adjusts the sound level.
    *
    * @param level the new volume level as a percentage (0 to 100).
    */
    public void adjustSoundLevel(int level) {
        volume = level / 100.0; // Convert percentage to a range between 0.0 and 1.0
        if (currentPlayer != null && !isMuted) {
            currentPlayer.setVolume(volume);
        }
    }

    /**
     * Mutes all sounds.
     */
    public void muteSound() {
        isMuted = true;
        if (currentPlayer != null) {
            currentPlayer.setVolume(0.0);
        }

    }

    /**
     * Unmutes all sounds.
     */
    public void unmuteSound() {
        isMuted = false;
        if (currentPlayer != null) {
            currentPlayer.setVolume(volume);
        }
    }

    /**
    * Plays a specific track.
    *
    * @param soundFilePath the file path of the sound to play.
    */
    public void playSound(String soundFilePath) {
        // Check if the file is already playing
        if (currentSoundFile != null && currentSoundFile.equals(soundFilePath)) {
            return; // Avoid restarting the same sound
        }

        stopMusic(); // Stop any existing music

        String soundPath = getClass().getResource(soundFilePath) != null ?
                getClass().getResource(soundFilePath).toExternalForm() : null;

        if (soundPath != null) {
            Media sound = new Media(soundPath);
            currentPlayer = new MediaPlayer(sound);
            currentPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
            currentPlayer.setVolume(isMuted ? 0.0 : volume); // Apply current volume or mute state
            currentPlayer.play();

            // Update currentSoundFile to the new sound file
            currentSoundFile = soundFilePath;


        } else {

            currentSoundFile = null; // Reset currentSoundFile if sound file is not found
        }
    }

    /**
     * Plays music for the MainView.
     */
    public void mainViewSound() {
        String mainViewSoundFile = getMainViewSoundFile();
        if (!mainViewSoundFile.equals(currentSoundFile)) {
            playSound(mainViewSoundFile);
        }
    }

    /**
     * Plays music for the Gameplay.
     */
    public void gameplaySound() {
        String gameplaySoundFile = getgameplaySoundFile();
        if (!gameplaySoundFile.equals(currentSoundFile)) {
            playSound(gameplaySoundFile);
        }
    }

    /**
    * Gets the sound file path for the main view.
    *
    * @return the main view sound file path.
    */
    private String getMainViewSoundFile() {
        return "/Sounds/PetSociety-HomeTheme.wav";
    }
    
    /**
    * Gets the sound file path for the gameplay view.
    *
    * @return the gameplay view sound file path.
    */
    private String getgameplaySoundFile() {
        return "/Sounds/PetSociety-ShopTheme.wav";
    }

    /**
    * Gets the current volume level.
    *
    * @return the volume level as a double (0.0 to 1.0).
    */
    public double getVolume() {
        return volume;
    }

    /**
     * Plays music for the PetSelectionView.
     */
    public void petSelectionSound() {
        String petSelectionViewSoundFile = getPetSelectionViewSoundFile();
        if (!petSelectionViewSoundFile.equals(currentSoundFile)) {
            playSound(petSelectionViewSoundFile);
        }
    }

    /**
     * Gets the sound file path for the PetSelectionView.
     *
     * @return the PetSelectionView sound file path.
     */
    private String getPetSelectionViewSoundFile() {
        return "/Sounds/PetSociety-FishingTheme.wav";
    }

    /**
     * Checks if the sound is muted.
     *
     * @return {@code true} if sound is muted, {@code false} otherwise.
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * Checks if button click sounds are muted.
     *
     * @return {@code true} if button click sounds are muted, {@code false} otherwise.
     */
    public boolean isButtonClickMuted() {
        return isButtonClickMuted;
    }
}