package seedu.address.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AVAILABLE_MONDAY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.UiTestUtil.MINI_PERSON_CARD_NAME_FX_CODE;
import static seedu.address.ui.UiTestUtil.MINI_PERSON_CARD_TAG_FX_CODE;

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
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class MiniPersonCardTest extends ApplicationTest {

    private MiniPersonCard miniPersonCard;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Person testPerson;

    @Override
    public void start(Stage stage) {
        // Create a person with tags for testing
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        testPerson = new PersonBuilder(firstPerson)
                .withDaysAvailable(VALID_DAY_AVAILABLE_MONDAY)
                .build();
        miniPersonCard = new MiniPersonCard(testPerson);

        UiTestUtil.setUpSystem();

        // Set up the scene
        Scene scene = new Scene(miniPersonCard.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void execute_displayNameCorrectly_success() {
        // Verify that the name label correctly displays the person's name
        verifyThat(MINI_PERSON_CARD_NAME_FX_CODE, (Label label) -> {
            return label.getText().equals(testPerson.getName().toString());
        });
    }

    @Test
    public void execute_tagCountMatchesPersonTags_success() {

        verifyThat(MINI_PERSON_CARD_TAG_FX_CODE, (FlowPane flowPane) -> {
            return flowPane.getChildren().size() == testPerson.getTags().size();
        });
    }

    @Test
    public void execute_tagNamesDisplayCorrectly_success() {

        verifyThat(MINI_PERSON_CARD_TAG_FX_CODE, (FlowPane flowPane) -> {
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
    public void execute_componentVisibility_success() {
        verifyThat(MINI_PERSON_CARD_NAME_FX_CODE, NodeMatchers.isVisible());
        verifyThat(MINI_PERSON_CARD_TAG_FX_CODE, NodeMatchers.isVisible());
    }
}

