package com.mkozachuk.terramon.service;

import com.mkozachuk.terramon.bot.TelegramBot;
import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.model.Terrarium;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MonitoringService implements Runnable {
    private Terrarium terrarium;
    private TerraDataService terraDataService;
    private TelegramBot bot;
    private String alertMessage;
    private PiService piService;


    @Autowired
    public MonitoringService(Terrarium terrarium, TerraDataService terraDataService, @Lazy TelegramBot bot, PiService piService) {
        this.terrarium = terrarium;
        this.terraDataService = terraDataService;
        this.bot = bot;
        this.piService = piService;
    }


    @Override
    public void run() {
        TerraData actualTerraData = statsCheck();
        terraDataService.save(actualTerraData);
        if (actualTerraData.getTemperature() >= terrarium.getTempMaxAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too HOT! Current temp is : " + actualTerraData.getTemperature() + " °C";
            log.warn("Too HOT! Current temp is : {}", actualTerraData.getTemperature());
        }

        if (actualTerraData.getTemperature() <= terrarium.getTempMinAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too Cold! Current temp is : " + actualTerraData.getTemperature() + " °C";
            log.warn("Too Cold! Current temp is : {}", actualTerraData.getTemperature());
        }

        if (actualTerraData.getHumidity() >= terrarium.getHumidityMaxAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too Wet! Current humidity is : " + actualTerraData.getHumidity() + " %";
            log.warn("Too Wet! Current humidity is : {}", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piService.startFan());
        }

        if (actualTerraData.getHumidity() <= terrarium.getHumidityMinAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too Dry! Current humidity is : " + actualTerraData.getHumidity() + " %";
            log.warn("Too Dry! Current humidity is : {}", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piService.stopFan());
        }

        if (actualTerraData.getHumidity() >= terrarium.getHumidityMaxOkLevel()) {
            log.warn("A bit too Wet! Starting the fan...\nCurrent humidity is : {} %", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piService.startFan());
        }

        if (actualTerraData.getHumidity() <= terrarium.getHumidityMinOkLevel()) {
            log.warn("A bit too Dry! Stopping the fan...\nCurrent humidity is : {} %", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piService.stopFan());
        }
        if (terrarium.isAlert()) {
            terrarium.setAlertMsg(alertMessage);
            bot.sendAlertToUser(alertMessage);
            terrarium.setAlert(false);
        }else {
            terrarium.setAlert(false);
            actualTerraData = null;
        }

    }

    private TerraData statsCheck() {
        terrarium.getTempSensor().setCurrentTemp(piService.checkFromTemp());
        terrarium.getHumiditySensor().setCurrentTemp(piService.checkFromHumidity().getOrDefault("temp", 0.0));
        terrarium.getHumiditySensor().setCurrentHumidity(piService.checkFromHumidity().getOrDefault("humidity", 0.0));

        TerraData actualTerraData = new TerraData();
        actualTerraData.setAddAt(new Date());
        actualTerraData.setTemperature(terrarium.getCurrentTemp());
        actualTerraData.setTemperatureFromHumiditySensor(terrarium.getCurrentTempFromHumiditySensor());
        actualTerraData.setHumidity(terrarium.getCurrentHumidity());
        log.info("Current TerraData {}", actualTerraData);
        return actualTerraData;
    }


}