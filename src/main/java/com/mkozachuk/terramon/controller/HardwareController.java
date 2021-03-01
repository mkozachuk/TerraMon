package com.mkozachuk.terramon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class HardwareController {

    boolean isFanOn;

    public float getTemp(){
        float temp = 24.4f;
        //todo logic here
        log.info("Retrieved temp is {}", temp);
        return temp;
    }

    public float getHumidity(){
        float hum = 75.5f;
        //todo logic here
        log.info("Retrieved humidity is {}", hum);
        return hum;
    }

    public void startFan(){
        log.info("Fan has been start");
        //todo
    }
    public void stopFan(){
        log.info("Fan has been stop");
        //todo
    }

    public boolean fanStatus(){
        log.info("Fan status : {}", isFanOn);
        //todo
        return isFanOn;
    }

}
