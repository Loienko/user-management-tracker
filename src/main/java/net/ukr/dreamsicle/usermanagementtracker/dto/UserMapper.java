package net.ukr.dreamsicle.usermanagementtracker.dto;

import net.ukr.dreamsicle.usermanagementtracker.model.role.Role;
import net.ukr.dreamsicle.usermanagementtracker.model.role.RoleType;
import net.ukr.dreamsicle.usermanagementtracker.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserMapper {

    public UserDTO userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId().toHexString())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .city(user.getCity())
                .phone(user.getPhone())
                .roles(user.getRoles().stream()
                        .map(var -> var.getName().getName())
                        .collect(Collectors.toSet()))
                .build();
    }

    public User userDtoToUser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .city(userDTO.getCity())
                .build();
    }

    public Page<UserDTO> userToUserDTOs(Page<User> users) {
        return users.map(this::userToUserDto);
    }
}
