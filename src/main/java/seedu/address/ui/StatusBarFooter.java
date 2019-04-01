package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.ReadOnlyTopDeck;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    public static final String TOTAL_CARDS_STATUS = "%d card(s) total";
    public static final String TOTAL_DECKS_STATUS = "%d deck(s) total";
    private static final String FXML = "StatusBarFooter.fxml";
    /**
     * Used to generate time stamps.
     * <p>
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();
    @FXML
    private Label syncStatus;
    @FXML
    private Label totalCardsStatus;
    @FXML
    private Label totalDecksStatus;
    @FXML
    private Label saveLocationStatus;


    public StatusBarFooter(Path saveLocation, ReadOnlyTopDeck topDeck) {
        super(FXML);
        topDeck.addListener(observable -> updateSyncStatus());
        topDeck.addListener(observable -> updateTotalDecksStatus((ReadOnlyTopDeck) observable));
        syncStatus.setText(SYNC_STATUS_INITIAL);
        updateTotalDecksStatus(topDeck);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Updates "last updated" status to the current time.
     */
    private void updateSyncStatus() {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        syncStatus.setText(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }

    /**
     * Updates card count in the status bar to the actual count.
     */
    private void updateTotalDecksStatus(ReadOnlyTopDeck topDeck) {
        int decksCount = topDeck.getDeckList().size();
        totalDecksStatus.setText(String.format(TOTAL_DECKS_STATUS, decksCount));
    }

}
