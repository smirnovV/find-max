package ru.smirnovv;

import org.apache.commons.lang3.math.NumberUtils;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.System.err;
import static java.lang.System.out;
import static picocli.CommandLine.*;
import static ru.smirnovv.MyCollectors.maxN;

@Command(description = "Finds the given number of maximums.",
        name = "FindMax", mixinStandardHelpOptions = true,
        version = "FindMax 0.1.0")
class FindMax {
    @Parameters(index = "0",
            description = "Number of maximums (must be positive).")
    private Integer numberOfMaximums;

    public static void main(final String[] args) {
        FindMax findMax = new FindMax();
        CommandLine cmd = new CommandLine(findMax);

        try {
            ParseResult parseResult = cmd.parseArgs(args);

            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(out);
            } else if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(out);
            } else if (findMax.numberOfMaximums <= 0) {
                out.println("Number of maximums must be positive!");
            } else {
                findMax.run();
            }
        } catch (ParameterException ex) {
            err.println(ex.getMessage());
            cmd.usage(out);
        } catch (Exception ex) {
            err.println(ex.getMessage());
        }
    }

    private void run() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            br.lines()
                    .filter(NumberUtils::isDigits)
                    .map(Long::valueOf)
                    .collect(maxN(numberOfMaximums))
                    .forEach(out::println);
        }
    }
}