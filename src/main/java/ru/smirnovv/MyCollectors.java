package ru.smirnovv;

import java.util.stream.Collector;

import static com.google.common.base.Preconditions.checkArgument;

public class MyCollectors {
    public static Collector<Long, MaxBoundedTreeSet,
            MaxBoundedTreeSet> maxN(/*@Nonnegative*/ final int numberOfMaxes) {
        checkArgument(numberOfMaxes > 0,
                "Argument 'numberOfMaxes' must be positive");

        return Collector.of(
                () -> new MaxBoundedTreeSet(numberOfMaxes),
                MaxBoundedTreeSet::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                }
        );
    }
}
