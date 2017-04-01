package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;


public class Settings {
    private static Logger logger = LoggerFactory.getLogger(Settings.class);

    private static Properties properties = new Properties(System.getProperties());

    private static boolean hasStart = false;

    public static void start() {
        try {
            properties.load(Settings.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            logger.error("load settings error:", e);
        }
        hasStart = true;
    }


    public static void stop() {

    }

    public static Properties getProperties() {
        if (!hasStart){
            start();
        }
        return properties;
    }


    /**
     * @param name
     * @return
     */
    public static String getString(String name) {
        return getProperties().getProperty(name);
    }

    /**
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getString(String name, String defaultValue) {
        return getProperties().getProperty(name, defaultValue);
    }

    /**
     * @param name
     * @param defaultValue
     * @return
     */
    public static Integer getInteger(String name, int defaultValue) {
        String property = getString(name);
        if (property == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(property);
        } catch (Exception e) {

        }
        return defaultValue;
    }


    /**
     * @param name
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String name, boolean defaultValue) {
        String property = getString(name);
        if (property == null) {
            return defaultValue;
        }
        property = property.trim().toLowerCase();
        if ("true".equals(property) || "yes".equals(property) || "y".equals(property)) {
            return true;
        }
        return false;
    }
}
