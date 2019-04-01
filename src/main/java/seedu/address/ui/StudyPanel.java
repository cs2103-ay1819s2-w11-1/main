package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.StudyView;


/**
 * The Study Panel of the App.
 */
public class StudyPanel extends UiPart<Region> {

    private static final String FXML = "StudyPanel.fxml";
    private static final PseudoClass ANSWER = PseudoClass.getPseudoClass("answer");
    private static final String YOUR_ANSWER_LABEL = "Your answer: ";

    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private HBox studyPane;

    @FXML
    private HBox card;

    @FXML
    private Label question;

    @FXML
    private Label id;

    @FXML
    private Label userAnswerLabel;


    public StudyPanel(ObservableValue<String> textShown, ObservableValue<StudyView.StudyState> studyState,
                      ObservableValue<String> userAnswer) {
        super(FXML);

        question.setText(textShown.getValue());
        userAnswerLabel.setVisible(false);
        card.pseudoClassStateChanged(ANSWER, false);
        textShown.addListener((observable, oldValue, newValue) -> {
            logger.info("textShown changed to: " + newValue);
            question.setText(textShown.getValue());
        });

        studyState.addListener((observable, oldValue, newValue) -> {
            logger.info("color changed for: " + newValue);
            card.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.StudyState.ANSWER);
            question.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.StudyState.ANSWER);
            userAnswerLabel.setVisible(studyState.getValue() == StudyView.StudyState.ANSWER);
        });

        userAnswer.addListener((observable, oldValue, newValue) -> {
            logger.info("user answer changed to: " + newValue);
            userAnswerLabel.setText(YOUR_ANSWER_LABEL + userAnswer.getValue());
        });

    }

}
