package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONEY_OWED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEY_OWED_FOR_LEND_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEY_OWED_FOR_LEND_COMMAND_IN_FLOAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEY_OWED_FOR_SPLIT_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MoneyOwed;
import seedu.address.model.person.Person;


class LendCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_withInvalidAmount_throwsCommandException() {
        Index indexOfFiona = Index.fromZeroBased(model.findIndex(FIONA));

        // This amount will cause Fiona to exceed maximum amount.
        MoneyOwed moneyOwed = new MoneyOwed(
                String.valueOf(INVALID_MONEY_OWED - FIONA.getMoneyOwed().getAmount()));

        LendCommand lendCommand = new LendCommand(indexOfFiona, moneyOwed);
        String expectedMessage = MoneyOwed.MESSAGE_CONSTRAINTS;
        assertCommandFailure(lendCommand, model, expectedMessage);
    }

    @Test
    public void execute_withInvalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MoneyOwed lentAmount = new MoneyOwed(VALID_MONEY_OWED_FOR_LEND_COMMAND);
        LendCommand lendCommand = new LendCommand(invalidIndex, lentAmount);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(lendCommand, model, expectedMessage);
    }

    @Test
    public void execute_withSuccess() {
        Index targetIndex = INDEX_FIRST_PERSON;
        MoneyOwed lentAmount = new MoneyOwed(VALID_MONEY_OWED_FOR_LEND_COMMAND);
        LendCommand lendCommand = new LendCommand(targetIndex, lentAmount);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new Person(
                personInFilteredList.getName(), personInFilteredList.getPhone(), personInFilteredList.getEmail(),
                personInFilteredList.getAddress(), personInFilteredList.getRemark(), personInFilteredList.getTags(),
                personInFilteredList.getBirthday(),
                personInFilteredList.getMoneyOwed().addAmountOwed(VALID_MONEY_OWED_FOR_LEND_COMMAND_IN_FLOAT),
                personInFilteredList.getDaysAvailable());
        String expectedMessage = String.format(
                LendCommand.MESSAGE_LENT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        expectedModel.setPerson(personInFilteredList, editedPerson);

        assertCommandSuccess(lendCommand,
                model,
                new CommandResult(expectedMessage).withPersonToShow(expectedModel.findIndex(editedPerson)),
                expectedModel);
    }

    @Test
    public void equals() {
        Index targetIndex = INDEX_FIRST_PERSON;
        MoneyOwed lentAmount = new MoneyOwed(VALID_MONEY_OWED_FOR_LEND_COMMAND);

        final LendCommand standardCommand = new LendCommand(targetIndex, lentAmount);
        MoneyOwed differentLentAmount = new MoneyOwed(VALID_MONEY_OWED_FOR_SPLIT_COMMAND);

        // same values -> returns true
        LendCommand commandWithSameValues = new LendCommand(targetIndex, lentAmount);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types of command -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new LendCommand(INDEX_SECOND_PERSON, lentAmount)));

        // different MoneyOwed -> returns false
        assertFalse(standardCommand.equals(new LendCommand(targetIndex, differentLentAmount)));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = INDEX_FIRST_PERSON;
        MoneyOwed lentAmount = new MoneyOwed(VALID_MONEY_OWED_FOR_LEND_COMMAND);
        LendCommand lendCommand = new LendCommand(targetIndex, lentAmount);
        String expected = LendCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", amountToLend="
                + lentAmount + "}";
        assertEquals(expected, lendCommand.toString());
    }
}
