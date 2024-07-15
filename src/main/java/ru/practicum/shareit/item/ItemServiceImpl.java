package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public ItemDto createItem(ItemDto item, Long userId) {
        userService.getUser(userId);
        Item tempItem = itemRepository.createItem(item, userId);
        return ItemMapper.toItemDto(tempItem);
    }

    @Override
    public ItemDto getItem(Long id) {
        Optional<Item> item = Optional.ofNullable(itemRepository.getItem(id));
        if (item.isEmpty()) {
            throw new NotFoundException("Нет такого предмета по id:" + id);
        }
        return ItemMapper.toItemDto(item.get());

    }

    @Override
    public ItemDto updateItem(ItemDto item, Long userId) {
        userService.getUser(userId);
        Long id = item.getId();
        Item oldItem = itemRepository.getItem(id);

        if (oldItem == null || !Objects.equals(oldItem.getOwnerId(), userId)) {
            throw new NotFoundException("Предмет id=" + id + "не пренадлежит пользователю id=" + userId);
        }
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }

        Item tempItem = itemRepository.updateItem(oldItem);
        return ItemMapper.toItemDto(tempItem);
    }

    @Override
    public List<ItemDto> getAllItems(Long userId) {
        userService.getUser(userId);
        List<Item> items = itemRepository.getAllItems(userId);
        return items.stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> searchItem(String str) {
        List<Item> items = itemRepository.searchItem(str.toLowerCase());
        return items.stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }
}