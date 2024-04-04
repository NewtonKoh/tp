package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY_OWED;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MoneyOwed;

/**
 * Parses input arguments and creates a new LendCommand object
 */
public class LendCommandParser implements Parser<LendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LendCommand
     * and returns a LendCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LendCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MONEY_OWED);
        Index targetIndex;
        MoneyOwed lentAmount;
        try {
            targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!argMultimap.getValue(PREFIX_MONEY_OWED).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_MISSING_AMOUNT));
        }

        lentAmount = ParserUtil.parseMoneyOwed(argMultimap.getValue(PREFIX_MONEY_OWED).get());

        return new LendCommand(targetIndex, lentAmount);
    }

}
