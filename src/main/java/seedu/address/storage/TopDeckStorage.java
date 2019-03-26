package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTopDeck;

/**
 * Represents a storage for {@link seedu.address.model.TopDeck}.
 */
public interface TopDeckStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTopDeckFilePath();

    /**
     * Returns TopDeck data as a {@link ReadOnlyTopDeck}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTopDeck> readTopDeck() throws DataConversionException, IOException;

    /**
     * @see #getTopDeckFilePath()
     */
    Optional<ReadOnlyTopDeck> readTopDeck(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTopDeck} to the storage.
     *
     * @param topDeck cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTopDeck(ReadOnlyTopDeck topDeck) throws IOException;

    /**
     * @see #saveTopDeck(ReadOnlyTopDeck)
     */
    void saveTopDeck(ReadOnlyTopDeck topDeck, Path filePath) throws IOException;

}
