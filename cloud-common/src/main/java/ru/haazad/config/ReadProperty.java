package ru.haazad.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperty {
    private static final String PROPERTY_FILE = "cloud-common/src/main/resources/config.properties";
    private static final Properties properties = new Properties();

    private ReadProperty() {}

    public static String getProperties(String propertyName) {
        try {
            InputStream in = new FileInputStream(PROPERTY_FILE);
            properties.load(in);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unknown property " + propertyName);
        }
    }
}
