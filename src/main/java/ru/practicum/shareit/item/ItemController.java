package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item create(@Valid @RequestBody ItemDto itemDto,
                       @RequestHeader("X-Sharer-User-Id") Integer id) {
        Item tempItem = itemService.createItem(itemDto, id);
        log.info("Item created: " + tempItem);
        return tempItem;
    }

    @PatchMapping("{id}")
    public Item update(@RequestBody ItemDto itemDto,
                       @RequestHeader("X-Sharer-User-Id") Integer userId,
                       @PathVariable Integer id) {
        Item tempItem = itemService.updateItem(itemDto, userId, id);
        log.info("Item updated: " + tempItem);
        return tempItem;
    }

    @GetMapping("{id}")
    public Item getForId(@PathVariable Integer id) {
        Item item = itemService.getItem(id);
        log.info("Get item: ", item);
        return item;
    }

    @GetMapping
    public List<Item> getAllItems(@RequestHeader("X-Sharer-User-Id") Integer id) {
        List<Item> itemsList = itemService.getAllItems(id);
        log.info("Get all items: " + itemsList);
        return itemsList;
    }

    @GetMapping("/search")
    public List<Item> searchItems(
            @RequestParam String text) {
        List<Item> itemsList = itemService.searchItem(text);
        log.info("Get all items: " + itemsList);
        return itemsList;
    }
}
