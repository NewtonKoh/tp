package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Filter;
import seedu.address.logic.commands.FilterDayCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.model.person.predicates.PersonAvailableOnDayPredicate;
import seedu.address.model.person.predicates.PersonHasTagPredicate;
import seedu.address.testutil.TestUtil;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Filter.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterTagCommand() throws Exception {
        // no leading and trailing whitespaces
        FilterTagCommand expectedFilterTagCommand =
                new FilterTagCommand(new PersonHasTagPredicate(TestUtil
                        .stringsToTags(Arrays.asList("friends", "TAs"))));
        assertParseSuccess(parser, "tag friends TAs", expectedFilterTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "tag \n friends \n \t TAs  \t", expectedFilterTagCommand);
    }

    @Test
    public void parse_caseInsensitiveArgs_returnsFilterTagCommand() throws Exception {
        // no leading and trailing whitespaces
        FilterTagCommand expectedFilterTagCommand =
                new FilterTagCommand(new PersonHasTagPredicate(TestUtil
                        .stringsToTags(Arrays.asList("friends", "TAs"))));
        assertParseSuccess(parser, "tag friends TAs", expectedFilterTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "tag \n frieNDS \n \t TAs  \t", expectedFilterTagCommand);
    }

    @Test
    public void parse_validArgs_returnsFilterDayCommand() throws Exception {
        // no leading and trailing whitespaces
        FilterDayCommand expectedFilterDayCommand =
                new FilterDayCommand(new PersonAvailableOnDayPredicate(TestUtil
                        .stringsToDays(Arrays.asList("monday", "tuesday"))));
        assertParseSuccess(parser, "day monday tuesday", expectedFilterDayCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "day \n monday \n \t tuesday  \t", expectedFilterDayCommand);
    }

    @Test
    public void parse_caseInsensitiveArgs_returnsFilterDayCommand() throws Exception {
        // no leading and trailing whitespaces
        FilterDayCommand expectedFilterDayCommand =
                new FilterDayCommand(new PersonAvailableOnDayPredicate(TestUtil
                        .stringsToDays(Arrays.asList("monday", "tuesday"))));
        assertParseSuccess(parser, "day moNday tUesday", expectedFilterDayCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "day \n mondAY \n \t tuEsday  \t", expectedFilterDayCommand);
    }

    @Test
    public void parse_emptyType_throwParseException() throws Exception {
        // no leading and trailing whitespaces
        assertParseFailure(parser, "monday tuesday", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Filter.MESSAGE_USAGE));
    }

    @Test
    public void parse_specifiedTypeTagNoArguments_throwParseException() throws Exception {
        // no leading and trailing whitespaces
        assertParseFailure(parser, "tag", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_specifiedTypeDayNoArguments_throwParseException() throws Exception {
        // no leading and trailing whitespaces
        assertParseFailure(parser, "day", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterDayCommand.MESSAGE_USAGE));
    }

}
