package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.StudyView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Shows the answer to the question
 */
public class ShowAnswerCommand extends Command {

    private static String userAnswer;

    public ShowAnswerCommand(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        StudyView studyView = ((StudyView) model.getViewState());
        studyView.setCurrentStudyState(StudyView.StudyState.ANSWER);
        studyView.updateTextShown();
        studyView.setUserAnswer(userAnswer);
        return new CommandResult("");
    }


}
