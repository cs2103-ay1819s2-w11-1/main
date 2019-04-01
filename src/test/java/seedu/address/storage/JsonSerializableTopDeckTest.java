package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.exceptions.DuplicateCardException;
import seedu.address.testutil.TypicalDecks;

public class JsonSerializableTopDeckTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableTopDeckTest");
    private static final Path TYPICAL_DECKS_FILE = TEST_DATA_FOLDER.resolve("typicalDeckTopDeck.json");
    private static final Path INVALID_DECK_FILE = TEST_DATA_FOLDER.resolve("invalidDeckTopDeck.json");
    private static final Path DUPLICATE_DECK_FILE = TEST_DATA_FOLDER.resolve("duplicateDeckTopDeck.json");
    private static final Path DUPLICATE_CARD_FILE = TEST_DATA_FOLDER.resolve("duplicateCardTopDeck.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalDecksFile_success() throws Exception {
        JsonSerializableTopDeck dataFromFile = JsonUtil
                .readJsonFile(TYPICAL_DECKS_FILE, JsonSerializableTopDeck.class).get();
        TopDeck topDeckFromFile = dataFromFile.toModelType();
        TopDeck typicalDeckTopDeck = TypicalDecks.getTypicalTopDeck();
        assertEquals(topDeckFromFile, typicalDeckTopDeck);
    }

    @Test
    public void toModelType_invalidDeckFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTopDeck dataFromFile = JsonUtil
                .readJsonFile(INVALID_DECK_FILE, JsonSerializableTopDeck.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateDecksFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTopDeck dataFromFile = JsonUtil
                .readJsonFile(DUPLICATE_DECK_FILE, JsonSerializableTopDeck.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableTopDeck.MESSAGE_DUPLICATE_DECK);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateCardsFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTopDeck dataFromFile = JsonUtil
                .readJsonFile(DUPLICATE_CARD_FILE, JsonSerializableTopDeck.class).get();
        thrown.expect(DuplicateCardException.class);
        thrown.expectMessage(JsonSerializableTopDeck.MESSAGE_DUPLICATE_CARD);
        dataFromFile.toModelType();
    }

}
