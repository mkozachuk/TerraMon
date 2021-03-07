package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.model.Terrarium;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            Date date = new Date();
            date.setDate(i);
            terraData.setAddAt(date);
            terraData.setHumidity(50 + i);
            terraData.setTemperature(20 + i);
            terraDataController.save(terraData);
            terraData = null;
        }
        return "redirect:/data";
    }

    @GetMapping()
    public String mainDataPage(Model model) {
        List<TerraData> allData = terraDataController.allTerraData();
        List<Date> dateList = new ArrayList<>();
        List<Double> tempList = new ArrayList<>();
        List<Double> humidityList = new ArrayList<>();

        for(TerraData t : allData){
            dateList.add(t.getAddAt());
            tempList.add(t.getTemperature());
            humidityList.add(t.getHumidity());
        }

        model.addAttribute("dateList", dateList);
        model.addAttribute("tempList", tempList);
        model.addAttribute("humidityList", humidityList);
        model.addAttribute("isAlert", terrarium.isAlert());
        model.addAttribute("fanStatus",terrarium.getFanStatus());
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
