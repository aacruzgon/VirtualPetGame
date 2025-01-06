/**
 * Functional interface for handling navigation between scenes.
 *
 * <p>The {@code NavigationHandler} interface defines a single abstract method,
 * {@code navigate}, which allows implementation of custom navigation logic
 * between scenes in an application. This interface is designed to be used
 * with lambda expressions or method references for flexibility and simplicity.</p>
 *
 * @author JaeHoon Jung
 */
package org.example;

@FunctionalInterface
public interface NavigationHandler {

    /**
     * Handles navigation to a specified scene.
     *
     * @param sceneName the name of the scene to navigate to
     */
    void navigate(String sceneName);
}
