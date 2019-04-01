package seedu.address.model.deck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.DeckBuilder;

public class DeckNameContainsKeywordPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DeckNameContainsKeywordsPredicate firstPredicate = new DeckNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        DeckNameContainsKeywordsPredicate secondPredicate = new DeckNameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeckNameContainsKeywordsPredicate firstPredicateCopy = new DeckNameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        DeckNameContainsKeywordsPredicate predicate = new DeckNameContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new DeckBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new DeckNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new DeckBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new DeckNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new DeckBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new DeckNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new DeckBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DeckNameContainsKeywordsPredicate predicate = new DeckNameContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new DeckBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new DeckNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new DeckBuilder().withName("Alice Bob").build()));
    }
}
