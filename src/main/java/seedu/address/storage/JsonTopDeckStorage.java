package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTopDeck;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonTopDeckStorage implements TopDeckStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTopDeckStorage.class);

    private Path filePath;

    public JsonTopDeckStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTopDeckFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTopDeck> readTopDeck() throws DataConversionException {
        return readTopDeck(filePath);
    }

    /**
     * Similar to {@link #readTopDeck()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTopDeck> readTopDeck(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTopDeck> jsonTopDeck = JsonUtil
                .readJsonFile(filePath, JsonSerializableTopDeck.class);
        if (!jsonTopDeck.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTopDeck.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTopDeck(ReadOnlyTopDeck topDeck) throws IOException {
        saveTopDeck(topDeck, filePath);
    }

    /**
     * Similar to {@link #saveTopDeck(ReadOnlyTopDeck)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTopDeck(ReadOnlyTopDeck topDeck, Path filePath) throws IOException {
        requireNonNull(topDeck);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTopDeck(topDeck), filePath);
    }

}
