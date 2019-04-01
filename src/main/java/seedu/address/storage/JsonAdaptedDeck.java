package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;

/**
 * Jackson-friendly version of {@link Deck}.
 */
public class JsonAdaptedDeck {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deck's %s field is missing!";

    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private List<JsonAdaptedCard> cards = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDeck} with the given deck details.
     */
    @JsonCreator
    public JsonAdaptedDeck(@JsonProperty("name") String name,
                           @JsonProperty("cards") List<JsonAdaptedCard> cards) {
        this.name = name;
        this.cards = new ArrayList<>(cards);
    }

    /**
     * Converts a given {@code Deck} into this class for Jackson's use.
     */
    public JsonAdaptedDeck(Deck source) {
        name = source.getName().fullName;
        cards = source.getCards().internalList.stream().map(JsonAdaptedCard::new)
                                              .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted deck object into the model's {@code Deck} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deck.
     */
    public Deck toModelType() throws IllegalValueException {
        final List<Card> deckCards = new ArrayList<>();
        for (JsonAdaptedCard card : cards) {
            deckCards.add(card.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME);
        }
        final Name deckName = new Name(name);

        return new Deck(deckName, deckCards);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedDeck)) {
            return false;
        }

        JsonAdaptedDeck otherDeck = (JsonAdaptedDeck) other;
        return Objects.equals(name, otherDeck.name) && cards.equals(otherDeck.cards);
    }
}

