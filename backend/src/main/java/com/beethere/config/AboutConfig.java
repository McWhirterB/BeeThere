package com.beethere.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "about")
@Validated

/**
 * Configuration class for About section details.
 */
public class AboutConfig {

    private String missionStatement;
    private Map<String, String> developmentTeam;
    private String copyright;
    private String contactUs;

    public String getMissionStatement() {
        return missionStatement;
    }

    public void setMissionStatement(String missionStatement) {
        this.missionStatement = missionStatement;
    }

    public Map<String, String> getDevelopmentTeam() {
        return developmentTeam;
    }

    public void setDevelopmentTeam(Map<String, String> developmentTeam) {
        this.developmentTeam = developmentTeam;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }
}
