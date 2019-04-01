package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalCards {
    public static final Card ADDITION = new CardBuilder().withQuestion("Solve 1 + 1.").withAnswer("2")
                                                         .withTags("Math").build();

    public static final Card SUBTRACTION = new CardBuilder().withQuestion("What is 10 - 10?").withAnswer("0")
                                                            .withTags("Math").build();

    public static final Card MULTIPLICATION = new CardBuilder().withQuestion("What is 8 * 8?")
                                                               .withAnswer("64").withTags("Math").build();

    public static final Card DIVISION = new CardBuilder().withQuestion("Evaluate 1024 / 2.").withAnswer("512")
                                                         .withTags("Math").build();

    public static final Card HELLO_WORLD = new CardBuilder().withQuestion("Hello?").withAnswer("World")
                                                            .withTags("Simple", "CS").build();

    public static final Card NO_TAG = new CardBuilder().withQuestion("Are there any tag?").withAnswer("No")
                                                       .build();

    public static final Card UNIQUE = new CardBuilder().withQuestion("Is this card unique?")
                                                       .withAnswer("Yes it is.").build();

    public static final Card LAYER = new CardBuilder().withQuestion("Which layer is HTTP at?")
                                                      .withAnswer("Application").withTags("CS2105").build();

    public static final Card TRANSPORT = new CardBuilder().withQuestion("What transport does HTTP use?")
                                                          .withAnswer("TCP").withTags("CS2105").build();

    public static final Card OK_STATUS = new CardBuilder()
            .withQuestion("What is the status code for OK in HTTP?").withAnswer("200").withTags("CS2105")
            .build();

    public static final String KEYWORD_MATCHING_HTTP = "HTTP";

    private TypicalCards() {
    } // prevents instantiation

    /**
     * Returns a {@code Deck} with all the typical cards.
     */
    public static Deck getTypicalDeck() {
        Deck ad = new Deck(new Name("Typical Deck"), getTypicalCards());
        return ad;
    }

    /**
     * Returns an {@code TopDeck} with all the typical cards.
     */
    public static TopDeck getTypicalTopDeck() {
        TopDeck td = new TopDeck();
        td.addDeck(getTypicalDeck());
        return td;
    }


    public static List<Card> getTypicalCards() {
        //The cards must start with different words
        return new ArrayList<>(Arrays.asList(ADDITION, DIVISION, NO_TAG, LAYER, OK_STATUS, HELLO_WORLD));
    }

}
