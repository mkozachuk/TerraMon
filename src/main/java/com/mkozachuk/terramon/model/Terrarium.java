package com.mkozachuk.terramon.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private String alertMsg;
    private boolean monitorOn;
    @Value("${terrarium.defaultMonitorDaley}")
    private long defaultMonitorDaley; //180000 = 3min


    private Fan fan = new Fan();
    private TempSensor tempSensor = new TempSensor();
    private HumiditySensor humiditySensor = new HumiditySensor();


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

    public void defaultValues(){
        defaultFanSpeed = 100;
        tempMinAlert = 22;
        tempMaxAlert = 28;
        humidityMinAlert = 40;
        humidityMaxAlert = 65;
        humidityMinOkLevel = 45;
        humidityMaxOkLevel = 60;
    }



}
