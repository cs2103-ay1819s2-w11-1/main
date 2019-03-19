package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.ListItem;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Panel containing the list of cards.
 */
public class ContentPanel extends UiPart<Region> {
    private static final String FXML = "ContentPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ContentPanel.class);

    @FXML
    private ListView<ListItem> listView;

    public ContentPanel(ObservableList<ListItem> list, ObservableValue<ListItem> selectedItem, Consumer<ListItem> onSelectedItemChange) {
        super(FXML);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in card list panel changed to : '" + newValue + "'");
            onSelectedItemChange.accept(newValue);
        });

        listView.setItems(list);
        listView.setCellFactory(cardListView -> new CardListViewCell());

        selectedItem.addListener((observable, oldValue, newValue) -> {
            logger.info("Selected item changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected card,
            // otherwise we would have an infinite loop.
            if (Objects.equals(listView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                listView.getSelectionModel().clearSelection();
            } else {
                int index = listView.getItems().indexOf(newValue);
                listView.scrollTo(index);
                listView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ListItem}.
     */
    class CardListViewCell extends ListCell<ListItem> {
        @Override
        protected void updateItem(ListItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (item instanceof Card) {
                    setGraphic(new CardDisplay((Card)item, getIndex() + 1).getRoot());
                } else if (item instanceof Deck) {
                    setGraphic(new DeckDisplay((Deck)item, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
