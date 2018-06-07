package ru.smirnovv;

import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MaxBoundedTreeSet extends TreeSet<Long> {
    private final long maxSize;

    public MaxBoundedTreeSet(/*@Nonnegative*/ final int maxSize) {
        checkArgument(maxSize > 0, "Argument 'maxSize' must be positive");

        this.maxSize = maxSize;
    }

    @Override
    public boolean add(/*@NotNull*/final Long e) {
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
