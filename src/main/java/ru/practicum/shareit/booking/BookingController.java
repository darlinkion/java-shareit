package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.marker.Create;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto create(@Validated(Create.class) @RequestBody BookingDto bookingDto,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        BookingResponseDto newBooking = bookingService.createBooking(bookingDto, userId);
        log.info("Booking created: " + newBooking);
        return newBooking;
    }

    @PatchMapping("{bookingId}")
    public BookingResponseDto updateStatus(@PathVariable Long bookingId,
                                           @RequestParam Boolean approved,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        BookingResponseDto bookingDto = bookingService.updateBooking(bookingId, approved, userId);
        log.info("Booking update: " + bookingDto);
        return bookingDto;
    }

    @GetMapping("{bookingId}")
    public BookingResponseDto getBooking(@PathVariable Long bookingId,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        BookingResponseDto bookingResponseDto = bookingService.getBooking(userId, bookingId);
        log.info("Get booking: " + bookingResponseDto);
        return bookingResponseDto;
    }

    @GetMapping
    public List<BookingResponseDto> getBookingsForState(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                        @RequestParam(defaultValue = "ALL") String state) {
        List<BookingResponseDto> bookingResponseDtoList = bookingService.getBookingForState(userId, state);
        log.info("Get bookings for state: " + bookingResponseDtoList);
        return bookingResponseDtoList;
    }

    @GetMapping("/owner")
    public List<BookingResponseDto> getBookingForOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                       @RequestParam(defaultValue = "ALL") String state) {
        List<BookingResponseDto> bookingResponseDtoList = bookingService.getBookingForOwner(userId, state);
        log.info("Get bookings for owner: " + bookingResponseDtoList);
        return bookingResponseDtoList;
    }

}
