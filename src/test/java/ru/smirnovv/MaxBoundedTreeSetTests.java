package ru.smirnovv;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MaxBoundedTreeSetTests {
    @Test
    public void shouldAddWhenIsNotFull() {
        MaxBoundedTreeSet set = new MaxBoundedTreeSet(4);
        set.add(60L);
        set.add(90L);
        set.add(105L);

        set.add(78L);

        assertThat(set, hasSize(4));
        assertThat(set, contains(60L, 78L, 90L, 105L));
    }

    @Test
    public void shouldSkipWhenIsFullAndNotGreaterThanMin() {
        MaxBoundedTreeSet set = new MaxBoundedTreeSet(4);
        set.add(60L);
        set.add(90L);
        set.add(105L);
        set.add(78L);

        set.add(45L);

        assertThat(set, hasSize(4));
        assertThat(set, contains(60L, 78L, 90L, 105L));
    }

    @Test
    public void shouldAddWhenIsFullAndGreaterThanMin() {
        MaxBoundedTreeSet set = new MaxBoundedTreeSet(4);
        set.add(60L);
        set.add(90L);
        set.add(105L);
        set.add(78L);

        set.add(80L);

        assertThat(set, hasSize(4));
        assertThat(set, contains(78L, 80L, 90L, 105L));
    }

    @Test
    public void shouldNotConstructWhenSizeIsNotPositive() {
        try {
            new MaxBoundedTreeSet(0);

            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), equalTo("Argument 'maxSize' must be positive"));
        }
    }
}