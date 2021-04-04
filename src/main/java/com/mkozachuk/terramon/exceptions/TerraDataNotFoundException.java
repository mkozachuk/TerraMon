package com.mkozachuk.terramon.exceptions;

public class TerraDataNotFoundException extends RuntimeException{
    public TerraDataNotFoundException(Long id) {
        super("This TerraData doesn't exist id: " + id);
    }
}
