package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * The Animations class manages a sequence of animations for a set of {@link ImageView} objects.
 * It creates and controls a timeline to animate the opacity of the images in a looping pattern.
 *
 * @author Alan Cruz
 */
public class    Animations {

    private final Timeline timeline;

    /**
     * Constructor to initialize animations for a sequence of ImageViews.
     *
     * @param imageViews The ImageViews involved in the animation.
     */
    public Animations(ImageView... imageViews) {
        this.timeline = createImageTransitionTimeline(imageViews);
    }

    /**
     * Creates a timeline for animating the opacity of ImageViews.
     *
     * @param imageViews The ImageViews to animate.
     * @return Configured Timeline.
     */
    private Timeline createImageTransitionTimeline(ImageView[] imageViews) {
        return new Timeline(
                new KeyFrame(Duration.seconds(0), e -> setOpacities(imageViews, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0)),
                new KeyFrame(Duration.seconds(1), e -> setOpacities(imageViews, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1)),
                new KeyFrame(Duration.seconds(2), e -> setOpacities(imageViews, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0))
        );
    }

    /**
     * Sets the opacity values for a sequence of ImageViews.
     *
     * @param imageViews The ImageViews.
     * @param opacities  The opacity values corresponding to each ImageView.
     */
    private void setOpacities(ImageView[] imageViews, double... opacities) {
        for (int i = 0; i < imageViews.length && i < opacities.length; i++) {
            imageViews[i].setOpacity(opacities[i]);
        }
    }

    /**
     * Starts the animation.
     */
    public void play() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        timeline.stop();
    }
}
