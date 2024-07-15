package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryInMemory implements ItemRepository {
    private final HashMap<Long, Item> items = new HashMap<>();
    private final HashMap<Long, List<Long>> owners = new HashMap<>();
    private Long index = 0L;

    @Override
    public Item createItem(ItemDto item, Long userId) {
        Item tempItem = ItemMapper.toItemFromDto(item);
        tempItem.setOwnerId(userId);
        tempItem.setId(setIfForItem());
        items.put(index, tempItem);

        owners.computeIfAbsent(userId, k -> new ArrayList<>()).add(index);
        return tempItem;
    }

    @Override
    public Item getItem(Long id) {
        Item item = items.get(id);
        if (item == null) {
            return null;
        }
        return Item.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .ownerId(item.getOwnerId())
                .request(item.getRequest())
                .build();
    }

    @Override
    public Item updateItem(Item item) {

        items.put(item.getId(), item);
        return item;
    }

    @Override
    public List<Item> getAllItems(Long userId) {
        List<Long> ownerItemsId = owners.get(userId);
        if (ownerItemsId == null) {
            return List.of();
        }
        return ownerItemsId.stream()
                .map(items::get)
                .toList();
    }

    @Override
    public List<Item> searchItem(String str) {
        List<Item> foundItems = new ArrayList<>();
        if (str.isEmpty()) {
            return foundItems;
        }

        return items.values().stream()
                .filter(item -> (item.getName().toLowerCase().contains(str.toLowerCase())
                        || item.getDescription().toLowerCase().contains(str.toLowerCase()))
                        && item.getAvailable())
                .toList();
    }

    private Long setIfForItem() {
        return ++index;
    }
}
