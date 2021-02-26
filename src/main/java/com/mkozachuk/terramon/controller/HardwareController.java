package com.mkozachuk.terramon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class HardwareController {
    public float getTemp(){
        float temp = 24.4f;
        //todo logic here
        return temp;
    }

    public float getHumidity(){
        float hum = 75.5f;
        //todo logic here
        return hum;
    }
}
