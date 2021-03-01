package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.repository.TerraDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class TerraDataController {
    private TerraDataRepository terraDataRepository;
    private HardwareController hardwareController;

    private float humidityMax;
    private float humidityMin;

    private float maxTemp;
    private float minTemp;

//    @Autowired
    public TerraDataController(TerraDataRepository terraDataRepository, HardwareController hardwareController){
        this.terraDataRepository = terraDataRepository;
        this.hardwareController = hardwareController;
    }

    public TerraData save (TerraData terraData){
        terraDataRepository.save(terraData);
        log.info("TerraData has been saved {}", terraData);
        return terraData;
    }

    public List<TerraData> allTerraData(){
        return (List<TerraData>) terraDataRepository.findAll();

    }

    public void lowDownHumidity (boolean isFanOn, TerraData terraData){
        if(terraData.getHumidity() >= humidityMax) {
            if (!isFanOn) {
                hardwareController.startFan();
            }
        }
    }

    public void increaseHumidity (boolean isFanOn, TerraData terraData){
        if(terraData.getHumidity() <= humidityMin) {
            if (isFanOn) {
                hardwareController.stopFan();
            }
        }
    }




}
