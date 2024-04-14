package seedu.address.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_STUB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AVAILABLE_MONDAY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_ADDRESS_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_ADDRESS_ICON_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_BIRTHDAY_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_BIRTHDAY_ICON_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_DAY_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_DAY_ICON_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_EMAIL_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_EMAIL_ICON_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_MONEY_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_MONEY_ICON_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_NAME_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_PHONE_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_PHONE_ICON_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_REMARK_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_TAG_FX_CODE;
import static seedu.address.ui.UiTestUtil.DISPLAY_CARD_TAG_ICON_FX_CODE;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class DisplayCardTest extends ApplicationTest {

    private DisplayCard displayCard;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Person testPerson;

    @Override
    public void start(Stage stage) {
        // Setup a person with comprehensive details for testing
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        testPerson = new PersonBuilder(firstPerson)
                .withRemark(REMARK_STUB)
                .withDaysAvailable(VALID_DAY_AVAILABLE_MONDAY)
                .build();

        displayCard = new DisplayCard(testPerson);

        UiTestUtil.setUpSystem();

        // Set up the scene
        Scene scene = new Scene(displayCard.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void execute_displayAttributesCorrectText_success() {
        verifyThat(DISPLAY_CARD_NAME_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getName().toString());
        });
        verifyThat(DISPLAY_CARD_PHONE_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getPhone().toString());
        });
        verifyThat(DISPLAY_CARD_ADDRESS_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getAddress().toString());
        });
        verifyThat(DISPLAY_CARD_EMAIL_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getEmail().toString());
        });
        verifyThat(DISPLAY_CARD_REMARK_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getRemark().toString());
        });
        verifyThat(DISPLAY_CARD_BIRTHDAY_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getBirthday().toString());
        });
    }

    @Test
    public void execute_tagCountMatchesPersonTags_success() {

        verifyThat(DISPLAY_CARD_TAG_FX_CODE, (FlowPane flowPane) -> {
            return flowPane.getChildren().size() == testPerson.getTags().size();
        });
    }

    @Test
    public void execute_tagNamesDisplayCorrectly_success() {

        verifyThat(DISPLAY_CARD_TAG_FX_CODE, (FlowPane flowPane) -> {
            if (testPerson.getTags().size() == 0) {
                return true;
            }
            for (int i = 0; i < testPerson.getTags().size(); ++i) {
                if (!((Label) flowPane.getChildren().get(i)).getText()
                        .equals(((Tag) testPerson.getTags().toArray()[i]).tagName)) {
                    return false;
                }
            }
            return true;
        });
    }

    @Test
    public void execute_daysAvailableCountMatchesPersonDays_success() {

        verifyThat(DISPLAY_CARD_DAY_FX_CODE, (FlowPane flowPane) -> {
            return flowPane.getChildren().size() == testPerson.getDaysAvailable().size();
        });
    }

    @Test
    public void execute_daysAvailableDisplayCorrectly_success() {

        verifyThat(DISPLAY_CARD_DAY_FX_CODE, (FlowPane flowPane) -> {
            if (testPerson.getDaysAvailable().size() == 0) {
                return true;
            }
            for (int i = 0; i < testPerson.getDaysAvailable().size(); ++i) {
                if (!((Label) flowPane.getChildren().get(i)).getText()
                        .equals(((Day) testPerson.getDaysAvailable().toArray()[i]).getShortForm())) {
                    return false;
                }
            }
            return true;
        });
    }

    @Test
    public void execute_moneyOwedMessageDisplayCorrectly_success() {
        verifyThat(DISPLAY_CARD_MONEY_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getMoneyOwed().getMessage());
        });
    }

    @Test
    public void execute_iconsVisibilityCorrect_success() {
        verifyThat(DISPLAY_CARD_TAG_ICON_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_DAY_ICON_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_PHONE_ICON_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_ADDRESS_ICON_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_EMAIL_ICON_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_BIRTHDAY_ICON_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_MONEY_ICON_FX_CODE, NodeMatchers.isVisible());
    }

    @Test
    public void execute_visibilityOfElementsCorrectVisibility_success() {
        verifyThat(DISPLAY_CARD_NAME_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_PHONE_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_ADDRESS_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_EMAIL_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_REMARK_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_BIRTHDAY_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_MONEY_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_TAG_FX_CODE, NodeMatchers.isVisible());
        verifyThat(DISPLAY_CARD_DAY_FX_CODE, NodeMatchers.isVisible());
    }
}
