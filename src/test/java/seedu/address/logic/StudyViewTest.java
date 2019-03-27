package seedu.address.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.StudyView;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalDecks.DECK_A;

public class StudyViewTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Deck deck = new DeckBuilder(DECK_A).build();
    private StudyView studyView;

    @Before
    public void setUp() {
        Card subtraction = new CardBuilder(SUBTRACTION).build();
        deck.addCard(subtraction);
        studyView = new StudyView(deck);
    }

    @Test
    public void constructor() {
        assertEquals(deck, studyView.getActiveDeck());
        assertEquals(StudyView.studyState.QUESTION, studyView.getCurrentStudyState());
        assertEquals(null, studyView.getUserAnswer());
    }

    //=========== Current Card ================================================================================

    @Test
    public void setCurrentCard_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        studyView.setCurrentCard(null);
    }

    @Test
    public void setCurrentCard_validCard_setCurrentCard() {
        Card addition = new CardBuilder(ADDITION).build();
        studyView.setCurrentCard(addition);
        assertEquals(addition, studyView.getCurrentCard());
    }

    @Test
    public void generateCard_anyCondition_setCurrentCard() {
        assertNotNull(studyView.getCurrentCard());
    }

    //=========== Study States ================================================================================

    @Test
    public void setStudyState_nullState_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        studyView.setCurrentStudyState(null);
    }

    @Test
    public void setStudyState_validState_setStudyState() {
        studyView.setCurrentStudyState(StudyView.studyState.QUESTION);
        assertEquals(StudyView.studyState.QUESTION, studyView.getCurrentStudyState());
    }

    //=========== TextShown ================================================================================

    @Test
    public void updateTextShown_questionMode_returnsQuestion() {
        studyView.setCurrentStudyState(StudyView.studyState.QUESTION);
        String question = studyView.getCurrentCard().getQuestion();
        assertEquals(question, studyView.textShownProperty().getValue());
    }

    @Test
    public void updateTextShown_answerMode_returnsAnswer() {
        studyView.setCurrentStudyState(StudyView.studyState.ANSWER);
        String answer = studyView.getCurrentCard().getAnswer();
        assertEquals(answer, studyView.textShownProperty().getValue());
    }

    //=========== User Answer ================================================================================

    @Test
    public void setUserAnswer_nullAnswer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        studyView.setUserAnswer(null);
    }

    @Test
    public void setUserAnswer_validAnswer_setUserAnswer() {
        String sampleUserAnswer = "Four";
        studyView.setUserAnswer(sampleUserAnswer);
        assertEquals(sampleUserAnswer, studyView.getUserAnswer());
    }
}
