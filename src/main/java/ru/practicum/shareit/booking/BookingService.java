package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.List;


public interface BookingService {
    BookingResponseDto createBooking(BookingDto bookingDto, Long id);

    BookingResponseDto updateBooking(Long bookingId, Boolean approved, Long userId);

    BookingResponseDto getBooking(Long userId, Long bookingId);

    List<BookingResponseDto> getBookingForState(Long userId, String state);

    List<BookingResponseDto> getBookingForOwner(Long userId, String state);
}
