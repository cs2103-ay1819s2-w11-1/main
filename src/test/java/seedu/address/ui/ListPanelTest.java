package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalDecks.getTypicalDecks;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DECK;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDeckObject;
import static seedu.address.ui.testutil.GuiTestAssert.assertDeckDisplayEquals;

import org.junit.Test;

import guitests.guihandles.DeckDisplayHandle;
import guitests.guihandles.ListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.ListItem;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;

public class ListPanelTest extends GuiUnitTest {
    private static final ObservableList<Deck> TYPICAL_DECKS = FXCollections.observableList(getTypicalDecks());

    private static final long DECK_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<ListItem> selectedItem = new SimpleObjectProperty<>();

    private ListPanelHandle listPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_DECKS);

        for (int i = 0; i < TYPICAL_DECKS.size(); i++) {
            listPanelHandle.navigateToDeck(TYPICAL_DECKS.get(i));
            Deck expectedDeck = TYPICAL_DECKS.get(i);
            DeckDisplayHandle actualDeck = listPanelHandle.getDeckDisplayHandle(i);

            assertCardDisplaysDeckObject(expectedDeck, actualDeck);
            assertEquals((i + 1) + ". ", actualDeck.getId());
        }
    }

    @Test
    public void selection_modelSelectedDeckChanged_selectionChanges() {
        initUi(TYPICAL_DECKS);
        Deck secondDeck = TYPICAL_DECKS.get(INDEX_SECOND_DECK.getZeroBased());
        guiRobot.interact(() -> selectedItem.set(secondDeck));
        guiRobot.pauseForHuman();

        DeckDisplayHandle expectedDeck = listPanelHandle
                .getDeckDisplayHandle(INDEX_SECOND_DECK.getZeroBased());
        DeckDisplayHandle selectedDeck = listPanelHandle.getHandleToSelectedDeck();
        assertDeckDisplayEquals(expectedDeck, selectedDeck);
    }


    /**
     * Verifies that creating and deleting large number of cards in {@code ListPanel} requires lesser than
     * {@code DECK_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Deck> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(DECK_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of card cards exceeded time limit");
    }

    /**
     * Returns a list of decks containing {@code deckCount} decks that is used to populate the
     * {@code ListPanel}.
     */
    private ObservableList<Deck> createBackingList(int deckCount) {
        ObservableList<Deck> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < deckCount; i++) {
            String name = i + " deck";
            Deck deck = new Deck(new Name(name));
            backingList.add(deck);
        }
        return backingList;
    }

    /**
     * Initializes {@code listPanelHandle} with a {@code ListPanel} backed by {@code list}.
     * Also shows the {@code Stage} that displays only {@code ListPanel}.
     */
    private void initUi(ObservableList<? extends ListItem> list) {
        ListPanel listPanel = new ListPanel((ObservableList<ListItem>) list, selectedItem, selectedItem::set);
        uiPartRule.setUiPart(listPanel);

        listPanelHandle = new ListPanelHandle(
                getChildNode(listPanel.getRoot(), ListPanelHandle.DECK_LIST_VIEW_ID));
    }
}
