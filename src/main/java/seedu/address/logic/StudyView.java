package seedu.address.logic;

import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

public class StudyView implements ViewState {
    public final List<Card> listOfCards;
    private final Deck activeDeck;
    private Card currentCard;
    private final SimpleObjectProperty<studyState> currentStudyState = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> textShown = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> userAnswer = new SimpleObjectProperty<>();
    private DeckShuffler deckShuffler;

    public enum studyState {
        QUESTION, ANSWER;
    }

    public StudyView(Deck deck) {
        this.activeDeck = deck;
        listOfCards = deck.getCards().internalList;
        setCurrentStudyState(studyState.QUESTION);
        this.deckShuffler = new DeckShuffler(activeDeck);
        generateCard();
    }

    @Override
    public Command parse(String commandWord, String arguments){

        switch (commandWord) {
            case DoneCommand.COMMAND_WORD:
                return new DoneCommand();
            default:
                if (getCurrentStudyState() == studyState.QUESTION) {
                    return new ShowAnswerCommand(commandWord+arguments);
                } else {
                    return new GenerateQuestionCommand();
                }
        }
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    //=========== Current Card ================================================================================


    public void setCurrentCard(Card card) {
        currentCard = card;
    }

    public void generateCard() {
        setCurrentCard(deckShuffler.generateCard());
        updateTextShown();
    };

    //=========== Study States ================================================================================

    public ReadOnlyProperty<studyState> studyStateProperty() {
        return currentStudyState;
    }

    public void setCurrentStudyState(studyState state) {
        currentStudyState.setValue(state);
    }

    public studyState getCurrentStudyState() {
        return currentStudyState.getValue();
    }

    //=========== TextShown ================================================================================

    public void updateTextShown() {
        String text =  (getCurrentStudyState() == studyState.QUESTION)
                ? currentCard.getQuestion()
                : currentCard.getAnswer();
        textShown.setValue(text);
    }


    public ReadOnlyProperty<String> textShownProperty() {
        updateTextShown();
        return textShown;
    }

    //=========== User Answer ================================================================================

    public ReadOnlyProperty<String> userAnswerProperty() {
        return userAnswer;
    }

    public void setUserAnswer(String answer) {
        userAnswer.setValue(answer);
    }

    public String getUserAnswer() {
        return userAnswer.getValue();
    }

}
