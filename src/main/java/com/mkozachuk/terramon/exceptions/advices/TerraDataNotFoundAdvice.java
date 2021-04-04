package com.mkozachuk.terramon.exceptions.advices;

import com.mkozachuk.terramon.exceptions.TerraDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TerraDataNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TerraDataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String terraDataNotFoundHandler(TerraDataNotFoundException e) {
        return e.getMessage();
    }
}

