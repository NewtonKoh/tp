package seedu.address.commons.util;

import org.junit.jupiter.api.Test;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class AnimationUtilTest {
    @Test
    public void getBounceBackTransition() {
        Node node = new VBox();
        AnimationUtil.getBounceBackTransition(node);
    }

    @Test
    public void getMoveTransition() {
        Node node = new VBox();
        AnimationUtil.getMoveTransition(node);
    }

    @Test
    public void getFadeInTransition() {
        Node node = new VBox();
        AnimationUtil.getFadeInTransition(node);
    }
}
