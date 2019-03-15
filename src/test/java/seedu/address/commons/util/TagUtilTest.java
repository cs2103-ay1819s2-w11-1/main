package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.tag.Tag;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.HELLO_WORLD;
import static seedu.address.testutil.TypicalCards.NO_TAG;

public class TagUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for containsWordInTags --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty
     * Invalid equivalence partitions for sentence: null
     * The three test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordInTags_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, HELLO_WORLD.getTags(), null, Optional.empty());
    }

    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, Set<Tag> tags, String word,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        TagUtil.containsWordInTags(tags, word);
    }

    @Test
    public void containsWordInTags_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, HELLO_WORLD.getTags(), "  ",
            Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordInTags_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word that is alphanumeric
     *
     * Valid tag set:
     *   - empty tag set
     *
     * Possible scenarios returning true:
     *   - matches first tag
     *   - last of the tags
     *   - middle tag
     *
     * Possible scenarios returning false:
     *   - query word matches part of a tag
     *
     * HELLO_WORD has ["Simple", "CS"] tags
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordInTags_validInputs_correctResult() {

        // Empty tags
        assertFalse(TagUtil.containsWordInTags(NO_TAG.getTags(), "abc")); // Boundary case

        // Matches a partial word only
        assertFalse(TagUtil.containsWordInTags(HELLO_WORLD.getTags(), "Simp")); // Sentence word bigger than query word
        assertFalse(TagUtil.containsWordInTags(HELLO_WORLD.getTags(), "Simpler")); // Query word bigger than sentence word

        // Matches word in the tag, different upper/lower case letters
        assertTrue(TagUtil.containsWordInTags(HELLO_WORLD.getTags(), "siMPle")); // Matches the tag that is of different case
    }

}
