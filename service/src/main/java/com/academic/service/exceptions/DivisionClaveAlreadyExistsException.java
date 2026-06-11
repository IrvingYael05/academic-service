package com.academic.service.exceptions;

public class DivisionClaveAlreadyExistsException extends RuntimeException {
    
    public DivisionClaveAlreadyExistsException(String clave) {
        super("Ya existe una división con la clave: " + clave);
    }
}