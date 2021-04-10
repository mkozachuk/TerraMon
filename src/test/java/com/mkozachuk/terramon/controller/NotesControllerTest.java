package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.Note;
import com.mkozachuk.terramon.model.Terrarium;
import com.mkozachuk.terramon.service.AboutService;
import com.mkozachuk.terramon.service.NoteService;
import com.mkozachuk.terramon.service.TerraDataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class NotesControllerTest {

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

    private Note note;

    @Test
    void notesPageTest() throws Exception {
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes"))
                .andExpect(content().string(
                        containsString("Notes TerraMon")));
    }

    @Test
    void processNewNoteTest() throws Exception {
        when(noteService.save(note))
                .thenReturn(note);

        mockMvc.perform(post("/notes/save")
                .content("title=Test+Note&text=Test+text")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/notes"));

    }
}