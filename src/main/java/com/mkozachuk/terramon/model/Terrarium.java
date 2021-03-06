package com.mkozachuk.terramon.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class Terrarium {

    private int defaultFanSpeed;
    private double tempMin;
    private double tempMax;
    private double humidityMin;
    private double humidityMax;


    private Fan fan;
    private TempSensor tempSensor;
    private HumiditySensor humiditySensor;


    public void startFan(){
        fan.setOn(true);
        log.info("Fan has been started");
    }

    public void stopFan(){
        fan.setOn(false);
        log.info("Fan has been stopped");
    }

    public boolean getFanStatus(){
        log.info("Fan status : {}", fan.isOn());
        return fan.isOn();
    }

    public void setFanSpeed(int fanSpeed){
        fan.setRotationSpeedInPercent(fanSpeed);
        log.info("Fan Rotation speed : {}", fanSpeed);
    }

    public double getCurrentTemp(){
        return tempSensor.getCurrentTemp();
    }

    public double getCurrentHumidity(){
        return humiditySensor.getCurrentHumidity();
    }

    public void lowDownHumidity (){
        if(getCurrentHumidity() >= humidityMax) {
            if (!getFanStatus()) {
                startFan();
            }
        }
    }

    public void increaseHumidity (){
        if(getCurrentHumidity() <= humidityMin) {
            if (getFanStatus()) {
                stopFan();
            }
        }
    }



}
