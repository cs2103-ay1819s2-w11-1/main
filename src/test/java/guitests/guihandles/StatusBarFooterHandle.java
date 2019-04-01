package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String TOTAL_DECKS_STATUS_ID = "#totalDecksStatus";
    private static final String TOTAL_CARDS_STATUS_ID = "#totalCardsStatus";
    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";

    private final Labeled syncStatusNode;
    private final Labeled totalCardsStatusNode;
    private final Labeled totalDecksStatusNode;
    private final Labeled saveLocationNode;

    private String lastRememberedSyncStatus;
    private String lastRememberedTotalCardsStatus;
    private String lastRememberedTotalDecksStatus;
    private String lastRememberedSaveLocation;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        syncStatusNode = getChildNode(SYNC_STATUS_ID);
        totalDecksStatusNode = getChildNode(TOTAL_DECKS_STATUS_ID);
        totalCardsStatusNode = getChildNode(TOTAL_CARDS_STATUS_ID);
        saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
    }

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'total cards' portion of the status bar.
     */
    public String getTotalCardsStatus() {
        return totalCardsStatusNode.getText();
    }

    /**
     * Returns the text of the 'total decks' portion of the status bar.
     */
    public String getTotalDecksStatus() {
        return totalDecksStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberSyncStatus() {
        lastRememberedSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the
     * most recent
     * {@code rememberSyncStatus()} call.
     */
    public boolean isSyncStatusChanged() {
        return !lastRememberedSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'total cards' portion of the status bar.
     */
    public void rememberTotalCardsStatus() {
        lastRememberedTotalCardsStatus = getTotalCardsStatus();
    }

    /**
     * Remembers the content of the 'total decks' portion of the status bar.
     */
    public void rememberTotalDecksStatus() {
        lastRememberedTotalDecksStatus = getTotalDecksStatus();
    }

    /**
     * Returns true if the current content of the 'total persons' is different from the value remembered by
     * the most
     * recent {@code rememberTotalPersonsStatus()} call.
     */
    public boolean isTotalPersonsStatusChanged() {
        return !lastRememberedTotalCardsStatus.equals(getTotalDecksStatus());
    }

    /**
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveLocation = getSaveLocation();
    }

    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by
     * the most
     * recent {@code rememberSaveLocation()} call.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveLocation.equals(getSaveLocation());
    }
}
