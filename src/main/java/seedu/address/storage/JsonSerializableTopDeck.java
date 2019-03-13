package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;

/**
 * An Immutable TopDeck that is serializable to JSON format.
 */
@JsonRootName(value = "topdeck")
class JsonSerializableTopDeck {

    public static final String MESSAGE_DUPLICATE_CARD = "Cards list contains duplicate card(s).";

    private final List<JsonAdaptedCard> cards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTopDeck} with the given cards.
     */
    @JsonCreator
    public JsonSerializableTopDeck(@JsonProperty("cards") List<JsonAdaptedCard> cards) {
        this.cards.addAll(cards);
    }

    /**
     * Converts a given {@code ReadOnlyTopDeck} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTopDeck}.
     */
    public JsonSerializableTopDeck(ReadOnlyTopDeck source) {
        cards.addAll(source.getCardList().stream().map(JsonAdaptedCard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this serializable deck into the model's {@code TopDeck} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TopDeck toModelType() throws IllegalValueException {
        TopDeck topDeck = new TopDeck();
        for (JsonAdaptedCard jsonAdaptedCard : cards) {
            Card card = jsonAdaptedCard.toModelType();
            if (topDeck.hasCard(card)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CARD);
            }
            topDeck.addCard(card);
        }
        return topDeck;
    }

}
