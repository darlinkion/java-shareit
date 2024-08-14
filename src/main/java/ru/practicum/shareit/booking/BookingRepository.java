package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartTimeDesc(Long bookerId, Sort sort);

    List<Booking> findByBookerIdAndStatus(Long bookerId, Status status, Sort sort);

    @Query("select b " +
            "from Booking b " +
            "where b.booker.id = ?1 and b.startTime  < ?2 and b.endTime  > ?2 ")
    List<Booking> findByBookerIdCurrent(Long bookerId, LocalDateTime now, Sort sort);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.booker.id = ?1 and  b.endTime > ?2 ")
    List<Booking> findByBookerIdFuture(Long bookerId, LocalDateTime localDateTime, Sort sort);

    List<Booking> findByBookerIdAndEndTimeBefore(Long bookerId, LocalDateTime now, Sort sort);

    Optional<Booking> findByBookerAndItem(User booker, Item item);

    Booking findFirstByItemIdAndStartTimeBefore(Long itemId, LocalDateTime now, Sort sort);

    Booking findFirstByItemIdAndStartTimeAfterAndStatus(Long itemId, LocalDateTime now, Sort sort, Status status);
}