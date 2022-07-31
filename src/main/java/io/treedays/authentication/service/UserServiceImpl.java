package io.treedays.authentication.service;

import io.treedays.authentication.domain.Role;
import io.treedays.authentication.domain.User;
import io.treedays.authentication.repository.RoleRepository;
import io.treedays.authentication.repository.UserRepository;
import io.treedays.authentication.service.exceptions.RoleAlreadyExistException;
import io.treedays.authentication.service.exceptions.RoleNotFoundException;
import io.treedays.authentication.service.exceptions.UserAlreadyExistException;
import io.treedays.authentication.service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
        userRepository.findByUsername(user.getUsername()).ifPresent(existingUser -> {
            throw new UserAlreadyExistException("user already exist");
        });
        log.info("Saving new user {}", user.getName());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) throws RoleAlreadyExistException {
        roleRepository.findByName(role.getName()).ifPresent(existingUser -> {
            throw new RoleAlreadyExistException("user already exist");
        });
        log.info("Saving new role {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public User addRoleToUser(String username, String roleName) throws UserNotFoundException, RoleNotFoundException {
        User existingUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        log.info("Adding role {} to user {}", roleName, username);
        Role role = roleRepository.findByName(roleName).orElseThrow(
                () -> new RoleNotFoundException(roleName)
        );
        existingUser.getRoles().add(role);
        return existingUser;
    }

    @Override
    public User getUser(String username) throws UserNotFoundException {
        log.info("Fetching user {}", username);
        User existingUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        return existingUser;
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        log.info("Fetching user {}", id.toString());
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id.toString())
        );
        return existingUser;
    }

    @Override
    public Role getRoleById(Long id) throws RoleNotFoundException {
        log.info("Fetching role {}", id.toString());
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new RoleNotFoundException(id.toString())
        );
        return null;
    }
}
