package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.bot.TelegramBot;
import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.model.Terrarium;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Slf4j
@Controller
public class MonitorTask implements Runnable {
    private Terrarium terrarium;
    private TerraDataController terraDataController;
    private TelegramBot bot;
    private String alertMessage;
    private PiController piController;


    @Autowired
    public MonitorTask(Terrarium terrarium, TerraDataController terraDataController, @Lazy TelegramBot bot, PiController piController) {
        this.terrarium = terrarium;
        this.terraDataController = terraDataController;
        this.bot = bot;
        this.piController = piController;
    }


    @Override
    public void run() {
        TerraData actualTerraData = statsCheckTask();
        terraDataController.save(actualTerraData);
        if (actualTerraData.getTemperature() >= terrarium.getTempMaxAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too HOT! Current temp is : " + actualTerraData.getTemperature();
            log.warn("Too HOT! Current temp is : {}", actualTerraData.getTemperature());
        }

        if (actualTerraData.getTemperature() <= terrarium.getTempMinAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too Cold! Current temp is : " + actualTerraData.getTemperature();
            log.warn("Too Cold! Current temp is : {}", actualTerraData.getTemperature());
        }

        if (actualTerraData.getHumidity() >= terrarium.getHumidityMaxAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too Wet! Current humidity is : " + actualTerraData.getHumidity();
            log.warn("Too Wet! Current humidity is : {}", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piController.startFan());
        }

        if (actualTerraData.getHumidity() <= terrarium.getHumidityMinAlert()) {
            terrarium.setAlert(true);
            alertMessage = "Too Dry! Current humidity is : " + actualTerraData.getHumidity();
            log.warn("Too Dry! Current humidity is : {}", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piController.stopFan());
        }

        if (actualTerraData.getHumidity() >= terrarium.getHumidityMaxOkLevel()) {
            log.warn("A bit too Wet! Starting the fan...\nCurrent humidity is : {}", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piController.startFan());
        }

        if (actualTerraData.getHumidity() <= terrarium.getHumidityMinOkLevel()) {
            log.warn("A bit too Dry! Stopping the fan...\nCurrent humidity is : {}", actualTerraData.getHumidity());
            terrarium.getFan().setOn(piController.stopFan());
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

    private TerraData statsCheckTask() {
        terrarium.getTempSensor().setCurrentTemp(piController.checkFromTemp());
        terrarium.getHumiditySensor().setCurrentTemp(piController.checkFromHumidity().getOrDefault("temp", 0.0));
        terrarium.getHumiditySensor().setCurrentHumidity(piController.checkFromHumidity().getOrDefault("humidity", 0.0));

        TerraData actualTerraData = new TerraData();
        actualTerraData.setAddAt(new Date());
        actualTerraData.setTemperature(terrarium.getCurrentTemp());
        actualTerraData.setTemperatureFromHumiditySensor(terrarium.getCurrentTempFromHumiditySensor());
        actualTerraData.setHumidity(terrarium.getCurrentHumidity());
        log.info("Current TerraData {}", actualTerraData);
        return actualTerraData;
    }

}
