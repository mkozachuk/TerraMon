package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.Terrarium;
import com.mkozachuk.terramon.service.AboutService;
import com.mkozachuk.terramon.service.NoteService;
import com.mkozachuk.terramon.service.TerraDataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SettingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Terrarium terrarium;

    @MockBean
    private AboutService aboutService;

    @MockBean
    private NoteService noteService;

    @MockBean
    private TerraDataService terraDataService;

    @Test
    void settingsPageTest() throws Exception {
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"))
                .andExpect(content().string(
                        containsString("Settings TerraMon")));
    }

}