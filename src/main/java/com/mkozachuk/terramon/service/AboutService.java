package com.mkozachuk.terramon.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AboutService {

    @Value("${terramon.version}")
    @Setter
    private String version;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Value("${terramon.urlToUpdate}")
    private String urlToUpdate;

    public boolean checkForUpdate(){

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(urlToUpdate, String.class);
        JSONObject obj = new JSONObject(response);
        String latestReleaseVersion =  obj.getString("name");

        if(version.equals(latestReleaseVersion)){
            return false;
        }else {
            log.info("New version is available\nYour version : {}\nAvailable version : {}",version,latestReleaseVersion);
            return true;
        }
    }

    public String getVersion() {
        return version;
    }

    public String getUrlToUpdate() {
        return urlToUpdate;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
