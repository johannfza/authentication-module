package io.treedays.authentication.service;

import io.treedays.authentication.domain.Role;
import io.treedays.authentication.domain.User;
import io.treedays.authentication.repository.RoleRepository;
import io.treedays.authentication.repository.UserRepository;
import io.treedays.authentication.service.exceptions.UserNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl sut;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenNewUser_saveUser_returnsUser() {
        // Given
        String username = "johndoe@example.com";
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword("password");
        newUser.setName("John");

        // When
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(passwordEncoder.encode(any())).thenReturn(any());

        // Then
        User savedUser = sut.saveUser(newUser);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(newUser);
        assertNotNull(savedUser);

    }

    @Test
    void givenNewRole_saveRole_returnRole() {
        // Given
        String roleName = "admin";
        Role newRole = new Role();
        newRole.setName(roleName);

        // When
        when(roleRepository.findByName(roleName)).thenReturn(Optional.ofNullable(null));
        when(roleRepository.save(newRole)).thenReturn(newRole);

        // Then
        Role savedRole = sut.saveRole(newRole);
        verify(roleRepository, times(1)).findByName(roleName);
        verify(roleRepository, times(1)).save(newRole);
        assertNotNull(savedRole);
    }

    @Test
    void givenUserAndRoleExist_addRoleToUser_returnsUser() throws UserNotFoundException {
        // Given
        String roleName = "admin";
        Role existingRole = new Role();
        existingRole.setName(roleName);

        String username = "johndoe@example.com";
        User existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setName("John");

        // When
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(existingUser));
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(existingRole));

        // Then
        User savedUser = sut.addRoleToUser(username, roleName);
        verify(userRepository, times(1)).findByUsername(username);
        verify(roleRepository, times(1)).findByName(roleName);
        assertEquals(savedUser.getRoles().size(), 1);
    }

    @Test
    void givenUserNotFound_getUser_throws() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            sut.getUser("unknown");
        });
    }

    @Test
    void getUsers() {
        // Given
        User existingUser1 = new User();
        User existingUser2 = new User();
        List<User> users = new ArrayList<>();
        users.add(existingUser1);
        users.add(existingUser2);
        // When
        when(userRepository.findAll()).thenReturn(users);

        // Then
        List<User> allUsers = sut.getUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(allUsers, users);
    }
}