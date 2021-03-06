package com.mkozachuk.terramon.model;

import lombok.Data;

@Data
public class Fan {

    private boolean on;
   private int rotationSpeedInPercent;

}
