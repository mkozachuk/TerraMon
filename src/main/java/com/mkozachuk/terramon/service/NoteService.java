package com.mkozachuk.terramon.service;

import com.mkozachuk.terramon.model.Note;
import com.mkozachuk.terramon.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void save(Note note){
        noteRepository.save(note);
        log.info("Note has been saved : {}", note);
    }

    public List<Note> allNotes(){
        return noteRepository.findAllByOrderByAddAtDesc();
    }

    public List<Note> last3Notes(){
        return noteRepository.findTop3ByOrderByAddAtDesc();
    }

    public void addDefaultNotes(){
        Date today = new Date();
        if(last3Notes().size() < 3){
            Note note = new Note();
            note.setTitle("Order TerraMon");
            note.setText("Order Smart Terrarium Monitoring for my Terraium");
            note.setAddAt(new Date(today.getTime() - 76 * 60 * 60 * 1000));
            save(note);

            note = new Note();
            note.setTitle("TerraMon on its way");
            note.setText("The package is on its way to me, the pets will be happy");
            note.setAddAt(new Date(today.getTime() - 48 * 60 * 60 * 1000));
            save(note);

            note = new Note();
            note.setTitle("First run");
            note.setText("Today I had setup TerraMon in my Terrarium :)");
            note.setAddAt(today);
            save(note);
            note = null;
        }
    }
}
