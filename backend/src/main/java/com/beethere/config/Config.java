package com.beethere.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "beethere")
@Validated
public class Config {
    
    @Min(value = 1, message = "shortStringMaxLength must be positive")
    private int shortStringMaxLength;
    
    @Min(value = 1, message = "longStringMaxLength must be positive")
    private int longStringMaxLength;
    
    @NotEmpty(message = "logConfigPath cannot be empty")
    private String logConfigPath;

    @Min(value = 1, message = "maxSeatCount must be positive")
    public int maxSeatCount;
    
    public int getMaxSeatCount() {
        return maxSeatCount;
    }

    public void setMaxSeatCount(int maxSeatCount) {
        this.maxSeatCount = maxSeatCount;
    }

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
}