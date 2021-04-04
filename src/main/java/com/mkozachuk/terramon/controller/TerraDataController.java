package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.Terrarium;
import com.mkozachuk.terramon.service.NoteService;
import com.mkozachuk.terramon.service.TerraDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TerraDataController {
    private Terrarium terrarium;
    private TerraDataService terraDataService;
    private NoteService noteService;

    @Autowired
    public TerraDataController(Terrarium terrarium, TerraDataService terraDataService, NoteService noteService) {
        this.terrarium = terrarium;
        this.terraDataService = terraDataService;
        this.noteService = noteService;
    }

    @GetMapping("/data")
    public String mainDataPage(Model model) {

        terraDataService.prepareDataLists();

        model.addAttribute("last100dateList", terraDataService.last100(terraDataService.getDateList()));
        model.addAttribute("last100midTempList", terraDataService.last100(terraDataService.getMidTempList()));
        model.addAttribute("last100humidityList", terraDataService.last100(terraDataService.getHumidityList()));
        model.addAttribute("last100tempFromTempSensorList", terraDataService.last100(terraDataService.getTempFromTempSensorList()));
        model.addAttribute("last100tempFromHumiditySensorList", terraDataService.last100(terraDataService.getTempFromHumiditySensorList()));

        model.addAttribute("dateList", terraDataService.getDateList());
        model.addAttribute("midTempList", terraDataService.getMidTempList());
        model.addAttribute("humidityList", terraDataService.getHumidityList());
        model.addAttribute("isAlert", terrarium.isAlert());
        model.addAttribute("alertMsg", terrarium.getAlertMsg());
        model.addAttribute("fanStatus", terrarium.getFanStatus());
        model.addAttribute("tempFromTempSensorList", terraDataService.getTempFromTempSensorList());
        model.addAttribute("tempFromHumiditySensorList", terraDataService.getTempFromHumiditySensorList());
        model.addAttribute("last3Notes", noteService.last3Notes());
        model.addAttribute("terrarium", terrarium);
        return "data";
    }
}
