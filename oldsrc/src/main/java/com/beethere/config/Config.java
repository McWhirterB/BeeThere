package com.beethere.config;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

public class Config {
    // New parameters MUST be added to the params list in fromFile()
    private String debugLevel; // TODO Validate debuglevel, or use an Enum for them

    public static Config fromFile(FileInputStream file){
        
        Config config;
        try {
            config = new Yaml().loadAs(file, Config.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid parameter found in config");
        }
        
        // snakeyaml won't throw an error if a class attribute isn't defined in the config file,
        //   it will simply leave it null. So we need to ensure no config properties are
        //   null when they shouldn't be
        List<Object> params = Arrays.asList(config.debugLevel);
        for (Object param : params){
            if (param == null){
                throw new IllegalStateException("All parameters must not be null");
            }
        }

        return config;

    }

    public String getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(String debugLevel) {
        this.debugLevel = debugLevel;
    }
}
