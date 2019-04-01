package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * ViewState of the Application during a Study session.
 */
public class StudyView implements ViewState {
    public final List<Card> listOfCards;
    private final Model model;
    private final Deck activeDeck;
    private final SimpleObjectProperty<StudyState> currentStudyState = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> textShown = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> userAnswer = new SimpleObjectProperty<>();
    private Card currentCard;
    private DeckShuffler deckShuffler;


    public StudyView(Deck deck) {
        this.activeDeck = deck;
        listOfCards = deck.getCards().internalList;
        setCurrentStudyState(StudyState.QUESTION);
        this.deckShuffler = new DeckShuffler(activeDeck);
        generateCard();
    }

    @Override
    public Command parse(String commandWord, String arguments){

        switch (commandWord) {
            case DoneCommand.COMMAND_WORD:
                return new DoneCommand();
            default:
                if (getCurrentStudyState() == StudyState.QUESTION) {
                    return new ShowAnswerCommand(commandWord + arguments);
                } else {
                    return new GenerateQuestionCommand();
                }
        }
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    //=========== Current Card ================================================================================

    /**
     * Sets the current card to be studied.
     */
    public void setCurrentCard(Card card) {
        requireNonNull(card);
        currentCard = card;
    }
    
  public Card getCurrentCard() {
        return currentCard;
    }

    //=========== Current Card =========================================================================

    /**
     * Generates the next card to be studied.
     */
    public void generateCard() {
        setCurrentCard(deckShuffler.generateCard());
        updateTextShown();
    }

    public ReadOnlyProperty<StudyState> studyStateProperty() {
        return currentStudyState;
    }
    public void setCurrentStudyState(studyState state) {
        requireNonNull(state);
        currentStudyState.setValue(state);
    }
    //=========== Study States =========================================================================

    public StudyState getCurrentStudyState() {
        return currentStudyState.getValue();
    }

    public void setCurrentStudyState(StudyState state) {
        currentStudyState.setValue(state);
    }

    /**
     * Updates the text shown in the UI.
     */
    public void updateTextShown() {
        String text = (getCurrentStudyState() == StudyState.QUESTION) ? currentCard
                .getQuestion() : currentCard.getAnswer();
        textShown.setValue(text);
    }

    //=========== TextShown ================================================================================

    /**
     * Returns the current textShown
     */
    public ReadOnlyProperty<String> textShownProperty() {
        updateTextShown();
        return textShown;
    }

    /**
     * Returns the user's answer
     */
    public ReadOnlyProperty<String> userAnswerProperty() {
        return userAnswer;
    }

    public void setUserAnswer(String answer) {
        requireNonNull(answer);
        userAnswer.setValue(answer);
    }
    //=========== User Answer ================================================================================

    public String getUserAnswer() {
        return userAnswer.getValue();
    }

    public void setUserAnswer(String answer) {
        userAnswer.setValue(answer);
    }

    /**
     * The type of possible states that the study view can have.
     */
    public enum StudyState { QUESTION, ANSWER }

}
