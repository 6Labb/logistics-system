package com.sixlab.logistics.slack_ai_service.Messenger.exception;

public class GeminiRetryException extends RuntimeException {
    public GeminiRetryException(String message, Throwable cause) {
        super(message, cause);
    }
}