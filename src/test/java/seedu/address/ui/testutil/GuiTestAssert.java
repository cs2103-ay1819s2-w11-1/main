package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import guitests.guihandles.CardDisplayHandle;
import guitests.guihandles.CardListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.deck.Card;
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
     * Asserts that {@code actualCard} displays the details of {@code expectedCard}.
     */
    public static void assertCardDisplaysCardObject(Card expectedCard, CardDisplayHandle actualCard) {
        assertEquals(expectedCard.getQuestion(), actualCard.getQuestion());
        assertEquals(expectedCard.getAnswer(), actualCard.getAnswer());
        assertTagsEqual(expectedCard, actualCard);
    }

    /**
     * Asserts that the list in {@code cardListPanelHandle} displays the details of {@code cards} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CardListPanelHandle cardListPanelHandle, Card... cards) {
        for (int i = 0; i < cards.length; i++) {
            cardListPanelHandle.navigateToCard(i);
            assertCardDisplaysCardObject(cards[i], cardListPanelHandle.getCardDiplayHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code cardListPanelHandle} displays the details of {@code cardss} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CardListPanelHandle cardListPanelHandle, List<Card> cards) {
        assertListMatching(cardListPanelHandle, cards.toArray(new Card[0]));
    }

    /**
     * Asserts the size of the list in {@code cardListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CardListPanelHandle cardListPanelHandle, int size) {
        int numberOfCards = cardListPanelHandle.getListSize();
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
        for (Tag tag: expectedCard.getTags()) {
            expectedTags.add(tag.tagName);
        }
        assertEquals(expectedTags, actualCard.getTags());

    }
}
