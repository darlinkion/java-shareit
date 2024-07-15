package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@Valid @RequestBody ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") Long id) {
        ItemDto tempItem = itemService.createItem(itemDto, id);
        log.info("Item created: " + tempItem);
        return tempItem;
    }

    @PatchMapping("{id}")
    public ItemDto update(@RequestBody ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") Long userId,
                          @PathVariable Long id) {
        itemDto.setId(id);
        ItemDto tempItem = itemService.updateItem(itemDto, userId);
        log.info("Item updated: " + tempItem);
        return tempItem;
    }

    @GetMapping("{id}")
    public ItemDto getForId(@PathVariable Long id) {
        ItemDto item = itemService.getItem(id);
        log.info("Get item: ", item);
        return item;
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") Long id) {
        List<ItemDto> itemsList = itemService.getAllItems(id);
        log.info("Get all items: " + itemsList);
        return itemsList;
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(
            @RequestParam String text) {
        List<ItemDto> itemsList = itemService.searchItem(text);
        log.info("Search all items: " + itemsList);
        return itemsList;
    }
}
