package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryInMemory implements ItemRepository {
    private final HashMap<Integer, Item> items = new HashMap<>();
    private final HashMap<Integer, List<Integer>> owners = new HashMap<>();
    private int index = 0;

    @Override
    public Item createItem(ItemDto item, Integer userId) {
        Item tempItem = ItemMapper.toItemFromDto(item);
        tempItem.setOwnerId(userId);
        tempItem.setId(setIfForItem());
        items.put(index, tempItem);

        List<Integer> ownerItemsId = owners.get(userId);
        if (ownerItemsId == null) {
            ownerItemsId = new ArrayList<>();
        }
        ownerItemsId.add(index);
        owners.put(userId, ownerItemsId);
        return tempItem;
    }

    @Override
    public Item getItem(int id) {
        return items.get(id);
    }

    @Override
    public Item updateItem(ItemDto item, Integer userId, Integer itemId) {
        Item tempItem = getItem(itemId);
        if (tempItem.getOwnerId() != userId) {
            throw new NotFoundException("Предмет id=" + itemId + "не пренадлежит пользователю id=" + userId);
        }
        if (item.getName() != null) {
            tempItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            tempItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            tempItem.setAvailable(item.getAvailable());
        }
        items.put(itemId, tempItem);
        return tempItem;
    }

    @Override
    public List<Item> getAllItems(Integer userId) {
        List<Integer> ownerItemsId = owners.get(userId);
        if (ownerItemsId == null) {
            return List.of();
        }
        List<Item> tempItems = new ArrayList<>();
        for (Integer itemId : ownerItemsId) {
            tempItems.add(items.get(itemId));
        }
        return tempItems;
    }

    @Override
    public List<Item> searchItem(String str) {
        List<Item> foundItems = new ArrayList<>();
        if (str.isEmpty()) {
            return List.of();
        }

        for (Item item : items.values()) {
            if ((item.getName().toLowerCase().contains(str)
                    || item.getDescription().toLowerCase().contains(str))
                    && item.getAvailable()) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    private int setIfForItem() {
        return ++index;
    }
}
