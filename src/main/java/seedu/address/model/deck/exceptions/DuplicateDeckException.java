package seedu.address.model.deck.exceptions;

/**
 * Signals that the operation will result in duplicate Decks (Decks are considered duplicates if they have
 * the same
 * * identity).
 */
public class DuplicateDeckException extends RuntimeException {
    public DuplicateDeckException() {
        super("Operation would result in duplicate decks");
    }
}
