package ru.practicum.shareit.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ItemRequest {
    public LocalDateTime created;
    private Integer id;
    private String description;
    private Integer requestorId;
}
