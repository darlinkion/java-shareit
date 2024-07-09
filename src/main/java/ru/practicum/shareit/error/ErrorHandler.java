package ru.practicum.shareit.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.EmailValidationException;
import ru.practicum.shareit.exception.NotFoundException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse validationExceptionInController(final NotFoundException e) {
        log.error("Validation {}", e);
        return new ErrorResponse("error");
    }

    @ExceptionHandler(value = EmailValidationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse emailValidationException(final EmailValidationException e) {
        log.error("Такой email уже занят", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final Exception e) {
        log.error("Error\n", e);
        return new ErrorResponse("Необработанная ошибка, ");
    }
}
