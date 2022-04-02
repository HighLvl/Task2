/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package task1;

import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        var options = buildOptions();
        var cmdParser = new DefaultParser();
        try {
            var line = cmdParser.parse(options, args);
            if (line.hasOption("h")) {
                new HelpFormatter().printHelp("task1", options);
            }
            var inputFilePath = line.getOptionValue("f");
            String outputFilePath = null;
            if (line.hasOption("o")) {
                outputFilePath = line.getOptionValue("o");
            }
            new App().launch(inputFilePath, outputFilePath);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            new HelpFormatter().printHelp("task1", options);
        }
    }

    private static Options buildOptions() {
        var inputFileOption = Option.builder()
                .option("f")
                .argName("file")
                .hasArg()
                .desc("*.osm.bz2")
                .required()
                .build();
        var outputOption = Option.builder()
                .option("o")
                .argName("file")
                .hasArg()
                .desc("Statistic out file")
                .build();
        var helpOption = Option.builder()
                .option("h")
                .desc("help")
                .build();
        var options = new Options();
        options.addOption(inputFileOption);
        options.addOption(outputOption);
        options.addOption(helpOption);
        return options;
    }

    private void launch(String osmDataPath, String statFilePath) {
        logger.info("Hello world!");
        try (var inputStream = openInputStream(osmDataPath);
             var outputStream = getOutputStream(statFilePath)) {
            collectStat(inputStream, outputStream);
        } catch (IOException | XMLStreamException e) {
            logger.error(e.getMessage());
        }
    }

    private OutputStream getOutputStream(String statFilePath) throws IOException {
        if (statFilePath == null) {
            return System.out;
        }
        var file = new File(statFilePath);
        if (!file.createNewFile()) {
            logger.info("Output file " + statFilePath + " already exists. Rewrite it");
        }
        else {
            logger.info("New output file created: " + statFilePath);
        }
        return new FileOutputStream(statFilePath);
    }

    private void collectStat(InputStream inputStream, OutputStream outputStream) throws XMLStreamException {
        var xmlReader = XMLInputFactory.newFactory().createXMLStreamReader(inputStream);
        var userToEditNumberMap = new HashMap<String, Integer>();
        var tagKeyToNodeNumberMap = new HashMap<String, Integer>();
        new OpenStreetMapXMLProcessor(xmlReader, userToEditNumberMap, tagKeyToNodeNumberMap).process();
        printStat(userToEditNumberMap, tagKeyToNodeNumberMap, outputStream);
    }

    private void printStat(Map<String, Integer> userToEditNumberMap, Map<String, Integer> tagKeyToNodeNumberMap, OutputStream outputStream) {
        printStatForMap(userToEditNumberMap, "Number of edits for users:", outputStream);
        printStatForMap(tagKeyToNodeNumberMap, "Number of tagged nodes for tags:", outputStream);
    }

    private void printStatForMap(Map<String, Integer> map, String title, OutputStream outputStream) {
        var entries = map
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        var writer = new PrintWriter(outputStream, true);
        writer.println(title);
        entries.forEach((entry) -> writer.println(entry.getKey() + " - " + entry.getValue()));
        writer.println();
    }

    private InputStream openInputStream(String path) throws IOException {
        var file = new File(path);
        return new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(file)));
    }
}
