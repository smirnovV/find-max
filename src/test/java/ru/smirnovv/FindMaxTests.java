package ru.smirnovv;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class FindMaxTests {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        systemOutRule.clearLog();
    }

    private String[] input = {"3"};

    @Test
    public void shouldSkipNonNumericLines() throws Exception {
        systemInMock.provideLines("r", "y", "1", "i", "8", "t", "3");

        FindMax.main(input);

        assertThat(systemOutRule.getLog(),
                containsString(Stream.of("1", "3", "8").collect(Collectors.joining(System.lineSeparator()))));
    }

    @Test
    public void shouldFindNothingWhenEmptyInput() throws Exception {
        FindMax.main(input);

        assertThat(systemOutRule.getLogWithNormalizedLineSeparator(), isEmptyString());
    }

    @Test
    public void shouldFindMaxesSuccessfully() throws Exception {
        systemInMock.provideLines("10", "5", "7", "6", "9", "1");

        FindMax.main(input);

        assertThat(systemOutRule.getLog(),
                containsString(Stream.of("7", "9", "10").collect(Collectors.joining(System.lineSeparator()))));
    }
}
