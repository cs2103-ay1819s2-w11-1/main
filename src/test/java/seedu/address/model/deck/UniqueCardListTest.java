package seedu.address.model.deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MOD;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.model.deck.exceptions.DuplicateCardException;
import seedu.address.testutil.CardBuilder;

public class UniqueCardListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueCardList uniqueCardList = new UniqueCardList();

    @Test
    public void contains_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.contains(null);
    }

    @Test
    public void contains_cardNotInList_returnsFalse() {
        assertFalse(uniqueCardList.contains(ADDITION));
    }

    @Test
    public void contains_cardInList_returnsTrue() {
        uniqueCardList.add(ADDITION);
        assertTrue(uniqueCardList.contains(ADDITION));
    }

    @Test
    public void contains_cardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCardList.add(ADDITION);
        Card editedAddition = new CardBuilder(ADDITION).withAnswer(VALID_ANSWER_MOD).withTags(VALID_TAG_MOD)
                                                       .build();
        assertTrue(uniqueCardList.contains(editedAddition));
    }

    @Test
    public void add_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.add(null);
    }

    @Test
    public void add_duplicateCard_throwsDuplicateCardException() {
        uniqueCardList.add(ADDITION);
        thrown.expect(DuplicateCardException.class);
        uniqueCardList.add(ADDITION);
    }

    @Test
    public void setCard_nullTargetCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCard(null, ADDITION);
    }

    @Test
    public void setCard_nullEditedCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCard(ADDITION, null);
    }

    @Test
    public void setCard_targetCardNotInList_throwsPersonNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        uniqueCardList.setCard(ADDITION, ADDITION);
    }

    @Test
    public void setCard_editedCardIsSameCard_success() {
        uniqueCardList.add(ADDITION);
        uniqueCardList.setCard(ADDITION, ADDITION);
        UniqueCardList expectedUniquePersonList = new UniqueCardList();
        expectedUniquePersonList.add(ADDITION);
        assertEquals(expectedUniquePersonList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasSameIdentity_success() {
        uniqueCardList.add(ADDITION);
        Card editedAddition = new CardBuilder(ADDITION).withAnswer(VALID_ANSWER_HELLO).withTags(VALID_TAG_MOD)
                                                       .build();
        uniqueCardList.setCard(ADDITION, editedAddition);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(editedAddition);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasDifferentIdentity_success() {
        uniqueCardList.add(ADDITION);
        uniqueCardList.setCard(ADDITION, SUBTRACTION);
        UniqueCardList expectedUniquePersonList = new UniqueCardList();
        expectedUniquePersonList.add(SUBTRACTION);
        assertEquals(expectedUniquePersonList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasNonUniqueIdentity_throwsDuplicateCardException() {
        uniqueCardList.add(ADDITION);
        uniqueCardList.add(SUBTRACTION);
        thrown.expect(DuplicateCardException.class);
        uniqueCardList.setCard(ADDITION, SUBTRACTION);
    }

    @Test
    public void remove_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.remove(null);
    }

    @Test
    public void remove_cardDoesNotExist_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        uniqueCardList.remove(ADDITION);
    }

    @Test
    public void remove_existingCard_removesCard() {
        uniqueCardList.add(ADDITION);
        uniqueCardList.remove(ADDITION);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_nullUniqueCardList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCards((UniqueCardList) null);
    }

    @Test
    public void setCards_uniqueCardList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueCardList.add(ADDITION);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(SUBTRACTION);
        uniqueCardList.setCards(expectedUniqueCardList);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCards((List<Card>) null);
    }

    @Test
    public void setCards_list_replacesOwnListWithProvidedList() {
        uniqueCardList.add(ADDITION);
        List<Card> cardList = Collections.singletonList(SUBTRACTION);
        uniqueCardList.setCards(cardList);
        UniqueCardList expectedUniquePersonList = new UniqueCardList();
        expectedUniquePersonList.add(SUBTRACTION);
        assertEquals(expectedUniquePersonList, uniqueCardList);
    }

    @Test
    public void setCards_listWithDuplicateCards_throwsDuplicateCardException() {
        List<Card> listWithDuplicateCards = Arrays.asList(ADDITION, ADDITION);
        thrown.expect(DuplicateCardException.class);
        uniqueCardList.setCards(listWithDuplicateCards);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCardList.asUnmodifiableObservableList().remove(0);
    }
}
