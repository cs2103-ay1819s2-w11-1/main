package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Helper functions for handling tags.
 */
public class TagUtil {

    /**
     * Returns true if the {@code tags} contains the {@code word}.
     * Requires a full word match for any of the tag. If the word is longer than
     * a single word it will not be considered.
     *
     * @param tags None of the tags should be null
     * @param word cannot be null, cannot be empty, must be a single word
     * @return
     */
    public static boolean containsWordInTags(Set<Tag> tags, String word) {
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        if (preppedWord.split("\\s+").length > 1) {
            return false;
        }

        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");

        return tags.stream().map(tag -> tag.tagName.toLowerCase()).anyMatch(preppedWord::equalsIgnoreCase);
    }
}
