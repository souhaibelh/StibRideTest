package config;
import java.io.*;
import java.util.Properties;

/**
 * Class that makes sure there is only one instance of properties file that will be shared amongst other classes
 */
public class ConfigManager {
    private final Properties prop;

    private ConfigManager() {
        prop = new Properties();
        load();
    }

    /**
     * Method that loads the property file in the prop attribute of the class
     */
    private void load() {
        File properties = new File(ConfigManager.class.getClassLoader().getResource("config/config.properties").getFile());
        try {
            InputStream is = new FileInputStream(properties);
            prop.load(is);
            is.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find config file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Couldn't load properties: " + e.getMessage());
        }
    }

    /**
     * Returns the property that is connected to the key passed as attribute
     * @param propertyName name of the property we are trying to get
     * @return the property contents
     */
    public String getProperty(String propertyName) {
        return prop.getProperty(propertyName);
    }

    /**
     * @return single instance of ConfigManager held inside ConfigManagerHolder
     */
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    /**
     * Private class that makes sure there is only 1 instance of ConfigManager
     */
    private static class ConfigManagerHolder {
        /**
         * Unique instance of ConfigManager
         */
        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
