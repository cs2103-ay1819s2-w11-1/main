package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.MULTIPLICATION;
import static seedu.address.testutil.TypicalCards.UNIQUE;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.Deck;

public class JsonTopDeckStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTopDeckStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTopDeck_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTopDeck(null);
    }

    private java.util.Optional<ReadOnlyTopDeck> readTopDeck(String filePath) throws Exception {
        return new JsonTopDeckStorage(Paths.get(filePath)).readTopDeck(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTopDeck("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTopDeck("notJsonFormatTopDeck.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readTopDeck_invalidCardTopDeck_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTopDeck("invalidDeckTopDeck.json");
    }

    @Test
    public void readTopDeck_invalidAndValidCardTopDeck_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTopDeck("invalidAndValidDeckTopDeck.json");
    }

    @Test
    public void readAndSaveTopDeck_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTopDeck.json");
        TopDeck original = getTypicalTopDeck();
        Deck originalDeck = original.getDeckList().get(0);
        JsonTopDeckStorage jsontopDeckStorage = new JsonTopDeckStorage(filePath);

        // Save in new file and read back
        jsontopDeckStorage.saveTopDeck(original, filePath);
        ReadOnlyTopDeck readBack = jsontopDeckStorage.readTopDeck(filePath).get();
        assertEquals(original, new TopDeck(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addCard(MULTIPLICATION, originalDeck);
        original.deleteCard(ADDITION, originalDeck);
        jsontopDeckStorage.saveTopDeck(original, filePath);
        readBack = jsontopDeckStorage.readTopDeck(filePath).get();
        assertEquals(original, new TopDeck(readBack));

        // Save and read without specifying file path
        original.addCard(UNIQUE, originalDeck);
        jsontopDeckStorage.saveTopDeck(original); // file path not specified
        readBack = jsontopDeckStorage.readTopDeck().get(); // file path not specified
        assertEquals(original, new TopDeck(readBack));

    }

    @Test
    public void saveTopDeck_nullTopDeck_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTopDeck(null, "SomeFile.json");
    }

    /**
     * Saves {@code topDeck} at the specified {@code filePath}.
     */
    private void saveTopDeck(ReadOnlyTopDeck topDeck, String filePath) {
        try {
            new JsonTopDeckStorage(Paths.get(filePath))
                    .saveTopDeck(topDeck, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTopDeck_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTopDeck(new TopDeck(), null);
    }
}
