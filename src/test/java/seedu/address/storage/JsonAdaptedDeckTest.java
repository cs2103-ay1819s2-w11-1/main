package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedDeck.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalDecks.DECK_A;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deck.Name;
import seedu.address.testutil.Assert;

public class JsonAdaptedDeckTest {
    private static final String INVALID_NAME = " ";

    private static final String VALID_NAME = DECK_A.getName().toString();
    private static final List<JsonAdaptedCard> VALID_CARDS = DECK_A.getCards().internalList.stream()
                                                                                           .map(JsonAdaptedCard::new)
                                                                                           .collect(Collectors
                                                                                                            .toList());

    @Test
    public void toModelType_validDeckDetails_returnsDeck() throws Exception {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(DECK_A);
        assertEquals(DECK_A, deck.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(INVALID_NAME, VALID_CARDS);
        String expectedMessage = Name.MESSAGE_NAME;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(null, VALID_CARDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }
}
