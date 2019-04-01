package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.StatusBarFooter.TOTAL_DECKS_STATUS;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.ListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.TopDeck;
import seedu.address.testutil.TypicalDecks;
import seedu.address.ui.CommandBox;

/**
 * A system test class for TopDeck, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class TopDeckSystemTest {

    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE = Arrays
            .asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected TopDeck getInitialData() {
        return TypicalDecks.getTypicalTopDeck();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public ListPanelHandle getCardListPanel() {
        return mainWindowHandle.getListPanel();
    }

    public ListPanelHandle getDeckListPanel() {
        return mainWindowHandle.getListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Displays all decks.
     */
    protected void showAllDecks() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getTopDeck().getDeckList().size(), getModel().getFilteredList().size());
    }

    /**
     * Displays all decks with any parts of their names matching {@code keyword} (case-insensitive).
     * TODO: uncomment when FindDeckCommand is implemented
     */
    /**
     protected void showDecksWithQuestion(String keyword) {
     executeCommand(FindDeckCommand.COMMAND_WORD + " " + keyword);
     assertTrue(getModel().getFilteredList().size() < getModel().getTopDeck().getDeckList().size());
     }
     **/

    /**
     * Selects the Deck at {@code index} of the displayed list.
     */
    protected void selectDeck(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getDeckListPanel().getSelectedDeckIndex());
    }

    /**
     * Deletes all Decks.
     */
    protected void deleteAllDecks() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getTopDeck().getDeckList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay}
     * displays
     * {@code expectedResultMessage}, the storage contains the same deck objects as {@code expectedModel}
     * and the deck list panel displays the decks in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput,
                                                     String expectedResultMessage, Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new TopDeck(expectedModel.getTopDeck()), testApp.readStorageTopDeck());
        assertListMatching(getDeckListPanel(), expectedModel.getFilteredList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code ListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberTotalCardsStatus();
        statusBarFooterHandle.rememberSyncStatus();
        getDeckListPanel().rememberSelectedDeckDisplay();
    }

    /**
     * Asserts that the previously selected deck is now deselected.
     */
    protected void assertSelectedDeckDeselected() {
        assertFalse(getDeckListPanel().isAnyDeckSelected());
    }

    /**
     * Asserts that only the deck at {@code expectedSelectedDeckIndex} is selected.
     *
     * @see ListPanelHandle#isSelectedDeckDisplayChanged()
     */
    protected void assertSelectedDeckChanged(Index expectedSelectedDeckIndex) {
        getDeckListPanel().navigateToDeck(getDeckListPanel().getSelectedDeckIndex());
        String selectedDeckName = getDeckListPanel().getHandleToSelectedDeck().getName();

        assertEquals(expectedSelectedDeckIndex.getZeroBased(), getDeckListPanel().getSelectedDeckIndex());
    }

    /**
     * Asserts that the selected deck in the deck list panel remains unchanged.
     *
     * @see ListPanelHandle#isSelectedDeckDisplayChanged()
     */
    protected void assertSelectedDeckUnchanged() {
        assertFalse(getDeckListPanel().isSelectedDeckDisplayChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isTotalPersonsStatusChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location and the total deck
     * list remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isTotalPersonsStatusChanged());
    }

    /**
     * Asserts that the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, and total persons was changed to match the total
     * number of persons in the address book, while the save location remains the same.
     */
    protected void assertStatusBarChangedExceptSaveLocation() {
        StatusBarFooterHandle handle = getStatusBarFooter();

        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());

        final int totalPersons = testApp.getModel().getTopDeck().getDeckList().size();
        assertEquals(String.format(TOTAL_DECKS_STATUS, totalPersons), handle.getTotalDecksStatus());

        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getDeckListPanel(), getModel().getFilteredList());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                     getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
        assertEquals(String.format(TOTAL_DECKS_STATUS, getModel().getTopDeck().getDeckList().size()),
                     getStatusBarFooter().getTotalDecksStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
