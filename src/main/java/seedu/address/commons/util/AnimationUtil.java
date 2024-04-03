package seedu.address.commons.util;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Utility methods for animations
 */
public class AnimationUtil {
    private static final double MOVE_DURATION = 200;
    private static final double MOVE_INITIAL = 500;
    private static final double MOVE_POP = 20;
    private static final double FADE_DURATION = 300;
    private static final double FADE_INITIAL = 0;
    private static final double FADE_ULTIMATE = 1;

    public static TranslateTransition getBounceBackTransition(Node node) {
        double originalPosition = node.getTranslateX();
        TranslateTransition bounceBackTransition = new TranslateTransition(Duration.millis(MOVE_DURATION), node);
        bounceBackTransition.setFromX(originalPosition - MOVE_POP);
        bounceBackTransition.setToX(originalPosition);
        bounceBackTransition.setDelay(Duration.millis(MOVE_DURATION));
        return bounceBackTransition;
    }

    public static FadeTransition getFadeInTransition(Node node) {
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(FADE_DURATION), node);
        fadeInTransition.setFromValue(FADE_INITIAL);
        fadeInTransition.setToValue(FADE_ULTIMATE);
        return fadeInTransition;
    }

    public static TranslateTransition getMoveTransition(Node node) {
        double originalPosition = node.getTranslateX();
        TranslateTransition moveTransition = new TranslateTransition(Duration.millis(MOVE_DURATION), node);
        moveTransition.setFromX(originalPosition + MOVE_INITIAL);
        moveTransition.setToX(originalPosition - MOVE_POP);
        return moveTransition;
    }
}
