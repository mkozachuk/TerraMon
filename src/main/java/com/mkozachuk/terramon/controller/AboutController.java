package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.service.AboutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class AboutController {

    private final AboutService aboutService;

    @Autowired
    public AboutController(AboutService aboutService){
        this.aboutService = aboutService;
    }

    @GetMapping
    public String about(Model model){
        model.addAttribute("version",aboutService.getVersion());
        model.addAttribute("botname", aboutService.getUsername());
        model.addAttribute("bottoken", aboutService.getToken());
        model.addAttribute("urlToUpdate", aboutService.getUrlToUpdate());
        model.addAttribute("isNewVersion",aboutService.checkForUpdate()) ;
        return "about";
    }


}
