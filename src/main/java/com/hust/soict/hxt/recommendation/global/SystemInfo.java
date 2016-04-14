package com.hust.soict.hxt.recommendation.global;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * Created by thuyenhx on 3/30/16.
 */
public class SystemInfo {
    private static Configuration configuration;

    private static String configFilePath = "conf.properties";

    static {
        try {
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(configFilePath);
            FileChangedReloadingStrategy reloadingStrategy = new FileChangedReloadingStrategy();
            propertiesConfiguration.setReloadingStrategy(reloadingStrategy);
            configuration = propertiesConfiguration;

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private SystemInfo() {

    }

    public static synchronized Configuration getConfiguration() {
        if (configuration == null)
            try {
                configuration = new PropertiesConfiguration(configFilePath);
            } catch (ConfigurationException e) {
                e.printStackTrace();
                System.exit(15);
            }
        return configuration;
    }
}
