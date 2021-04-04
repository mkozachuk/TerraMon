package com.mkozachuk.terramon.exceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {
        super("This Note doesn't exist id: " + id);
    }
}
