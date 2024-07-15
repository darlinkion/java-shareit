package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item createItem(ItemDto item, Long userId);

    Item getItem(Long id);

    Item updateItem(Item item);

    List<Item> getAllItems(Long userId);

    List<Item> searchItem(String str);
}
