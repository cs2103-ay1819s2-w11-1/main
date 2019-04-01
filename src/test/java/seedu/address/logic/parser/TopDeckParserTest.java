package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CardsView;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.AddDeckCommand;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCardCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCardCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.QuestionContainsKeywordsPredicate;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.CardUtil;
import seedu.address.testutil.DeckBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;

public class TopDeckParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TopDeckParser parser = new TopDeckParser();
    private final Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());

    @Test
    public void parseCommand_addDeck() throws Exception {
        Deck deck = new DeckBuilder().withName(VALID_NAME_DECK_A).build();
        AddDeckCommand command = (AddDeckCommand) parser
                .parseCommand(AddDeckCommand.COMMAND_WORD + VALID_DECK_NAME_A_ARGS, model);
        assertEquals(new AddDeckCommand((DecksView) model.getViewState(), deck), command);
    }

    @Test
    public void parseCommand_addCard() throws Exception {
        model.changeDeck(getTypicalDeck());

        Card card = new CardBuilder().build();
        AddCardCommand command = (AddCardCommand) parser.parseCommand(CardUtil.getAddCommand(card), model);
        assertEquals(new AddCardCommand((CardsView) model.getViewState(), card), command);
    }

    //    @Test
    //    public void parseCommand_clear() throws Exception {
    //        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, model) instanceof ClearCommand);
    //        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", model) instanceof
    //        ClearCommand);
    //    }

    @Test
    public void parseCommand_deleteCard() throws Exception {
        model.changeDeck(getTypicalDeck());

        DeleteCardCommand command = (DeleteCardCommand) parser
                .parseCommand(DeleteCardCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased(), model);
        assertEquals(new DeleteCardCommand((CardsView) model.getViewState(), INDEX_FIRST_CARD), command);
    }

    @Test
    public void parseCommand_editCard() throws Exception {
        model.changeDeck(getTypicalDeck());

        Card card = new CardBuilder().build();
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(card).build();
        EditCardCommand command = (EditCardCommand) parser.parseCommand(
                EditCardCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased() + " " + CardUtil
                        .getEditCardDescriptorDetails(descriptor), model);
        assertEquals(new EditCardCommand((CardsView) model.getViewState(), INDEX_FIRST_CARD, descriptor),
                     command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, model) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", model) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findCard() throws Exception {
        model.changeDeck(getTypicalDeck());

        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCardCommand command = (FindCardCommand) parser.parseCommand(
                FindCardCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                model);
        assertEquals(new FindCardCommand((CardsView) model.getViewState(),
                                         new QuestionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, model) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", model) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, model) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3", model) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", model);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, model) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", model) instanceof ListCommand);
    }

    @Test
    public void parseCommand_selectCard() throws Exception {
        model.changeDeck(getTypicalDeck());

        SelectCardCommand command = (SelectCardCommand) parser
                .parseCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased(), model);
        assertEquals(new SelectCardCommand((CardsView) model.getViewState(), INDEX_FIRST_CARD), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, model) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1", model) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, model) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3", model) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("", model);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", model);
    }
}
