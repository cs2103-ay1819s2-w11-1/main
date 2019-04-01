package seedu.address.model.deck;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.ListItem;
import seedu.address.model.tag.Tag;

/**
 * Represents a deck object in TopDeck.
 * Guarantees: details are present and not null, field values are validated and immutable
 */
public class Card implements ListItem {
    //Identity field
    private final String question;

    private final String answer;

    private final Set<Tag> tags = new HashSet<Tag>();

    /**
     * Every field must be present and not null.
     */
    public Card(String question, String answer, Set<Tag> tags) {
        requireNonNull(question, answer);
        this.question = question;
        this.answer = answer;
        this.tags.addAll(tags);
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both cards have the same question.
     * This defines a weaker notion of equality between two cards.
     */
    public boolean isSameCard(Card otherCard) {
        if (otherCard == this) {
            return true;
        }

        return otherCard != null && otherCard.getQuestion().equals(question);
    }


    /**
     * Returns true if both cards have the same question and answers.
     * Defines a stronger notion of equality between 2 cards.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherCard = (Card) other;

        return this.answer.equals(otherCard.answer) && this.question.equals(otherCard.question) && this.tags
                .equals(otherCard.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Question: ").append(getQuestion()).append(" Answer: ").append(getAnswer())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
