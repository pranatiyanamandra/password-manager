package com.py.projects.trello.controllers;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InvalidatedTokenCollection {
    private Set<String> invalidatedTokens = new HashSet<>();

    public InvalidatedTokenCollection() {
    }

    public Set<String> getInvalidatedTokens() {
        return invalidatedTokens;
    }

    public void addToken(String invalidatedToken) {
        this.invalidatedTokens.add(invalidatedToken);
    }

    public boolean hasToken(String token) {
        return this.invalidatedTokens.contains(token);
    }
}
