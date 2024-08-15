package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.marker.Create;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class BookingDto {
    private Long id;
    @NotNull(groups = Create.class)
    @Future(groups = Create.class)
    private LocalDateTime start;
    @NotNull(groups = Create.class)
    @Future(groups = Create.class)
    private LocalDateTime end;
    @NotNull(groups = Create.class)
    private Long itemId;
    private Long bookerId;
    private Status status;
}
