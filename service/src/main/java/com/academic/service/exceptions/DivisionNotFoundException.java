package com.academic.service.exceptions;

public class DivisionNotFoundException extends RuntimeException {
    
    public DivisionNotFoundException(Long id) {
        super("División con ID " + id + " no encontrada");
    }
    
    public DivisionNotFoundException(String mensaje) {
        super(mensaje);
    }
}
