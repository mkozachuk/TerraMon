package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.Note;
import com.mkozachuk.terramon.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class NoteController {
    private NoteRepository noteRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void save(Note note){
        noteRepository.save(note);
        log.info("Note has been saved : {}", note);
    }

    public List<Note> allNotes(){
        return (List<Note>) noteRepository.findAll();
    }

    public List<Note> last3Notes(){
        return noteRepository.findTop3ByOrderByAddAtDesc();
    }
}
