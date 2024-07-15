package ru.practicum.shareit.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    String error;
    String message;
    String stacktrace;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
    }
}
