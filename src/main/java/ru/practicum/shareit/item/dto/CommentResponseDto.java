package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class CommentResponseDto {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
