package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameSettings} class is responsible for managing the game settings, including
 * parental controls, play statistics, and sound settings.
 * @author Alan Cruz
 */
public class GameSettings {
    private Settings settings = new Settings();

    /**
     * Retrieves the current settings object.
     *
     * @return The {@link Settings} object.
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * The {@code Settings} class encapsulates overall game settings, such as sound, parental controls,
     * and play statistics.
     */
    public static class Settings {
        private boolean sound = true; // Default: sound enabled
        private ParentalControls parentalControls = new ParentalControls();
        private PlayStatistics playStatistics = new PlayStatistics();

        /**
         * Retrieves the parental controls settings.
         *
         * @return The {@link ParentalControls} object.
         */
        public ParentalControls getParentalControls() {
            return parentalControls;
        }

        /**
         * Retrieves the play statistics.
         *
         * @return The {@link PlayStatistics} object.
         */
        public PlayStatistics getPlayStatistics() {
            return playStatistics;
        }
    }

    /**
     * The {@code ParentalControls} class handles settings for restricting play hours
     * and enabling parental controls.
     */
    public static class ParentalControls {
        private boolean enabled = false; // Default: disabled
        private AllowedPlayHours allowedPlayHours = new AllowedPlayHours();

        /**
         * Checks if parental controls are enabled.
         *
         * @return {@code true} if enabled, {@code false} otherwise.
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * Sets the parental controls enabled state.
         *
         * @param enabled {@code true} to enable parental controls, {@code false} to disable.
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * Retrieves the allowed play hours.
         *
         * @return The {@link AllowedPlayHours} object.
         */
        public AllowedPlayHours getAllowedPlayHours() {
            return allowedPlayHours;
        }
    }

    /**
     * The {@code AllowedPlayHours} class specifies the time window during which gameplay is allowed.
     */
    public static class AllowedPlayHours {
        private String start = "08:00"; // Default start time
        private String end = "20:00";   // Default end time

        /**
         * Retrieves the start time of allowed play hours.
         *
         * @return The start time in "HH:mm" format.
         */
        public String getStart() {
            return start;
        }

        /**
         * Sets the start time of allowed play hours.
         *
         * @param start The start time in "HH:mm" format.
         * @throws IllegalArgumentException If the time format is invalid.
         */
        public void setStart(String start) {
            if (isValidTimeFormat(start)) {
                this.start = start;
            } else {
                throw new IllegalArgumentException("Invalid start time format. Expected HH:mm.");
            }
        }

        /**
         * Retrieves the end time of allowed play hours.
         *
         * @return The end time in "HH:mm" format.
         */
        public String getEnd() {
            return end;
        }

        /**
         * Sets the end time of allowed play hours.
         *
         * @param end The end time in "HH:mm" format.
         * @throws IllegalArgumentException If the time format is invalid.
         */
        public void setEnd(String end) {
            if (isValidTimeFormat(end)) {
                this.end = end;
            } else {
                throw new IllegalArgumentException("Invalid end time format. Expected HH:mm.");
            }
        }

        /**
         * Validates the time format.
         *
         * @param time The time string to validate.
         * @return {@code true} if the format is valid, {@code false} otherwise.
         */
        private boolean isValidTimeFormat(String time) {
            return time.matches("\\d{2}:\\d{2}"); // Matches "HH:mm"
        }
    }

    /**
     * The {@code PlayStatistics} class tracks gameplay statistics such as total play time,
     * average session time, and individual session durations.
     */
    public static class PlayStatistics {
        private float totalPlayTime = 0.0f; // Total play time in minutes (float for precision)
        private float averageSessionTime = 0.0f; // Average session time in minutes (float for precision)
        private String sessionStartTime = null; // Start time of the current session
        private List<Float> sessionDurations = new ArrayList<>(); // List to track durations of each session as floats

        /**
         * Retrieves the total play time.
         *
         * @return Total play time in minutes.
         */
        public float getTotalPlayTime() {
            return totalPlayTime;
        }

        /**
         * Retrieves the average session time.
         *
         * @return Average session time in minutes.
         */
        public float getAverageSessionTime() {
            return averageSessionTime;
        }

        /**
         * Retrieves the list of session durations.
         *
         * @return A list of session durations in minutes.
         */
        public List<Float> getSessionDurations() {
            return sessionDurations;
        }

        /**
         * Updates the play statistics for a session.
         *
         * @param sessionDuration The duration of the session in minutes.
         * @throws IllegalArgumentException If the session duration is negative.
         */
        public void updateStatistics(float sessionDuration) {
            if (sessionDuration < 0) {
                throw new IllegalArgumentException("Session duration cannot be negative.");
            }
            sessionDurations.add(sessionDuration); // Add the new session duration
            totalPlayTime += sessionDuration; // Update total play time
            int totalSessions = sessionDurations.size(); // Total number of sessions
            averageSessionTime = totalPlayTime / totalSessions; // Recalculate average session time
        }

        /**
         * Resets the play statistics.
         */
        public void resetStatistics() {
            this.totalPlayTime = 0.0f;
            this.averageSessionTime = 0.0f;
            this.sessionStartTime = null;
            this.sessionDurations.clear(); // Clear the session durations
        }
    }
}

