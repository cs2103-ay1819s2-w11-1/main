package seedu.address.model.deck.exceptions;

/**
 * Signals that the operation will result in duplicate Cards (Cards are considered duplicates if they have
 * the same
 * identity).
 */
public class DeckImportException extends RuntimeException {
    public DeckImportException(String msg) {
        super(msg);
    }
}
