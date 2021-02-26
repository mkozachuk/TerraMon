package com.mkozachuk.terramon.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

//@Entity
@Data
public class TerraData {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // in C
    private float temperature;
    //in %
    private float humidity;

    private Date addAt;
}
