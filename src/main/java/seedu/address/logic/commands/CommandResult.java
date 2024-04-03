package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Used for showing QR code for the person to be paid.
     */
    private final Person personToPay;

    /**
     * Used for setting the UI to display the person at the current index. If an invalid
     * index is given, UI will display the HomeCard instead.
     */
    private final Integer personToShow;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(
            String feedbackToUser, boolean showHelp, boolean exit, Person personToPay, Integer personToShow) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.personToPay = personToPay;
        this.personToShow = personToShow;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code showHelp}
     * and {@code exit}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, null);
    }

    public CommandResult(String feedbackToUser, Person personToPay) {
        this(feedbackToUser, false, false, personToPay, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, null);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowPayment() {
        return personToPay != null;
    }

    public Person getPersonToPay() {
        return personToPay;
    }

    public Integer getPersonToShow() {
        return personToShow;
    }

    /**
     * Returns a new {@code CommandResult} with the personToShow set to the provided index.
     */
    public CommandResult withPersonToShow(int index) {
        return new CommandResult(
                feedbackToUser,
                showHelp,
                exit,
                personToPay,
                index
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && Objects.equals(personToPay, otherCommandResult.personToPay)
                && Objects.equals(personToShow, otherCommandResult.personToShow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, personToPay, personToShow);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("personToPay", personToPay)
                .add("personToShow", personToShow)
                .toString();
    }

}
