package ru.smirnovv;

import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A bounded TreeSet of Long numbers limited by the maximum size and
 * intended to collect the maximum elements.
 *
 * @see TreeSet
 */
public class MaxBoundedTreeSet extends TreeSet<Long> {
    /**
     * The maximum number of elements in this set.
     */
    private final long maxSize;

    /**
     * Constructs an instance with the given maximum size.
     *
     * @param maxSize the maximum size.
     * @throws IllegalArgumentException if 'maxSize' is not positive.
     */
    public MaxBoundedTreeSet(/*@Nonnegative*/ final int maxSize) {
        checkArgument(maxSize > 0, "Argument 'maxSize' must be positive");

        this.maxSize = maxSize;
    }

    /**
     * Adds a new element to this set if it's not full yet or if the given element is greater
     * than the smallest one in it. When this set is full already and the given element added,
     * it removes the smallest one to keep the maximum size.
     *
     * @param e a number to be added.
     * @return {@code true} whether the given element added to this set or not.
     * @throws NullPointerException if the given number is null.
     */
    @Override
    public final boolean add(/*@NotNull*/ final Long e) {
        checkNotNull(e, "Argument 'e' cannot be null");

        if (super.size() < maxSize) {
            return super.add(e);
        } else {
            Long first = super.first();
            if (e > first) {
                super.remove(first);
                return super.add(e);
            }
        }

        return false;
    }
}
