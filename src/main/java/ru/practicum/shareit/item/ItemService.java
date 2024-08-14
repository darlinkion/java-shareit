package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto item, Long userId);

    ItemResponseDto getItem(Long itemId, Long userId);

    ItemDto updateItem(ItemDto item, Long userId);

    List<ItemDto> getAllItems(Long userId);

    List<ItemDto> searchItem(String str);

    CommentResponseDto createComment(CommentDto comment);
}
