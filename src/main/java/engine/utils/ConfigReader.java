package engine.utils;

import model.enums.ProfessionType;
import model.enums.SpecialSkillType;
import model.enums.TerrainType;
import model.enums.cards.event.EventType;
import model.enums.cards.event.IconType;
import model.enums.cards.event.ThreatType;
import model.enums.elements.ResourceType;
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

	public static <T> T loadValue(Class<T> outputClass, String config, String row, String col) {
		Properties prop = new Properties();
		String stringValue = null;

		try (InputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
			prop.load(inputStream);

			BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/" + prop.getProperty(config + "_CONFIG_FILE")));
			CSVParser parser = new CSVParser(reader, CSVFormat.newFormat(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			for (CSVRecord record : parser) {
				if (row.equals(record.get(0))) {
					stringValue = record.get(col);
					break;
				}
			}
		} catch (IOException exception) {
			logger.debug("Błąd wczytywania konfiguracji");
		}

		return castValueFromString(outputClass, stringValue);
	}

	private static <T> T castValueFromString(Class<T> outputClass, String stringValue) {
		T value = null;
		if (outputClass == String.class) {
			value = outputClass.cast(stringValue);
		} else if (outputClass == Integer.class) {
			value = outputClass.cast(Integer.parseInt(stringValue));
		} else if (outputClass == Boolean.class) {
			value = outputClass.cast(Boolean.valueOf(stringValue));
		} else if (outputClass == SpecialSkillType.class) {
			value = outputClass.cast(SpecialSkillType.valueOf(stringValue));
        } else if (outputClass == EventType.class) {
            value = outputClass.cast(EventType.valueOf(stringValue));
        } else if (outputClass == IconType.class) {
            value = outputClass.cast(IconType.valueOf(stringValue));
        } else if (outputClass == ThreatType.class) {
            value = outputClass.cast(ThreatType.valueOf(stringValue));
		} else if (outputClass == TerrainType.class) {
			value = outputClass.cast(TerrainType.valueOf(stringValue));
		} else if (outputClass == ResourceType.class) {
			value = outputClass.cast(ResourceType.valueOf(stringValue));
		} else if (outputClass == ProfessionType.class) {
			value = outputClass.cast(ProfessionType.valueOf(stringValue));
		} else {
			logger.warn("Błąd rzutowania typów w trakcie inicjalizacji danych.");
		}

		return value;
	}
}
