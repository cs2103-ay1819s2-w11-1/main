package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

import seedu.address.model.tag.Tag;

public class TagUtil {

    /**
     * Returns true if the {@code tags} contains the {@code word}.
     * Requires a full word match for any of the tag.
     *
     * @param tags None of the tags should be null
     * @param word cannot be null, cannot be empty, must be a single word
     * @return
     */
    public static boolean containsWordInTags(Set<Tag> tags, String word) {
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        return tags.stream().map(tag -> tag.tagName)
            .anyMatch(preppedWord::equalsIgnoreCase);
    }
}
