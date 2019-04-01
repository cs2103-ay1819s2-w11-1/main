package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MATH;

import org.junit.Test;

import seedu.address.testutil.EditCardDescriptorBuilder;

public class EditCardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCardCommand.EditCardDescriptor descriptorWithSameValues = new EditCardCommand.EditCardDescriptor(
                DESC_HELLO);
        assertTrue(DESC_HELLO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_HELLO.equals(DESC_HELLO));

        // null -> returns false
        assertFalse(DESC_HELLO.equals(null));

        // different types -> returns false
        assertFalse(DESC_HELLO.equals(5));

        // different values -> returns false
        assertFalse(DESC_HELLO.equals(DESC_MOD));

        // different question -> returns false
        EditCardCommand.EditCardDescriptor editedHello = new EditCardDescriptorBuilder(DESC_HELLO)
                .withQuestion(VALID_QUESTION_MOD).build();
        assertFalse(DESC_HELLO.equals(editedHello));

        // different answer -> returns false
        editedHello = new EditCardDescriptorBuilder(DESC_HELLO).withAnswer(VALID_ANSWER_MOD).build();
        assertFalse(DESC_HELLO.equals(editedHello));

        // different tags -> returns false
        editedHello = new EditCardDescriptorBuilder(DESC_HELLO).withTags(VALID_TAG_MATH).build();
        assertFalse(DESC_HELLO.equals(editedHello));
    }
}
