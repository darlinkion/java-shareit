package ru.practicum.shareit.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.ItemOwnerException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongUserException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse validationExceptionInController(final NotFoundException e) {
        log.error("Validation {}", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse emailValidationException(final DataIntegrityViolationException e) {
        log.error("Такой email уже занят", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final Exception e) {
        log.error("Error\n", e);
        return new ErrorResponse("Необработанная ошибка", e.getMessage());
    }

    @ExceptionHandler(value = BookingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse bookingExceptionInService(final BookingException e) {
        log.error("Booking {}", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = ItemOwnerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse itemOwnerExceptionInService(final ItemOwnerException e) {
        log.error("ItemOwner {}", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = WrongUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse wrongUserExceptionInService(final WrongUserException e) {
        log.error("Wrong user {}", e);
        return new ErrorResponse(e.getMessage());
    }
}
