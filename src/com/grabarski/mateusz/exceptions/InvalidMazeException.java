package com.grabarski.mateusz.exceptions;

/**
 * Created by Mateusz Grabarski on 21.05.2018.
 */
public class InvalidMazeException extends RuntimeException {

    public InvalidMazeException(String message) {
        super(message);
    }
}