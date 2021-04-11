package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.Note;
import com.mkozachuk.terramon.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class NotesController {

    private final NoteService noteService;

    @Autowired
    public NotesController(NoteService noteService){
        this.noteService = noteService;
    }

    @ModelAttribute
    public Note newNote() {
        return new Note();
    }

    @GetMapping("/notes")
    public String notesPage(Model model) {
        noteService.addDefaultNotes();
        model.addAttribute("note", newNote());
        model.addAttribute("allNotes", noteService.allNotes());
        return "notes";
    }

    @PostMapping("/notes/save")
    public String processNewNote(Note note) {
        note.setAddAt(new Date());
        noteService.save(note);
        return "redirect:/notes";
    }
}
