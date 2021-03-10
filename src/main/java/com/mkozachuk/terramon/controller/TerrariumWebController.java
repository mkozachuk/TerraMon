package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.model.Terrarium;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/data")
public class TerrariumWebController {
    private Terrarium terrarium;
    private TerraDataController terraDataController;

    @Autowired
    public TerrariumWebController(Terrarium terrarium, TerraDataController terraDataController){
        this.terrarium = terrarium;
        this.terraDataController = terraDataController;
    }

    @GetMapping("/test")
    public String testData(Model model) {
        for(int i = 0; i < 10; i++){
            TerraData terraData = new TerraData();
            LocalDate localDate = LocalDate.now().plusDays(i);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            terraData.setAddAt(date);
            terraData.setHumidity(50 + i);
            terraData.setTemperature(20 + i);
            terraData.setTemperatureFromHumiditySensor(21 + i);
            terraDataController.save(terraData);
            terraData = null;
        }
        return "redirect:/data";
    }

    @GetMapping()
    public String mainDataPage(Model model) {
        List<TerraData> allData = terraDataController.allTerraData();
        List<Date> dateList = new ArrayList<>();
        List<Double> midTempList = new ArrayList<>();
        List<Double> humidityList = new ArrayList<>();
        List<Double> tempFromTempSensorList = new ArrayList<>();
        List<Double> tempFromHumiditySensorList = new ArrayList<>();

        double currentTempFromTempSens = terrarium.getCurrentTemp();
        double currentTempFromHumiditySens = terrarium.getCurrentTempFromHumiditySensor();
        double currentHumidity = terrarium.getCurrentHumidity();

        for(TerraData t : allData){
            dateList.add(t.getAddAt());
            midTempList.add((t.getTemperature() + t.getTemperatureFromHumiditySensor())/2);
            humidityList.add(t.getHumidity());
            tempFromTempSensorList.add(t.getTemperature());
            tempFromHumiditySensorList.add(t.getTemperatureFromHumiditySensor());
        }

        model.addAttribute("dateList", dateList);
        model.addAttribute("midTempList", midTempList);
        model.addAttribute("humidityList", humidityList);
        model.addAttribute("isAlert", terrarium.isAlert());
        model.addAttribute("fanStatus",terrarium.getFanStatus());
        model.addAttribute("tempFromTempSensorList", tempFromTempSensorList);
        model.addAttribute("tempFromHumiditySensorList", tempFromHumiditySensorList);

        model.addAttribute("currentTempFromTempSens", currentTempFromTempSens);
        model.addAttribute("currentTempFromHumiditySens", terrarium.getCurrentTempFromHumiditySensor());
        model.addAttribute("currentHumidity", currentHumidity);
        return "data";
    }

    @GetMapping("/settings")
    public String settingsPage(Model model) {
        model.addAttribute("terrarium", this.terrarium);
        return "settings";
    }

    @PostMapping("/settings")
    public String processSettings(Terrarium terrarium) {
        this.terrarium = terrarium;
        return "redirect:/data/settings";
    }




}
