package com.gmail.uprial.respawnlimiter.config;

@SuppressWarnings("ExceptionClassNameDoesntEndWithException")
class InternalConfigurationError extends RuntimeException {
    InternalConfigurationError(String message) {
        super(message);
    }
}