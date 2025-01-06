package org.example;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
/**
 * Utility class for managing the positioning of UI elements based on background image scaling.
 *
 * @author Alan Cruz
 */

public class PetMath {

    /**
     * Updates the position of an AnchorPane based on the scaling of a background image.
     *
     * @param backgroundImage         The background image being scaled.
     * @param originalBackgroundWidth The original width of the background image.
     * @param originalBackgroundHeight The original height of the background image.
     * @param originalLayoutX         The original X-coordinate of the AnchorPane.
     * @param originalLayoutY         The original Y-coordinate of the AnchorPane.
     * @param pane                    The AnchorPane whose position is being updated.
     */
    // Method to update the position of any AnchorPane based on background scaling
    protected void updatePosition(ImageView backgroundImage, double originalBackgroundWidth, double originalBackgroundHeight,
                                  double originalLayoutX, double originalLayoutY, AnchorPane pane) {
        // Get the current scaled width and height of the background image
        double scaledWidth = backgroundImage.getFitWidth();  // Get the scaled width
        double scaledHeight = backgroundImage.getFitHeight();  // Get the scaled height

        // Calculate the scaling factors based on the original and scaled size
        double scaleFactorX = scaledWidth / originalBackgroundWidth;
        double scaleFactorY = scaledHeight / originalBackgroundHeight;

        // Adjust the pane position based on the scaling factor
        double newLayoutX = originalLayoutX * scaleFactorX;
        double newLayoutY = originalLayoutY * scaleFactorY;

        // Set the new position of the AnchorPane
        pane.setLayoutX(newLayoutX);
        pane.setLayoutY(newLayoutY);


    }

    /**
     * Updates the position of a VBox based on the scaling of a background image.
     *
     * @param backgroundImage         The background image being scaled.
     * @param originalBackgroundWidth The original width of the background image.
     * @param originalBackgroundHeight The original height of the background image.
     * @param originalLayoutX         The original X-coordinate of the VBox.
     * @param originalLayoutY         The original Y-coordinate of the VBox.
     * @param pane                    The VBox whose position is being updated.
     */
    // Method to update the position of any AnchorPane based on background scaling
    protected void updatePosition(ImageView backgroundImage, double originalBackgroundWidth, double originalBackgroundHeight,
                                  double originalLayoutX, double originalLayoutY, Pane pane) {
        // Get the current scaled width and height of the background image
        double scaledWidth = backgroundImage.getFitWidth();  // Get the scaled width
        double scaledHeight = backgroundImage.getFitHeight();  // Get the scaled height

        // Calculate the scaling factors based on the original and scaled size
        double scaleFactorX = scaledWidth / originalBackgroundWidth;
        double scaleFactorY = scaledHeight / originalBackgroundHeight;

        // Adjust the pane position based on the scaling factor
        double newLayoutX = originalLayoutX * scaleFactorX;
        double newLayoutY = originalLayoutY * scaleFactorY;

        // Set the new position of the AnchorPane
        pane.setLayoutX(newLayoutX);
        pane.setLayoutY(newLayoutY);
    }

    /**
     * Updates the position of a StackPane based on the scaling of a background image.
     *
     * @param backgroundImage         The background image being scaled.
     * @param originalBackgroundWidth The original width of the background image.
     * @param originalBackgroundHeight The original height of the background image.
     * @param originalLayoutX         The original X-coordinate of the StackPane.
     * @param originalLayoutY         The original Y-coordinate of the StackPane.
     * @param pane                    The StackPane whose position is being updated.
     */
    // Method to update the position of any AnchorPane based on background scaling
    protected void updatePosition(ImageView backgroundImage, double originalBackgroundWidth, double originalBackgroundHeight,
                                  double originalLayoutX, double originalLayoutY, StackPane pane) {
        // Get the current scaled width and height of the background image
        double scaledWidth = backgroundImage.getFitWidth();  // Get the scaled width
        double scaledHeight = backgroundImage.getFitHeight();  // Get the scaled height

        // Calculate the scaling factors based on the original and scaled size
        double scaleFactorX = scaledWidth / originalBackgroundWidth;
        double scaleFactorY = scaledHeight / originalBackgroundHeight;

        // Adjust the pane position based on the scaling factor
        double newLayoutX = originalLayoutX * scaleFactorX;
        double newLayoutY = originalLayoutY * scaleFactorY;

        // Set the new position of the AnchorPane
        pane.setLayoutX(newLayoutX);
        pane.setLayoutY(newLayoutY);
    }


    /**
     * Updates the position of a ScrollPane based on the scaling of a background image.
     *
     * @param backgroundImage         The background image being scaled.
     * @param originalBackgroundWidth The original width of the background image.
     * @param originalBackgroundHeight The original height of the background image.
     * @param originalLayoutX         The original X-coordinate of the ScrollPane.
     * @param originalLayoutY         The original Y-coordinate of the ScrollPane.
     * @param pane                    The ScrollPane whose position is being updated.
     */
    protected void updatePosition(ImageView backgroundImage, double originalBackgroundWidth, double originalBackgroundHeight,
                                  double originalLayoutX, double originalLayoutY, ScrollPane pane) {
        // Get the current scaled width and height of the background image
        double scaledWidth = backgroundImage.getFitWidth();  // Get the scaled width
        double scaledHeight = backgroundImage.getFitHeight();  // Get the scaled height

        // Calculate the scaling factors based on the original and scaled size
        double scaleFactorX = scaledWidth / originalBackgroundWidth;
        double scaleFactorY = scaledHeight / originalBackgroundHeight;

        // Adjust the pane position based on the scaling factor
        double newLayoutX = originalLayoutX * scaleFactorX;
        double newLayoutY = originalLayoutY * scaleFactorY;

        // Set the new position of the AnchorPane
        pane.setLayoutX(newLayoutX);
        pane.setLayoutY(newLayoutY);
    }


}
