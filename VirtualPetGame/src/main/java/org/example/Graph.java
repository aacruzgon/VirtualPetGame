/**
 * Represents a graph structure for managing scenes and their navigation paths.
 *
 * <p>The {@code Graph} class is designed to manage a collection of scenes and define
 * valid navigation paths between them. It provides methods to add scenes, define
 * navigation paths, and retrieve information about scenes and their connections.</p>
 *
 * @author Alan Cruz
 */
package org.example;

import javafx.scene.Scene;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private final Map<String, Scene> sceneGraph = new HashMap<>(); // Maps scene names to Scene objects
    private final Map<String, List<String>> navigationGraph = new HashMap<>(); // Maps scene names to their navigable scenes

    /**
     * Adds a scene to the graph with the specified name.
     *
     * @param name  the unique name of the scene
     * @param scene the {@code Scene} object to be added
     */
    public void addScene(String name, Scene scene) {
        sceneGraph.put(name, scene);
    }

    /**
     * Defines valid navigation paths from one scene to others.
     *
     * @param from the name of the source scene
     * @param to   a list of names of target scenes that can be navigated to from the source scene
     */
    public void addNavigation(String from, List<String> to) {
        navigationGraph.put(from, to);
    }

    /**
     * Retrieves a scene by its name.
     *
     * @param name the name of the scene
     * @return the {@code Scene} object associated with the given name, or {@code null} if no such scene exists
     */
    public Scene getScene(String name) {
        return sceneGraph.get(name);
    }

    /**
     * Retrieves the list of valid navigation paths for a given scene.
     *
     * @param name the name of the source scene
     * @return a list of names of navigable target scenes, or {@code null} if no navigation paths are defined for the scene
     */
    public List<String> getNavigableScenes(String name) {
        return navigationGraph.get(name);
    }

    /**
     * Checks if a scene with the specified name exists in the graph.
     *
     * @param name the name of the scene to check
     * @return {@code true} if the scene exists, {@code false} otherwise
     */
    public boolean hasScene(String name) {
        return sceneGraph.containsKey(name);
    }
}

