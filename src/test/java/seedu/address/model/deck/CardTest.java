package seedu.address.model.deck;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.CardBuilder;

public class CardTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Card card = new CardBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        card.getTags().remove(0);
    }

    @Test
    public void isSameCard() {
        Card additionCopy = new CardBuilder(ADDITION).build();

        // same object -> returns true
        assertTrue(additionCopy.isSameCard(additionCopy));

        // null -> returns false
        assertFalse(additionCopy.isSameCard(null));

        // different question and answer -> return false
        assertFalse(additionCopy.isSameCard(SUBTRACTION));

        // same question, different answer -> returns true
        Card editedAddition = new CardBuilder(ADDITION).withAnswer("DIFFERENT").build();
        assertTrue(ADDITION.isSameCard(editedAddition));
    }

    @Test
    public void equals() {
        Card additionCopy = new CardBuilder(ADDITION).build();

        // same object -> returns true
        assertTrue(additionCopy.equals(additionCopy));

        // null -> returns false
        assertFalse(additionCopy.equals(null));

        // different type -> return false
        assertFalse(additionCopy.equals(5));

        // different card -> return false
        assertFalse(additionCopy.equals(SUBTRACTION));

        // different answer -> return false
        Card editedAddition = new CardBuilder(ADDITION).withAnswer("DIFFERENT").build();
        assertFalse(ADDITION.equals(editedAddition));
    }
}
