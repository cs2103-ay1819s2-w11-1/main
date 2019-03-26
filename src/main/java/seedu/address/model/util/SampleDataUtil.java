package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.TopDeck;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Card[] getSampleCards() {
        return new Card[]{new Card("What layer is HTTP at?", "Application", getTagSet("CS2105")), new Card(
                "What transport does HTTP use?", "TCP", getTagSet("CS2105")), new Card(
                "How many sides does a triangle have?", "3", getTagSet("Geometry")), new Card(
                "How many seconds are there in 1 millisecond?", "1 * 10^-3", new HashSet<Tag>())};
    }

    public static Deck[] getSampleDecks() {
        Deck sampleDeck = new Deck(new Name("Sample Deck"));
        for (Card sampleCard : getSampleCards()) {
            sampleDeck.addCard(sampleCard);
        }
        return new Deck[]{sampleDeck};
    }

    public static ReadOnlyTopDeck getSampleTopDeck() {
        TopDeck sampleTd = new TopDeck();
        for (Deck sampleDeck : getSampleDecks()) {
            sampleTd.addDeck(sampleDeck);
        }
        return sampleTd;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}
