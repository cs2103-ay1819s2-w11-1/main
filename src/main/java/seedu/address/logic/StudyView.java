package seedu.address.logic;

import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * Stores the state of the Study View.
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

    public StudyView(Model model, Deck deck) {
        this.model = model;
        this.activeDeck = deck;
        listOfCards = deck.getCards().internalList;
        setCurrentStudyState(StudyState.QUESTION);
        this.deckShuffler = new DeckShuffler(activeDeck);
        generateCard();
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {

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

    public void setCurrentCard(Card card) {
        currentCard = card;
    }

    //=========== Current Card ================================================================================

    public void generateCard() {
        setCurrentCard(deckShuffler.generateCard());
        updateTextShown();
    }

    public ReadOnlyProperty<StudyState> studyStateProperty() {
        return currentStudyState;
    }

    //=========== Study States ================================================================================

    public StudyState getCurrentStudyState() {
        return currentStudyState.getValue();
    }

    public void setCurrentStudyState(StudyState state) {
        currentStudyState.setValue(state);
    }

    public void updateTextShown() {
        String text = (getCurrentStudyState() == StudyState.QUESTION) ? currentCard.getQuestion() : currentCard
                .getAnswer();
        textShown.setValue(text);
    }

    //=========== TextShown ================================================================================

    /**
     * String to be shown in flashcard
     */
    public ReadOnlyProperty<String> textShownProperty() {
        updateTextShown();
        return textShown;
    }

    public ReadOnlyProperty<String> userAnswerProperty() {
        return userAnswer;
    }

    //=========== User Answer ================================================================================

    public String getUserAnswer() {
        return userAnswer.getValue();
    }

    public void setUserAnswer(String answer) {
        userAnswer.setValue(answer);
    }

    public enum StudyState {
        QUESTION, ANSWER
    }

}
