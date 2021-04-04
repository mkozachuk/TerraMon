package com.mkozachuk.terramon.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Fan {
    private boolean on;
    private int rotationSpeedInPercent;
}
