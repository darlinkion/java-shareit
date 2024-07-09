package ru.practicum.shareit.booking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    private Integer id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer itemId;
    private Integer bookerId;
    private Status status;
}
