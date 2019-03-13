package seedu.address.model.deck.exceptions;

/**
 * Signals that the operation will result in duplicate Cards. (Cards are identified by the question)
 */
public class DuplicateCardException extends RuntimeException {
    public DuplicateCardException() {
        super("Operation would result in duplicate persons");
    }
}
