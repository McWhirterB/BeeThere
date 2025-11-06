package com.beethere.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.yaml.snakeyaml.Yaml;


/**
 * Implements a singleton pattern.
 * <br></br>
 * Usage example:
 * <br></br>
 * <pre>
 * {@code
 * Config config = Config.getInstance();
 * String value = config.getValue;
 * }
 * </pre>
 * <br></br>
 * It is recommended to call getInstance() at the entry point to trigger a crash from loading the config file sooner rather than later
 */
// New parameters MUST be added to the params list in fromFile()
public class Config {
    private static Config instance;
    private int shortStringMaxLength = -1;
    private int longStringMaxLength = -1;
    private String logConfigPath = "";

    public static Config getInstance() throws IOException{
        if (instance == null){
            initializeFromFile();
        }
        return instance;
    }

    private Config(){} 

    public int getShortStringMaxLength(){
        return shortStringMaxLength;
    }

    public void setShortStringMaxLength(int val){
        this.shortStringMaxLength = val;
    }

    public int getLongStringMaxLength(){
        return longStringMaxLength;
    }

    public void setLongStringMaxLength(int val){
        this.longStringMaxLength = val;
    }

    public String getLogConfigPath(){
        return logConfigPath;
    }

    public void setLogConfigPath(String path){
        this.logConfigPath = path;
    }

    public static void initializeFromFile() throws IOException{
        IllegalStateException missingConfigException = new IllegalStateException("All parameters must be set in config");
        
        FileInputStream file;
		try {
			file = new FileInputStream("config.yaml");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Config file not found");
		}

        try {
            Config.instance = new Yaml().loadAs(file, Config.class);
			file.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid parameter found in config " + e);
        }
        
        // snakeyaml won't throw an error if a class attribute isn't defined in the config file,
        //   it will simply leave it null. So we need to ensure no config properties are
        //   null when they shouldn't be

        if (instance.shortStringMaxLength == -1 || instance.longStringMaxLength == -1){
            throw missingConfigException;
        }

        // List<Object> params = Arrays.asList(Config.shortStringMax, Config.longStringMax);
        // for (Object param : params){
        //     if (param == null){
        //         throw new IllegalStateException("All parameters must be set in config");
        //     }

        //     System.out.println(param);
        // }

    }
}
