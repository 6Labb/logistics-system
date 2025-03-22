package com.sixlab.logistics.slack_ai_service.Messenger.exception;

public class SlackCircuitException extends RuntimeException {

    public SlackCircuitException(String message) {
        super(message);
    }

    public SlackCircuitException(String message, Throwable cause) {
        super(message, cause);
    }
}