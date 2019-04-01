package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedCard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCards.HELLO_WORLD;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;

public class JsonAdaptedCardTest {

    private static final String INVALID_TAG = "#friend";

    private static final String VALID_QUESTION = HELLO_WORLD.getQuestion();
    private static final String VALID_ANSWER = HELLO_WORLD.getAnswer();
    private static final List<JsonAdaptedTag> VALID_TAGS = HELLO_WORLD.getTags().stream()
                                                                      .map(JsonAdaptedTag::new)
                                                                      .collect(Collectors.toList());

    @Test
    public void toModelType_validCardDetails_returnsCard() throws Exception {
        JsonAdaptedCard card = new JsonAdaptedCard(HELLO_WORLD);
        assertEquals(HELLO_WORLD, card.toModelType());
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(null, VALID_ANSWER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "question");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "answer");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }


    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, VALID_ANSWER, invalidTags);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

}
