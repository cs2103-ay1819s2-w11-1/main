package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.deck.Card;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building a Card object.
 */
public class CardBuilder {
    public static final String DEFAULT_QUESTION = "What is the name of this app?";
    public static final String DEFAULT_ANSWER = "TopDeck";

    private String question;
    private String answer;
    private Set<Tag> tags;

    public CardBuilder() {
        this.question = DEFAULT_QUESTION;
        this.answer = DEFAULT_ANSWER;
        this.tags = new HashSet<Tag>();
    }

    /**
     * Initialize the CardBuilder using the data of {@code cardToCopy}
     *
     * @param cardToCopy the card to get the information from
     */
    public CardBuilder(Card cardToCopy) {
        this.question = cardToCopy.getQuestion();
        this.answer = cardToCopy.getAnswer();
        this.tags = cardToCopy.getTags();
    }

    /**
     * Sets the question of the {@code Card} we are building.
     */
    public CardBuilder withQuestion(String question) {
        this.question = question;
        return this;
    }

    /**
     * Sets the answer of the {@code Card} we are building.
     */
    public CardBuilder withAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CardBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Card build() {
        return new Card(question, answer, tags);
    }
}
