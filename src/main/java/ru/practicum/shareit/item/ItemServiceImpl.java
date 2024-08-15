package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.BookingException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    @Override
    public ItemDto createItem(ItemDto item, Long userId) {
        User user = UserMapper.toUserFromDto(userService.getUser(userId));
        Item tempItem = ItemMapper.toItemFromDto(item);
        tempItem.setOwner(user);
        return ItemMapper.toItemDto(itemRepository.save(tempItem));
    }

    @Transactional(readOnly = true)
    @Override
    public ItemResponseDto getItem(Long itemId, Long userId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new NotFoundException("Нет такого предмета по id:" + itemId);
        }
        log.info("id itemUser: " + item.get().getOwner().getId() + " id user:" + userId);
        List<Comment> comments = new ArrayList<>(commentRepository.findAllByItemId(itemId));

        if (item.get().getOwner().getId().equals(userId)) {
            Booking bookingLast = bookingRepository.findFirstByItemIdAndStartTimeBefore(itemId, LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "start"));
            Booking bookingNext = bookingRepository.findFirstByItemIdAndStartTimeAfterAndStatus(item.get().getId(),
                    LocalDateTime.now(), Sort.by(Sort.Direction.ASC, "startTime"), Status.APPROVED);
            return ItemMapper.toItemResponseDto(item.get(), bookingNext, bookingLast, comments);
        }
        return ItemMapper.toItemResponseDto(item.get(), null, null, comments);
    }

    @Transactional
    @Override
    public ItemDto updateItem(ItemDto item, Long userId) {
        userService.getUser(userId);
        Long id = item.getId();
        Optional<Item> oldItem = itemRepository.findById(id);

        if (oldItem.isEmpty()) {
            throw new NotFoundException("Предмет id=" + id + "не пренадлежит пользователю id=" + userId);
        }
        if (item.getName() != null) {
            oldItem.get().setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.get().setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.get().setAvailable(item.getAvailable());
        }

        Item tempItem = itemRepository.save(oldItem.get());
        return ItemMapper.toItemDto(tempItem);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> getAllItems(Long userId) {
        userService.getUser(userId);
        List<Item> items = itemRepository.findItemByOwner_Id(userId);
        return items.stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> searchItem(String str) {
        if (str == null || str.isBlank())
            return new ArrayList<>();

        List<Item> items = itemRepository.searchItem(str.toLowerCase());
        return items.stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public CommentResponseDto createComment(CommentDto commentDto) {
        User user = UserMapper.toUserFromDto(userService.getUser(commentDto.getAuthorId()));
        Item item = itemRepository.findById(commentDto.getItemId()).orElseThrow(
                () -> new NotFoundException("Нет такого предмета"));

        Booking booking = bookingRepository.findByBookerAndItem(user, item).orElseThrow(
                () -> new BookingException("Нет такого бронирования")
        );

        if (!booking.getStatus().equals(Status.APPROVED) || booking.getEndTime().isAfter(LocalDateTime.now())) {
            throw new BookingException("Бронирование не завершено");
        }

        Comment newComment = CommentMapper.fromDto(commentDto, item, user);

        Comment comment = commentRepository.save(newComment);
        return CommentMapper.toCommentResponseDto(comment);
    }
}