package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.deck.Card;

/**
 * Provides a handle to a card display in the card list panel.
 */
public class CardDisplayHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String QUESTION_FIELD_ID = "#question";
    private static final String ANSWER_FIELD_ID = "#answer";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label questionLabel;
    private final Label answerLabel;
    private final List<Label> tagLabels;

    public CardDisplayHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        questionLabel = getChildNode(QUESTION_FIELD_ID);
        answerLabel = getChildNode(ANSWER_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer.getChildrenUnmodifiable().stream().map(Label.class::cast)
                                 .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    public String getAnswer() {
        return answerLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels.stream().map(Label::getText).collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code card}.
     *
     * @param card
     */
    public boolean equals(Card card) {
        return getQuestion().equals(card.getQuestion()) && getAnswer().equals(card.getAnswer())
            && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset
            .copyOf(card.getTags().stream().map(tag -> tag.tagName)
                    .collect(Collectors.toList())));
    }
}
