package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import guitests.guihandles.CardDisplayHandle;
import guitests.guihandles.DeckDisplayHandle;
import guitests.guihandles.ListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.logic.ListItem;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardDisplayEquals(CardDisplayHandle expectedCard, CardDisplayHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getQuestion(), actualCard.getQuestion());
        assertEquals(expectedCard.getAnswer(), actualCard.getAnswer());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualDeck} displays the same name as {@code expectedDeck}.
     */
    public static void assertDeckDisplayEquals(DeckDisplayHandle expectedDeck, DeckDisplayHandle actualDeck) {
        assertEquals(expectedDeck.getId(), actualDeck.getId());
        assertEquals(expectedDeck.getName(), actualDeck.getName());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCard}.
     */
    public static void assertCardDisplaysCardObject(Card expectedCard, CardDisplayHandle actualCard) {
        assertEquals(expectedCard.getQuestion(), actualCard.getQuestion());
        assertEquals(expectedCard.getAnswer(), actualCard.getAnswer());
        assertTagsEqual(expectedCard, actualCard);
    }

    /**
     * Asserts that {@code actualDeck} displays the details of {@code expectedDeck}.
     */
    public static void assertCardDisplaysDeckObject(Deck expectedDeck, DeckDisplayHandle actualDeck) {
        assertEquals(expectedDeck.getName().fullName, actualDeck.getName());
    }

    /**
     * Asserts that the list in {@code listPanelHandle} displays the details of {@code listItems} correctly.
     */
    public static void assertListMatching(ListPanelHandle listPanelHandle,
                                          List<? extends ListItem> listItems) {
        ListItem firstItem = listItems.get(0);
        if (firstItem instanceof Deck) {
            ArrayList<Deck> deckList = new ArrayList<>();
            for (ListItem item : listItems) {
                deckList.add((Deck) item);
            }
            assertDeckListMatching(listPanelHandle, deckList);
        }
    }

    /**
     * Asserts that the list in {@code listPanelHandle} displays the details of {@code decks} correctly and
     * in the correct order.
     */
    public static void assertDeckListMatching(ListPanelHandle listPanelHandle, List<Deck> decks) {
        assertDeckListMatching(listPanelHandle, decks.toArray(new Deck[0]));
    }

    /**
     * Asserts that the list in {@code listPanelHandle} displays the details of {@code decks} correctly and
     * in the correct order.
     */
    public static void assertDeckListMatching(ListPanelHandle listPanelHandle, Deck... decks) {
        for (int i = 0; i < decks.length; i++) {
            listPanelHandle.navigateToDeck(i);
            assertCardDisplaysDeckObject(decks[i], listPanelHandle.getDeckDisplayHandle(i));
        }
    }


    /**
     * Asserts the size of the list in {@code listPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ListPanelHandle listPanelHandle, int size) {
        int numberOfCards = listPanelHandle.getListSize();
        assertEquals(size, numberOfCards);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tag names in {@code expectedCard}.
     */
    private static void assertTagsEqual(Card expectedCard, CardDisplayHandle actualCard) {
        List<String> expectedTags = new ArrayList<String>();
        for (Tag tag : expectedCard.getTags()) {
            expectedTags.add(tag.tagName);
        }
        assertEquals(expectedTags, actualCard.getTags());

    }
}
