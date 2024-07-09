package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item createItem(ItemDto item, Integer userId);

    Item getItem(int id);

    Item updateItem(ItemDto item, Integer userId, Integer itemId);

    List<Item> getAllItems(Integer userId);

    List<Item> searchItem(String str);
}
