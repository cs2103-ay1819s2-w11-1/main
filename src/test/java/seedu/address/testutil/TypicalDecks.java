package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.getTypicalCards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TopDeck;
import seedu.address.model.deck.Deck;

/**
 * A utility class containing a list of {@code Deck} objects to be used in tests.
 */
public class TypicalDecks {

    public static final Deck DECK_A = new DeckBuilder().withName("Algebra").build();
    public static final Deck DECK_B = new DeckBuilder().withName("Bacon Salad Recipe").build();
    public static final Deck DECK_C = new DeckBuilder().withName("Calculus").build();
    public static final Deck DECK_D = new DeckBuilder().withName("Darwinian Studies")
                                                       .withCards(getTypicalCards()).build();
    public static final Deck DECK_E = new DeckBuilder().withName("Epistemology").withCards(getTypicalCards())
                                                       .build();


    public static final Deck DECK_WITH_CARDS = TypicalCards.getTypicalDeck();

    // Manually added
    public static final Deck DECK_F = new DeckBuilder().withName("Finance").build();
    public static final Deck DECK_G = new DeckBuilder().withName("Geometry").build();
    public static final Deck DECK_H = new DeckBuilder().withName("John GraphQL").build();
    public static final Deck DECK_I = new DeckBuilder().withName("John History").build();


    public static final Deck THERE = new DeckBuilder().withName("Baby there").withCards(getTypicalCards())
                                                      .build();
    public static final Deck NOTHING = new DeckBuilder().withName("is nothing").build();
    public static final Deck HOLDING = new DeckBuilder().withName("holding me back").build();
    public static final String KEYWORD_MATCHING_JOHN = "John";

    private TypicalDecks() {
    } // prevents instantiation

    /**
     * Returns an {@code TopDeck} with all the typical decks.
     */
    public static TopDeck getTypicalTopDeck() {
        TopDeck td = new TopDeck();
        for (Deck deck : getTypicalDecks()) {
            td.addDeck(deck);
        }
        return td;
    }

    public static List<Deck> getTypicalDecks() {
        return new ArrayList<>(
                Arrays.asList(DECK_WITH_CARDS, DECK_A, DECK_B, DECK_C, DECK_D, DECK_E, THERE, NOTHING,
                              HOLDING));
    }
}

