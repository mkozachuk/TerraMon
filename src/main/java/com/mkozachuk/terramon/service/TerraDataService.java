package com.mkozachuk.terramon.service;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.repository.TerraDataRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TerraDataService {

    private TerraDataRepository terraDataRepository;

    @Autowired
    public TerraDataService(TerraDataRepository terraDataRepository) {
        this.terraDataRepository = terraDataRepository;
    }

    @Getter
    private List<TerraData> allData;
    @Getter
    private List<Date> dateList = new ArrayList<>();
    @Getter
    private List<Double> midTempList = new ArrayList<>();
    @Getter
    private List<Double> humidityList = new ArrayList<>();
    @Getter
    private List<Double> tempFromTempSensorList = new ArrayList<>();
    @Getter
    private List<Double> tempFromHumiditySensorList = new ArrayList<>();

    private boolean moreThan100;

    public TerraData save(TerraData terraData) {
        terraDataRepository.save(terraData);
        log.info("TerraData has been saved {}", terraData);
        return terraData;
    }

    public List<TerraData> allTerraData() {
        return (List<TerraData>) terraDataRepository.findAll();

    }

    public void prepareDataLists() {
        allData = allTerraData();

        if(allData.size() > 100){
            moreThan100 = true;
        }
        for (TerraData t : allData) {
            dateList.add(t.getAddAt());
            midTempList.add((t.getTemperature() + t.getTemperatureFromHumiditySensor()) / 2);
            humidityList.add(t.getHumidity());
            tempFromTempSensorList.add(t.getTemperature());
            tempFromHumiditySensorList.add(t.getTemperatureFromHumiditySensor());
        }

    }

    public List last100(List anyList){
        if(moreThan100){
            return anyList.subList(anyList.size() - 100, anyList.size() - 1);
        }else {
            return anyList;
        }
    }
}
