package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.ItemOwnerException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongUserException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final Sort start = Sort.by(Sort.Direction.DESC, "startTime");

    @Transactional
    @Override
    public BookingResponseDto createBooking(BookingDto bookingDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Нет такого пользователя"));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(
                () -> new NotFoundException("Нет такого предмета"));

        if (userId.equals(item.getOwner().getId())) {
            throw new ItemOwnerException("Невозможно бронировать свою вещб");
        }

        if (!item.getAvailable() ||
                bookingDto.getStart().isEqual(bookingDto.getEnd()) ||
                bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new BookingException("Невозможно забронировать данную вещь");
        }
        Booking booking = BookingMapper.toBooking(bookingDto, item, user);
        Booking tempBooking = bookingRepository.save(booking);
        return BookingMapper.toBookingResponseDto(tempBooking);
    }

    @Transactional
    @Override
    public BookingResponseDto updateBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Нет такого бронирования"));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new WrongUserException("Нет такого пользователя"));
        Item item = itemRepository.findById(booking.getItem().getId()).orElseThrow(
                () -> new NotFoundException("Нет такого предмета"));

        Status status;
        if (approved) {
            status = Status.APPROVED;
        } else {
            status = Status.REJECTED;
        }

        if (status == booking.getStatus()) {
            throw new BookingException("Статус бронирования уже изменен");
        }

        if ((item.getOwner().getId())!= userId) {
            throw new NotFoundException("Только владелиц вещи может изменить статус");
        }

        booking.setStatus(status);
        return BookingMapper.toBookingResponseDto(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    @Override
    public BookingResponseDto getBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Нет такого бранирования"));
        Item item = booking.getItem();
        if ((!userId.equals(booking.getBooker().getId())) && (!userId.equals(item.getOwner().getId()))){
            throw new BookingException("Бронирование может получит только пользователь создавший его");
        }
        return BookingMapper.toBookingResponseDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingResponseDto> getBookingForState(Long userId, String state) {
        State newState = State.valueOf(state);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new WrongUserException("Нет такого пользователя"));
        List<Booking> bookings;
        switch (newState) {
            case ALL:
                bookings = bookingRepository.findByBookerIdOrderByStartTimeDesc(userId, start);
                break;
            case WAITING:
                bookings = bookingRepository.findByBookerIdAndStatus(userId, Status.WAITING, start);
                break;
            case REJECTED:
                bookings = bookingRepository.findByBookerIdAndStatus(userId, Status.REJECTED, start);
                break;
            case CURRENT:
                bookings = bookingRepository.findByBookerIdCurrent(userId, LocalDateTime.now(), start);
                break;
            case FUTURE:
                bookings = bookingRepository.findByBookerIdFuture(userId, LocalDateTime.now(), start);
                break;
            case PAST:
                bookings = bookingRepository.findByBookerIdAndEndTimeBefore(userId, LocalDateTime.now(), start);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + newState);
        }
        return bookings.stream()
                .map(BookingMapper::toBookingResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingResponseDto> getBookingForOwner(Long userId, String state) {
        State newState = State.valueOf(state);
        if (newState == null) {
            throw new RuntimeException("Нет такого состояния");
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new WrongUserException("Нет такого пользователя"));
        List<Item> items = itemRepository.findItemByOwner_Id(userId);

        if (items.isEmpty()) {
            throw new BookingException("У пользователя нет вещей");
        }

        return new ArrayList<>();
    }
}
