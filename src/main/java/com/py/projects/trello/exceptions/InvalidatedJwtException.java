package com.py.projects.trello.exceptions;

public class InvalidatedJwtException extends RuntimeException {
    public InvalidatedJwtException(String errorMessage) {
        super(errorMessage);
    }
}
