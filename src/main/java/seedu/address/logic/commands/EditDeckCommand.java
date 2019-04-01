package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_DECK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.DecksView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;
import seedu.address.model.deck.UniqueCardList;

/**
 * Edits the details of an existing deck in TopDeck.
 */
public class EditDeckCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deck identified "
            + "by the index number used in the displayed deck list. " + "Changes its name to NAME.\n"
            + "Parameters: INDEX (must be a positive integer) " + "[" + PREFIX_NAME + "NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_NAME + "[NAME]";
    public static final String DEFAULT_INDEX = "1";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + DEFAULT_INDEX;

    public static final String MESSAGE_EDIT_DECK_SUCCESS = "Edited Deck: %1$s";


    private final Index index;
    private final EditDeckDescriptor editDeckDescriptor;
    private final DecksView decksView;

    /**
     * @param index              of the deck in the filtered deck list to edit
     * @param editDeckDescriptor details to edit the deck with
     */
    public EditDeckCommand(DecksView decksView, Index index, EditDeckDescriptor editDeckDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeckDescriptor);

        this.decksView = decksView;
        this.index = index;
        this.editDeckDescriptor = new EditDeckDescriptor(editDeckDescriptor);
    }

    /**
     * Creates and returns a {@code Deck} with the details of {@code deckToEdit}
     * edited with {@code editDeckDescriptor}.
     */
    private static Deck createEditedDeck(Deck deckToEdit, EditDeckDescriptor editDeckDescriptor) {
        assert deckToEdit != null;

        Name updatedName = editDeckDescriptor.getName().orElse(deckToEdit.getName());

        return new Deck(updatedName, deckToEdit.getCards().internalList);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Deck> currentDeckList = decksView.getFilteredList();

        if (index.getZeroBased() >= currentDeckList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Deck deckToEdit = currentDeckList.get(index.getZeroBased());
        Deck editedDeck = createEditedDeck(deckToEdit, editDeckDescriptor);

        if (!deckToEdit.isSameDeck(editedDeck) && model.hasDeck(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }

        model.updateDeck(deckToEdit, editedDeck);
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        model.commitTopDeck();
        return new CommandResult(String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDeckCommand)) {
            return false;
        }

        // state check
        EditDeckCommand e = (EditDeckCommand) other;
        return index.equals(e.index) && editDeckDescriptor.equals(e.editDeckDescriptor);
    }

    /**
     * Stores the details to edit the deck with. Each non-empty field value will replace the
     * corresponding field value of the deck.
     */
    public static class EditDeckDescriptor {
        private Name name;
        // private List<Card> cards;
        private UniqueCardList cards;

        public EditDeckDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeckDescriptor(EditDeckDescriptor toCopy) {
            setName(toCopy.name);
            setCards(toCopy.cards);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, cards);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        /**
         * Returns an unmodifiable card set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code cards} is null.
         */
        public Optional<UniqueCardList> getCards() {
            // return (cards != null) ? Optional.of(Collections.unmodifiableList(cards)) : Optional.empty();
            return (cards != null) ? Optional.of(cards) : Optional.empty();
        }

        /**
         * Sets {@code cards} to this object's {@code tags}.
         * A defensive copy of {@code cards} is used internally.
         */
        public void setCards(UniqueCardList cards) {
            // this.cards = (cards != null) ? new ArrayList<>(cards) : null;
            this.cards = cards;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDeckDescriptor)) {
                return false;
            }

            // state check
            EditDeckDescriptor e = (EditDeckDescriptor) other;

            return getName().equals(e.getName()) && getCards().equals(e.getCards());
        }
    }
}

