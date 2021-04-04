package com.mkozachuk.terramon.api;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.service.TerraDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/data")
public class TerrariumDataController {
    private TerraDataService terraDataService;

    @Autowired
    public TerrariumDataController(TerraDataService terraDataService){
        this.terraDataService = terraDataService;
    }

    @GetMapping
    List<TerraData> findAll() {
        return terraDataService.allTerraData();
    }

    @GetMapping(value = "/{id}")
    TerraData findById(@PathVariable Long id) {
        return terraDataService.findById(id);
    }

    @PostMapping
    TerraData newNote(@RequestBody TerraData newTerraData) {
        return terraDataService.save(newTerraData);
    }

    @PutMapping("/{id}")
    TerraData replaceTerraData(@RequestBody TerraData newTerradata, @PathVariable Long id) {
        return terraDataService.replaysTerraData(newTerradata,id);
    }

    @DeleteMapping("/{id}")
    void deleteNote(@PathVariable Long id) {
        terraDataService.deleteById(id);
    }
}
