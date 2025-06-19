package com.nirmal.project.db;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static{
        try(InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("db-config.properties")){
            if (inputStream == null) {
                throw new RuntimeException("Unable to find db-config.properties");
            }
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
