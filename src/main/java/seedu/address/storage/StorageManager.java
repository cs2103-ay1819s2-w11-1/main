package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TopDeckStorage topDeckStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TopDeckStorage topDeckStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.topDeckStorage = topDeckStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getTopDeckFilePath() {
        return topDeckStorage.getTopDeckFilePath();
    }

    @Override
    public Optional<ReadOnlyTopDeck> readTopDeck() throws DataConversionException, IOException {
        return readTopDeck(topDeckStorage.getTopDeckFilePath());
    }

    @Override
    public Optional<ReadOnlyTopDeck> readTopDeck(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return topDeckStorage.readTopDeck(filePath);
    }

    @Override
    public void saveTopDeck(ReadOnlyTopDeck topDeck) throws IOException {
        saveTopDeck(topDeck, topDeckStorage.getTopDeckFilePath());
    }

    @Override
    public void saveTopDeck(ReadOnlyTopDeck topDeck, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        topDeckStorage.saveTopDeck(topDeck, filePath);
    }

}
