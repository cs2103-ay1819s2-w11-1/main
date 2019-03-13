package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_MOD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.NameContainsKeywordsPredicate;
import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.TopDeckBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TopDeck(), new TopDeck(modelManager.getTopDeck()));
        assertEquals(null, modelManager.getSelectedCard());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTopDeckFilePath(Paths.get("topdeck/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTopDeckFilePath(Paths.get("new/topdeck/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTopDeckFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setTopDeckFilePath(null);
    }

    @Test
    public void setTopDeckFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("topdeck/file/path");
        modelManager.setTopDeckFilePath(path);
        assertEquals(path, modelManager.getTopDeckFilePath());
    }

    @Test
    public void hasCard_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCard(null);
    }

    @Test
    public void hasCard_cardNotInTopDeck_returnsFalse() {
        assertFalse(modelManager.hasCard(ADDITION));
    }

    @Test
    public void hasCard_cardInTopDeck_returnsTrue() {
        modelManager.addCard(ADDITION);
        assertTrue(modelManager.hasCard(ADDITION));
    }

    @Test
    public void deleteCard_cardIsSelectedAndFirstCardInFilteredCardList_selectionCleared() {
        modelManager.addCard(ADDITION);
        modelManager.setSelectedCard(ADDITION);
        modelManager.deleteCard(ADDITION);
        assertEquals(null, modelManager.getSelectedCard());
    }

    @Test
    public void deleteCard_cardIsSelectedAndSecondCardInFilteredCardList_firstCardSelected() {
        modelManager.addCard(ADDITION);
        modelManager.addCard(SUBTRACTION);
        assertEquals(Arrays.asList(ADDITION, SUBTRACTION), modelManager.getFilteredCardList());
        modelManager.setSelectedCard(SUBTRACTION);
        modelManager.deleteCard(SUBTRACTION);
        assertEquals(ADDITION, modelManager.getSelectedCard());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addCard(ADDITION);
        modelManager.setSelectedCard(ADDITION);
        Card updatedAddition = new CardBuilder(ADDITION).withAnswer(VALID_ANSWER_MOD).build();
        modelManager.setCard(ADDITION, updatedAddition);
        assertEquals(updatedAddition, modelManager.getSelectedCard());
    }

    @Test
    public void getFilteredCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCardList().remove(0);
    }

    @Test
    public void setSelectedCard_cardNotInFilteredCardList_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        modelManager.setSelectedCard(ADDITION);
    }

    @Test
    public void setSelectedCard_cardInFilteredCardList_setsSelectedCard() {
        modelManager.addCard(ADDITION);
        assertEquals(Collections.singletonList(ADDITION), modelManager.getFilteredCardList());
        modelManager.setSelectedCard(ADDITION);
        assertEquals(ADDITION, modelManager.getSelectedCard());
    }

    @Test
    public void equals() {
        TopDeck topDeck = new TopDeckBuilder().withCard(ADDITION).withCard(SUBTRACTION).build();
        TopDeck differentTopDeck = new TopDeck();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(topDeck, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(topDeck, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different topDeck -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTopDeck, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ADDITION.getQuestion().split("\\s+");
        modelManager.updateFilteredCardList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(topDeck, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTopDeckFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(topDeck, differentUserPrefs)));
    }
}
