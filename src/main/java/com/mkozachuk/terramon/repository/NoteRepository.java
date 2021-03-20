package com.mkozachuk.terramon.repository;

import com.mkozachuk.terramon.model.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findTop3ByOrderByAddAtDesc();
}
