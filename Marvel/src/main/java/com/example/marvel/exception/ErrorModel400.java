package com.example.marvel.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorModel400 extends Throwable {
    private final List<ErrorDetails> errors;

    public ErrorModel400 (List<ErrorDetails> errors){
        this.errors = errors;
    }
}
