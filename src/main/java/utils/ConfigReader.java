package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {
    private static Logger logger = LogManager.getLogger(ConfigReader.class);

    public static String loadValue(String configName, String rowName, String colName) {
        Properties prop = new Properties();
        String value = null;

        try (InputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(inputStream);

            BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/" + prop.getProperty(configName + "_CONFIG_FILE")));
//			CSVParser parser = new CSVParser(reader, CSVFormat.newFormat(';').withHeader(prop.getProperty(configName + "_HEADERS")));
            CSVParser parser = new CSVParser(reader, CSVFormat.newFormat(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            for (CSVRecord record : parser) {
                if (rowName.equals(record.get(0))) {
                    value = record.get(colName);
                    break;
                }
            }
        } catch (IOException exception) {
            logger.debug("Błąd wczytywania konfiguracji");
        }

        return value;
    }
}
