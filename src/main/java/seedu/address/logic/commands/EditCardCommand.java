package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing card in the deck.
 */
public class EditCardCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the card identified "
            + "by the index number used in the displayed card list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) " + "[" + PREFIX_QUESTION + "QUESTION] " + "["
            + PREFIX_ANSWER + "ANSWER] " + "[" + PREFIX_TAG + "TAG]...\n" + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUESTION + "What is the sum from 1 to 10? " + PREFIX_ANSWER + "55";

    public static final String MESSAGE_EDIT_CARD_SUCCESS = "Edited Card: %1$s";
    public static final String MESSAGE_EDIT_CARD_AUTOCOMPLETE = "";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the deck.";

    private final Index index;
    private final Optional<EditCardDescriptor> editCardDescriptor;
    private final CardsView cardsView;

    /**
     * @param index              of the card in the filtered card list to edit
     * @param editCardDescriptor details to edit the card with
     */
    public EditCardCommand(CardsView cardsView, Index index, EditCardDescriptor editCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editCardDescriptor);

        this.cardsView = cardsView;
        this.index = index;
        this.editCardDescriptor = Optional.of(new EditCardDescriptor(editCardDescriptor));
    }

    public EditCardCommand(CardsView cardsView, Index index) {
        requireNonNull(index);

        this.cardsView = cardsView;
        this.index = index;
        this.editCardDescriptor = Optional.empty();
    }

    /**
     * Creates and returns a {@code Card} with the details of {@code cardToEdit}
     * edited with {@code editCardDescriptor}.
     */
    private static Card createEditedCard(Card cardToEdit, EditCardDescriptor editCardDescriptor) {
        assert cardToEdit != null;

        String updatedQuestion = editCardDescriptor.getQuestion().orElse(cardToEdit.getQuestion());
        String updatedAnswer = editCardDescriptor.getAnswer().orElse(cardToEdit.getAnswer());
        Set<Tag> updatedTags = editCardDescriptor.getTags().orElse(cardToEdit.getTags());

        return new Card(updatedQuestion, updatedAnswer, updatedTags);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Card> lastShownList = cardsView.getFilteredList();
        Deck activeDeck = cardsView.getActiveDeck();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        if (editCardDescriptor.isPresent()) {
            Card cardToEdit = lastShownList.get(index.getZeroBased());
            Card editedCard = createEditedCard(cardToEdit, editCardDescriptor.get());

            if (!cardToEdit.isSameCard(editedCard) && model.hasCard(editedCard, activeDeck)) {
                throw new CommandException(MESSAGE_DUPLICATE_CARD);
            }

            model.setCard(cardToEdit, editedCard, activeDeck);
            model.commitTopDeck();
            return new UpdatePanelCommandResult(String.format(MESSAGE_EDIT_CARD_SUCCESS, editedCard));
        } else {
            Card cardToEdit = lastShownList.get(index.getZeroBased());
            String question = cardToEdit.getQuestion();
            String answer = cardToEdit.getAnswer();
            Set<Tag> tags = cardToEdit.getTags();

            final StringBuilder builder = new StringBuilder();
            for (Tag tag : tags) {
                builder.append(" ").append(PREFIX_TAG).append(tag.tagName);
            }
            String updatedText = String
                    .format("%s %d %s%s %s%s %s", COMMAND_WORD, index.getOneBased(), PREFIX_QUESTION,
                            question, PREFIX_ANSWER, answer, builder.toString());
            return new PrefillCommandBoxCommandResult(MESSAGE_EDIT_CARD_AUTOCOMPLETE, updatedText);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCardCommand)) {
            return false;
        }

        // state check
        EditCardCommand e = (EditCardCommand) other;
        return index.equals(e.index) && editCardDescriptor.equals(e.editCardDescriptor);
    }

    /**
     * Stores the details to edit the card with. Each non-empty field value will replace the
     * corresponding field value of the card.
     */
    public static class EditCardDescriptor {
        private String question;
        private String answer;
        private Set<Tag> tags;

        public EditCardDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCardDescriptor(EditCardDescriptor toCopy) {
            setQuestion(toCopy.question);
            setAnswer(toCopy.answer);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, answer, tags);
        }

        public Optional<String> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Optional<String> getAnswer() {
            return Optional.ofNullable(answer);
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCardDescriptor)) {
                return false;
            }

            // state check
            EditCardDescriptor e = (EditCardDescriptor) other;

            return getQuestion().equals(e.getQuestion()) && getAnswer().equals(e.getAnswer()) && getTags()
                    .equals(e.getTags());
        }
    }
}
