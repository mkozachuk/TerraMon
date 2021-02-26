package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.repository.TerraDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class TerraDataController {
    private TerraDataRepository terraDataRepository;

    public TerraDataController(TerraDataRepository terraDataRepository){
        this.terraDataRepository = terraDataRepository;
    }

    public TerraData save (TerraData terraData){
        terraDataRepository.save(terraData);
        log.info("TerraData has been saved {}", terraData);
        return terraData;
    }

    public List<TerraData> allTerraData(){
        return (List<TerraData>) terraDataRepository.findAll();

    }
}
