package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStartTime())
                .end(booking.getEndTime())
                .itemId(booking.getItem().getId())
                .bookerId(booking.getBooker().getId())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toBooking(BookingDto bookingDto, Item item, User user) {
        return Booking.builder()
                .id(bookingDto.getId())
                .startTime(bookingDto.getStart())
                .endTime(bookingDto.getEnd())
                .item(item)
                .booker(user)
                .status(Status.WAITING)
                .build();
    }

    public static BookingResponseDto toBookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .start(booking.getStartTime())
                .end(booking.getEndTime())
                .item(ItemMapper.toItemDto(booking.getItem()))
                .booker(UserMapper.toUserDto(booking.getBooker()))
                .status(booking.getStatus())
                .build();
    }
}
