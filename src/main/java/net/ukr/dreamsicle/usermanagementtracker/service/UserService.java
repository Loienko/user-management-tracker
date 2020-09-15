package net.ukr.dreamsicle.usermanagementtracker.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.usermanagementtracker.dto.UserDTO;
import net.ukr.dreamsicle.usermanagementtracker.dto.UserMapper;
import net.ukr.dreamsicle.usermanagementtracker.exception.CustomDataAlreadyExistsException;
import net.ukr.dreamsicle.usermanagementtracker.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.usermanagementtracker.model.role.Role;
import net.ukr.dreamsicle.usermanagementtracker.model.role.RoleType;
import net.ukr.dreamsicle.usermanagementtracker.model.user.User;
import net.ukr.dreamsicle.usermanagementtracker.repository.RoleRepository;
import net.ukr.dreamsicle.usermanagementtracker.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static net.ukr.dreamsicle.usermanagementtracker.model.user.StatusType.ACTIVE;
import static net.ukr.dreamsicle.usermanagementtracker.model.user.StatusType.DELETED;
import static net.ukr.dreamsicle.usermanagementtracker.service.ConstantsService.EMAIL_IS_ALREADY_IN_USE;
import static net.ukr.dreamsicle.usermanagementtracker.service.ConstantsService.USERNAME_IS_ALREADY_IN_USE;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDTO createUser(UserDTO userDTO) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDTO.getUsername()))) {
            throw new CustomDataAlreadyExistsException(USERNAME_IS_ALREADY_IN_USE);
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userDTO.getEmail()))) {
            throw new CustomDataAlreadyExistsException(EMAIL_IS_ALREADY_IN_USE);
        }

        saveRolesToDB();

        User user = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .city(userDTO.getCity())
                .phone(userDTO.getPhone())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .updated(Timestamp.valueOf(LocalDateTime.now()))
                .status(ACTIVE)
                .roles(acquireRoles(userDTO))
                .build();

        return userMapper.userToUserDto(userRepository.save(user));
    }

    private void saveRolesToDB() {
        if (roleRepository.findAll().isEmpty()){
            roleRepository.save(Role.builder()
                    .name(RoleType.USER)
                    .build());
            roleRepository.save(Role.builder()
                    .name(RoleType.ADMIN)
                    .build());
        }
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDTO updateUser(ObjectId id, UserDTO userDTO) {
        User userUpdateById = userRepository.findByIdAndStatus(id, ACTIVE).orElseThrow(ResourceNotFoundException::new);

        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDTO.getUsername())) && !userUpdateById.getUsername().equals(userDTO.getUsername())) {
            throw new CustomDataAlreadyExistsException(USERNAME_IS_ALREADY_IN_USE);
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userDTO.getEmail())) && !userUpdateById.getEmail().equals(userDTO.getEmail())) {
            throw new CustomDataAlreadyExistsException(EMAIL_IS_ALREADY_IN_USE);
        }

        User actualUser = userMapper.userDtoToUser(userDTO);

        userUpdateById.setId(id);
        userUpdateById.setName(actualUser.getName());
        userUpdateById.setUsername(actualUser.getUsername());
        userUpdateById.setEmail(actualUser.getEmail());
        userUpdateById.setPhone(actualUser.getPhone());
        userUpdateById.setCity(actualUser.getCity());
        userUpdateById.setRoles(actualUser.getRoles());

        return userMapper.userToUserDto(userRepository.save(userUpdateById));
    }

    private Set<Role> acquireRoles(UserDTO user) {
        return user.getRoles().stream()
                .map(role -> roleRepository.findByName(RoleType.getEnumFromString(role)).orElseThrow(ResourceNotFoundException::new))
                .collect(Collectors.toSet());
    }

    public Page<UserDTO> findAllUsers(Pageable pageable) {
        return userMapper.userToUserDTOs(userRepository.findAllByStatus(pageable, ACTIVE));
    }

    public UserDTO findUserById(ObjectId id) {
        return userRepository
                .findByIdAndStatus(id, ACTIVE)
                .map(userMapper::userToUserDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteUser(ObjectId id) {
        User user = userRepository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(ResourceNotFoundException::new);
        user.setStatus(DELETED);
        userRepository.save(user);
    }
}