package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto item, Long userId);

    ItemDto getItem(Long id);

    ItemDto updateItem(ItemDto item, Long userId);

    List<ItemDto> getAllItems(Long userId);

    List<ItemDto> searchItem(String str);
}
