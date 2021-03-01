package com.mkozachuk.terramon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("data")
public class WebController {

    @GetMapping()
    public String data(){
        return "data";
    }


}
