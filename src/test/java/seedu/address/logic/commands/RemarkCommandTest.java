package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LONG_REMARK;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_STUB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(
                remarkCommand,
                model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)),
                expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(editedPerson.getRemark().toString()));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(
                remarkCommand,
                model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)),
                expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(
                remarkCommand,
                model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)),
                expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Test Adding a Long Remark to a Person in the Unfiltered List
     */
    @Test
    public void execute_addLongRemarkUnfilteredList_success() {
        // Setup first person for testing
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Person editedPerson = new PersonBuilder(firstPerson).withRemark(LONG_REMARK).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(LONG_REMARK));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)), expectedModel);
    }

    /**
     * Test Adding an Empty Remark
     */
    @Test
    public void execute_addEmptyRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String emptyRemark = "";
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(emptyRemark).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(emptyRemark));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)), expectedModel);
    }

    /**
     * Test No Change in Remark
     */
    @Test
    public void execute_noChangeInRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingRemark = firstPerson.getRemark().value;
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(existingRemark).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(existingRemark));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)), expectedModel);
    }

    /**
     * Test Adding a Remark to a Newly Added Person
     */
    @Test
    public void execute_addRemarkToNewPerson_success() {
        Person newPerson = new PersonBuilder().build();
        model.addPerson(newPerson);
        Index newPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size()); // Last person, newly added

        Person editedPerson = new PersonBuilder(newPerson).withRemark(VALID_REMARK_BOB).build();

        RemarkCommand remarkCommand = new RemarkCommand(newPersonIndex, new Remark(VALID_REMARK_BOB));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(newPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)), expectedModel);
    }

    /**
     * Ensure remark command works on filtered list
     */
    @Test
    public void execute_addRemarkFilteredList_success() {
        // Filter the list to only show the second person
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        // Get the person from the filtered and unfiltered list to verify the correct person is in view
        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personInFullList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertTrue(personInFilteredList.equals(personInFullList));

        Person editedPerson = new PersonBuilder(personInFilteredList).withRemark(VALID_REMARK_BOB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personInFullList, editedPerson);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(
                remarkCommand,
                model,
                new CommandResult(expectedMessage).withPersonToShow(INDEX_SECOND_PERSON.getZeroBased()),
                expectedModel);
    }

    /**
     * Test Changing Remark on a Person Already with a Remark
     */
    @Test
    public void execute_changeRemarkUnfilteredList_success() {

        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withRemark(VALID_REMARK_BOB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_BOB));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(remarkCommand, model,
                new CommandResult(expectedMessage).withPersonToShow(model.findIndex(editedPerson)), expectedModel);
    }
}
