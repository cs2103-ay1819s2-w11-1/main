package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.ListItem;
import seedu.address.model.Model;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ListItem> PREDICATE_MATCHING_NO_CARDS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<? extends ListItem> toDisplay) {
        Optional<Predicate<ListItem>> predicate = toDisplay.stream().map(ModelHelper::getPredicateMatching)
                                                           .reduce(Predicate::or);
        model.updateFilteredList(predicate.orElse(PREDICATE_MATCHING_NO_CARDS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, ListItem... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ListItem} equals to {@code other}.
     *
     * @param other
     */
    private static Predicate<ListItem> getPredicateMatching(ListItem other) {
        return item -> item.equals(other);
    }
}
