package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class CommentDto {
    @NotBlank
    private String text;
    private Long authorId;
    private Long itemId;
}