package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.deck.Deck;

/**
 * Provides a handle for {@code ListPanel} containing the list of {@code DeckDisplay}.
 */
public class ListPanelHandle extends NodeHandle<ListView<Deck>> {
    public static final String DECK_LIST_VIEW_ID = "#listView";

    private static final String DECK_PANE_ID = "#deckPane";

    private Optional<Deck> lastRememberedSelectedDeckDisplay;

    public ListPanelHandle(ListView<Deck> listPanelNode) {
        super(listPanelNode);
    }

    /**
     * Returns a handle to the selected {@code DeckDisplayHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no deck is selected, or more than 1 deck is selected.
     * @throws IllegalStateException if the selected deck is currently not in the scene graph.
     */
    public DeckDisplayHandle getHandleToSelectedDeck() {
        List<Deck> selectedDeckList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedDeckList.size() != 1) {
            throw new AssertionError("Deck list size expected 1.");
        }

        return getAllDeckNodes().stream().map(DeckDisplayHandle::new)
                                .filter(handle -> handle.equals(selectedDeckList.get(0))).findFirst()
                                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected deck.
     */
    public int getSelectedDeckIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a deck is currently selected.
     */
    public boolean isAnyDeckSelected() {
        List<Deck> selectedDecksList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedDecksList.size() > 1) {
            throw new AssertionError("Deck list size expected 0 or 1.");
        }

        return !selectedDecksList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code deck}.
     */
    public void navigateToDeck(Deck deck) {
        if (!getRootNode().getItems().contains(deck)) {
            throw new IllegalArgumentException("Deck does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(deck);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToDeck(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code DeckDisplay} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the display deck handle of a deck associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected deck is currently not in the scene graph.
     */
    public DeckDisplayHandle getDeckDisplayHandle(int index) {
        return getAllDeckNodes().stream().map(DeckDisplayHandle::new)
                                .filter(handle -> handle.equals(getDeck(index))).findFirst()
                                .orElseThrow(IllegalStateException::new);
    }

    private Deck getDeck(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all deck nodes in the scene graph.
     * Deck nodes that are visible in the listview are definitely in the scene graph, while some nodes that
     * are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllDeckNodes() {
        return guiRobot.lookup(DECK_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code DeckDisplay} in the list.
     */
    public void rememberSelectedDeckDisplay() {
        List<Deck> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedDeckDisplay = Optional.empty();
        } else {
            lastRememberedSelectedDeckDisplay = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code DeckDisplay} is different from the value remembered by the most
     * recent
     * {@code rememberSelectedDeckDisplay()} call.
     */
    public boolean isSelectedDeckDisplayChanged() {
        List<Deck> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedDeckDisplay.isPresent();
        } else {
            return !lastRememberedSelectedDeckDisplay.isPresent()
                || !lastRememberedSelectedDeckDisplay.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
