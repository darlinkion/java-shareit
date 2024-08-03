package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.marker.Marker;

@ToString
@Getter
@Setter
@Builder
public class UserDto {
    Long id;
    @NotBlank(groups=Marker.Create.class)
    private String name;
    @Email
    @NotBlank(groups = Marker.Create.class)
    private String email;
}
