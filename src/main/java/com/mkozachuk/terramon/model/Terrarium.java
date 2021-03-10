package com.mkozachuk.terramon.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class Terrarium {

    private int defaultFanSpeed;
    private double tempMinAlert;
    private double tempMaxAlert;
    private double humidityMinAlert;
    private double humidityMaxAlert;
    private double humidityMinOkLevel;
    private double humidityMaxOkLevel;

    private boolean alert;
    private boolean monitorOn;

    private long defaultMonitorDaley = 300000;


    private Fan fan = new Fan();
    private TempSensor tempSensor = new TempSensor();
    private HumiditySensor humiditySensor = new HumiditySensor();


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

    public double getCurrentTempFromHumiditySensor(){
        return humiditySensor.getCurrentTemp();
    }

    public double getCurrentHumidity(){
        return humiditySensor.getCurrentHumidity();
    }

    public void lowDownHumidity (){
        if(getCurrentHumidity() >= humidityMaxAlert) {
            if (!getFanStatus()) {
                startFan();
            }
        }
    }

    public void increaseHumidity (){
        if(getCurrentHumidity() <= humidityMinAlert) {
            if (getFanStatus()) {
                stopFan();
            }
        }
    }



}
