package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public Item createItem(ItemDto item, Integer userId) {
        userService.getUser(userId);
        return itemRepository.createItem(item, userId);
    }

    @Override
    public Item getItem(int id) {
        Optional<Item> item = Optional.ofNullable(itemRepository.getItem(id));
        if (item.isEmpty()) {
            throw new NotFoundException("Нет такого предмета по id:" + id);
        }
        return item.get();

    }

    @Override
    public Item updateItem(ItemDto item, Integer userId, Integer itemId) {
        userService.getUser(userId);
        getItem(itemId);
        return itemRepository.updateItem(item, userId, itemId);
    }

    @Override
    public List<Item> getAllItems(Integer userId) {
        userService.getUser(userId);
        return itemRepository.getAllItems(userId);
    }

    @Override
    public List<Item> searchItem(String str) {
        return itemRepository.searchItem(str.toLowerCase());
    }
}