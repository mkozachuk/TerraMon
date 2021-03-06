package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.Terrarium;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TerrariumController {
    private Terrarium terrarium;

    public TerrariumController(Terrarium terrarium){
        this.terrarium = terrarium;
    }


}
