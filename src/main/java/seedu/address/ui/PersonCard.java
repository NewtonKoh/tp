package seedu.address.ui;

import java.util.Comparator;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.AnimationUtil;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final TranslateTransition moveTransition = AnimationUtil.getMoveTransition(getRoot());
    private final TranslateTransition bounceBackTransition = AnimationUtil.getBounceBackTransition(getRoot());
    private final FadeTransition fadeInTransition = AnimationUtil.getFadeInTransition(getRoot());
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

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, boolean animate) {
        super(FXML);
        this.person = person;
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
            playAnimation();
        }
    }

    private void playAnimation() {
        fadeInTransition.playFromStart();
        moveTransition.playFromStart();
        bounceBackTransition.playFromStart();
    }

    public HBox getCardPane() {
        return cardPane;
    }
}
