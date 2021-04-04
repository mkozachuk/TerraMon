package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.Terrarium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {

    private Terrarium terrarium;

    @Autowired
    public SettingsController(Terrarium terrarium){
        this.terrarium = terrarium;
    }

    @GetMapping("/settings")
    public String settingsPage(Model model) {
        model.addAttribute("terrarium", this.terrarium);
        return "settings";
    }

    @PostMapping("/settings/save")
    public String processSettings(Terrarium terrarium) {
        this.terrarium.setHumidityMinAlert(terrarium.getHumidityMinAlert());
        this.terrarium.setTempMaxAlert(terrarium.getTempMaxAlert());
        this.terrarium.setHumidityMaxAlert(terrarium.getHumidityMaxAlert());
        this.terrarium.setTempMinAlert(terrarium.getTempMinAlert());
        return "settings";
    }
}
