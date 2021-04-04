package com.mkozachuk.terramon.api;

import com.mkozachuk.terramon.model.Note;
import com.mkozachuk.terramon.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    private NoteService noteService;

    @Autowired
    NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping
    List<Note> findAll() {
        return noteService.allNotes();
    }

    @GetMapping(value = "/{id}")
    Note findById(@PathVariable Long id) {
        return noteService.findById(id);
    }

    @PostMapping
    Note newNote(@RequestBody Note newNote) {
        return noteService.save(newNote);
    }

    @PutMapping("/{id}")
    Note replaceNote(@RequestBody Note newNote, @PathVariable Long id) {
        return noteService.replaysNote(newNote,id);
    }

    @DeleteMapping("/{id}")
    void deleteNote(@PathVariable Long id) {
        noteService.deleteById(id);
    }
}
