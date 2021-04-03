package com.mkozachuk.terramon.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class TerraData implements Exportable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // in C
    private double temperature;
    private double temperatureFromHumiditySensor;
    //in %
    private double humidity;

    private Date addAt;

    @Override
    public String[] getTableHeaders() {
        return new String[]{"ID", "Temperature1", "Temperature2", "Humidity", "Add Date"};
    }

    @Override
    public String[] getNameMapping() {
        return new String[]{"id", "temperature", "temperatureFromHumiditySensor", "humidity", "addAt"};
    }
}
