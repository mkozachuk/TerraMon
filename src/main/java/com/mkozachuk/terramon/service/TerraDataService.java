package com.mkozachuk.terramon.service;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.repository.TerraDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TerraDataService {
    private TerraDataRepository terraDataRepository;

    @Autowired
    public TerraDataService(TerraDataRepository terraDataRepository){
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
