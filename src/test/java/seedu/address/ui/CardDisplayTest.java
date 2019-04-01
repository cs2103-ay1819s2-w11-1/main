package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCardObject;

import org.junit.Test;

import guitests.guihandles.CardDisplayHandle;
import seedu.address.model.deck.Card;
import seedu.address.testutil.CardBuilder;

public class CardDisplayTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Card cardWithNoTags = new CardBuilder().withTags().build();
        CardDisplay cardDisplay = new CardDisplay(cardWithNoTags, 1);
        uiPartRule.setUiPart(cardDisplay);
        assertCardDisplay(cardDisplay, cardWithNoTags, 1);

        // with tags
        Card personWithTags = new CardBuilder().build();
        cardDisplay = new CardDisplay(personWithTags, 2);
        uiPartRule.setUiPart(cardDisplay);
        assertCardDisplay(cardDisplay, personWithTags, 2);
    }

    @Test
    public void equals() {
        Card person = new CardBuilder().build();
        CardDisplay cardDisplay = new CardDisplay(person, 0);

        // same card, same index -> returns true
        CardDisplay copy = new CardDisplay(person, 0);
        assertTrue(cardDisplay.equals(copy));

        // same object -> returns true
        assertTrue(cardDisplay.equals(cardDisplay));

        // null -> returns false
        assertFalse(cardDisplay.equals(null));

        // different types -> returns false
        assertFalse(cardDisplay.equals(0));

        // different card, same index -> returns false
        Card differentQuestion = new CardBuilder().withQuestion("differentQuestion").build();
        assertFalse(cardDisplay.equals(new CardDisplay(differentQuestion, 0)));

        // same card, different index -> returns false
        assertFalse(cardDisplay.equals(new CardDisplay(person, 1)));
    }

    /**
     * Asserts that {@code cardDisplay} displays the details of {@code expectedCard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CardDisplay cardDisplay, Card expectedCard, int expectedId) {
        guiRobot.pauseForHuman();

        CardDisplayHandle cardDisplayHandle = new CardDisplayHandle(cardDisplay.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", cardDisplayHandle.getId());

        // verify card details are displayed correctly
        assertCardDisplaysCardObject(expectedCard, cardDisplayHandle);
    }
}
