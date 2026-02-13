package com.example.demo.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {

        super("la risorsa con id " + id + " non è stata trovata!");
    }
}
