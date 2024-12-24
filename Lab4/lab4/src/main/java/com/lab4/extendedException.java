package com.lab4;

public class extendedException extends Exception {
    public extendedException(String message) {
        super(message);
    }

    public extendedException(String message, Throwable reason) {
        super(message, reason);
    }
}
