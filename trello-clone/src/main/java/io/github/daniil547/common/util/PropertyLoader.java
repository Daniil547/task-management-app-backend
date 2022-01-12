package io.github.daniil547.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    public static Properties loadProperties() {
        InputStream in = PropertyLoader.class.getClassLoader()
                                             .getResourceAsStream("application.properties");

        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;

    }
}