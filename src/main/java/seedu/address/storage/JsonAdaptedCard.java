package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deck.Card;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Card}.
 */
public class JsonAdaptedCard {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    private final String question;
    private final String answer;

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCard} with the given card details.
     */
    @JsonCreator
    public JsonAdaptedCard(@JsonProperty("question") String question, @JsonProperty("answer") String answer,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.question = question;
        this.answer = answer;
        if (tagged != null) {
            this.tags.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Card} into this class for Jackson's use.
     */
    public JsonAdaptedCard(Card source) {
        this.question = source.getQuestion();
        this.answer = source.getAnswer();
        this.tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted card object into the model's {@code Card} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card.
     */
    public Card toModelType() throws IllegalValueException {
        final List<Tag> cardTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            cardTags.add(tag.toModelType());
        }

        if (question == null || question.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "question"));
        }

        if (answer == null || answer.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "answer"));
        }

        final Set<Tag> modelTags = new HashSet<Tag>(cardTags);

        return new Card(question, answer, modelTags);
    }
}
