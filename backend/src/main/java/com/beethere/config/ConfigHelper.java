package com.beethere.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Helper class to provide static access to the Config bean.
 * Useful for non-spring classes (such as entities) that need acces to configuration values.
 */
@Component
public class ConfigHelper {
    private static Config config;

    @Autowired
    public ConfigHelper(Config config) {
        ConfigHelper.config = config;
        System.out.println("ConfigHelper initialized with Config bean");
        System.out.println(config.getLongStringMaxLength());
    }

    public static Config getConfig() {
        return config;
    }
}