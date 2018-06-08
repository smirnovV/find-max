package ru.smirnovv;

import java.util.stream.Collector;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A utility class containing factory-methods for own collectors.
 */
public final class MyCollectors {
    /**
     * Private constructor to block instantiation.
     */
    private MyCollectors() {
    }

    /**
     * Returns a collector that accumulates the input elements into a bounded set of maximum ones.
     *
     * @param numberOfMaximums the maximum number of maximum elements.
     * @return the new {@code collector}.
     * @throws IllegalArgumentException if 'numberOfMaximums' is not positive.
     * @see Collector
     * @see MaxBoundedTreeSet
     */
    public static Collector<Long, MaxBoundedTreeSet, MaxBoundedTreeSet> maxN(/*@Nonnegative*/
            final int numberOfMaximums) {
        checkArgument(numberOfMaximums > 0, "Argument 'numberOfMaxes' must be positive");

        return Collector.of(
                () -> new MaxBoundedTreeSet(numberOfMaximums),
                MaxBoundedTreeSet::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                }
        );
    }
}
