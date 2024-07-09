package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.request.ItemRequest;

@ToString
@Getter
@Setter
@Builder
public class Item {
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Boolean available;
    private Integer ownerId;
    private ItemRequest request;
}
