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
import seedu.address.model.deck.Card;

/**
 * Panel containing the list of persons.
 */
public class CardListPanel extends UiPart<Region> {
    private static final String FXML = "CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    @FXML
    private ListView<Card> cardListView;

    public CardListPanel(ObservableList<Card> cardList, ObservableValue<Card> selectedCard,
                         Consumer<Card> onSelectedCardChange) {
        super(FXML);
        cardListView.setItems(cardList);
        cardListView.setCellFactory(listView -> new PersonListViewCell());
        cardListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in card list panel changed to : '" + newValue + "'");
            onSelectedCardChange.accept(newValue);
        });
        selectedCard.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected card changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected card,
            // otherwise we would have an infinite loop.
            if (Objects.equals(cardListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                cardListView.getSelectionModel().clearSelection();
            } else {
                int index = cardListView.getItems().indexOf(newValue);
                cardListView.scrollTo(index);
                cardListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code CardDisplay}.
     */
    class PersonListViewCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CardDisplay(card, getIndex() + 1).getRoot());
            }
        }
    }

}
