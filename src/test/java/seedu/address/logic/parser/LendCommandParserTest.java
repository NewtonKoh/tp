package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEY_OWED_FOR_LEND_COMMAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LendCommand;
import seedu.address.model.person.MoneyOwed;

public class LendCommandParserTest {

    private LendCommandParser parser = new LendCommandParser();

    @Test
    void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 $/22.40",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0 $/22.40" + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_USAGE));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingMoneyOwed_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_MISSING_AMOUNT));
    }

    @Test
    public void parse_validArgs_returnsLendCommand() {
        // no leading and trailing whitespaces
        LendCommand expectedLendCommand =
                new LendCommand(INDEX_FIRST_PERSON, new MoneyOwed(VALID_MONEY_OWED_FOR_LEND_COMMAND));
        assertParseSuccess(parser, "1 $/12.80", expectedLendCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 1 \t $/12.80  \t", expectedLendCommand);
    }

}
