package com.mkozachuk.terramon.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Value("${terramon.version}")
    private String version;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @GetMapping
    public String addVersion(Model model){
        model.addAttribute("version",version);
        model.addAttribute("botname", username);
        model.addAttribute("bottoken", token);
        return "home";
    }
}
