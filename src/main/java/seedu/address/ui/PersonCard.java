package seedu.address.ui;

import java.util.Comparator;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double MOVE_DURATION = 100;
    private static final double DELAY_DURATION = 50;
    private static final double MOVE_INITIAL = 70;
    private static final double MOVE_POP = 20;
    private static final double FADE_DURATION = 150;
    private static final double FADE_INITIAL = 0.5;
    private static final double FADE_ULTIMATE = 1;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final int index;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label moneyOwed;
    @FXML
    private FlowPane daysAvailable;
    private TranslateTransition moveTransition;
    private TranslateTransition moveBackTransition;
    private FadeTransition fadeInTransition;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, boolean animate) {
        super(FXML);
        this.person = person;
        this.index = displayedIndex;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        moneyOwed.setText(person.getMoneyOwed().getMessage());
        person.getDaysAvailable().stream()
                .sorted(Comparator.comparing(Enum::ordinal))
                .forEach(day -> daysAvailable.getChildren().add(new Label(day.getShortForm())));
        if (animate) {
            setUpAnimation();
        }
    }

    private void setUpAnimation() {
        double globalDelay = (index - 1) * DELAY_DURATION;
        moveTransition = new TranslateTransition(Duration.millis(MOVE_DURATION), this.getRoot());
        double originalPosition = this.getRoot().getTranslateX();
        moveTransition.setFromX(originalPosition + MOVE_INITIAL);
        moveTransition.setToX(originalPosition - MOVE_POP);
        moveTransition.setDelay(Duration.millis(globalDelay));
        moveBackTransition = new TranslateTransition(Duration.millis(MOVE_DURATION), this.getRoot());
        moveBackTransition.setFromX(originalPosition - MOVE_POP);
        moveBackTransition.setToX(originalPosition);
        moveBackTransition.setDelay(Duration.millis(MOVE_DURATION + globalDelay));
        fadeInTransition = new FadeTransition(Duration.millis(FADE_DURATION), this.getRoot());
        fadeInTransition.setFromValue(FADE_INITIAL);
        fadeInTransition.setToValue(FADE_ULTIMATE);
        fadeInTransition.setDelay(Duration.millis(globalDelay));
        fadeInTransition.playFromStart();
        moveTransition.playFromStart();
        moveBackTransition.playFromStart();
    }

    public HBox getCardPane() {
        return cardPane;
    }
}
