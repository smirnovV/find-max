package ru.smirnovv;

import org.apache.commons.lang3.math.NumberUtils;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;

import static java.lang.System.err;
import static java.lang.System.out;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.IVersionProvider;
import static picocli.CommandLine.ParameterException;
import static picocli.CommandLine.Parameters;
import static picocli.CommandLine.ParseResult;
import static ru.smirnovv.MyCollectors.maxN;

/**
 * Program that finds the given number of maximums reading lines from the standard input.
 */
@Command(description = "Finds the given number of maximums.", name = "find-max",
        mixinStandardHelpOptions = true, versionProvider = FindMax.VersionProvider.class
)
class FindMax {
    /**
     * The number of maximums to find.
     */
    @Parameters(index = "0", description = "Number of maximums (must be positive).")
    private Integer numberOfMaximums;

    /**
     * The entry point.
     *
     * @param args the program arguments.
     */
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

    /**
     * Executes the program flow reading lines from the standard input,
     * finding maximum numbers in them and printing the result to the standard output.
     *
     * @throws IOException if an IO error occurred.
     */
    private void run() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            br.lines()
                    .filter(NumberUtils::isDigits)
                    .map(Long::valueOf)
                    .collect(maxN(numberOfMaximums))
                    .forEach(out::println);
        }
    }

    /**
     * Version provider that returns the version from the MANIFEST.MF.
     */
    public static class VersionProvider implements IVersionProvider {
        @Override
        public String[] getVersion() throws IOException {
            final Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                try (InputStream stream = resources.nextElement().openStream()) {
                    final Manifest manifest = new Manifest(stream);
                    final String title = manifest.getMainAttributes().getValue("Implementation-Title");
                    final String version = manifest.getMainAttributes().getValue("Implementation-Version");
                    if ("find-max".equals(title) && version != null) {
                        return new String[] {version};
                    }
                }
            }
            return new String[] {"No version"};
        }
    }
}
