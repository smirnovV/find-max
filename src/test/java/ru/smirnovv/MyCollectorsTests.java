package ru.smirnovv;

import org.junit.Test;

import java.util.TreeSet;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MyCollectorsTests {
    @Test
    public void shouldAddWhenMaxNCollector() {
        TreeSet<Long> set = Stream.of(60L, 90L, 105L, 78L, 15L).collect(MyCollectors.maxN(4));

        assertThat(set, hasSize(4));
        assertThat(set, contains(60L, 78L, 90L, 105L));
    }
}