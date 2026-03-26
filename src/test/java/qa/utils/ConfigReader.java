package qa.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties props = new Properties();

    // static block runs once when class is first used
    static {
        try {
            FileInputStream file = new FileInputStream(
                    "src/main/resources/config.properties"
            );
            props.load(file);
        } catch (Exception e) {
            throw new RuntimeException(
                    "config.properties not found: " + e.getMessage()
            );
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                    "Key not found in config.properties: " + key
            );
        }
        return value.trim();
    }
}