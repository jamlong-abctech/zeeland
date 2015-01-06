package com.abctech.zeeland.exception;

/**
 * Something exceptional has happened during execution.
 */
public class ZeelandException extends RuntimeException {
    public ZeelandException(String message) {
        super(message);
    }

    public ZeelandException(String message, Throwable cause) {
        super(message, cause);
    }
}
