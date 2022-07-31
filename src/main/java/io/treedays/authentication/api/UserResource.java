package io.treedays.authentication.api;

import io.treedays.authentication.domain.Role;
import io.treedays.authentication.domain.User;
import io.treedays.authentication.model.AssignRoleToUserForm;
import io.treedays.authentication.service.UserService;
import io.treedays.authentication.service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Long> getUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(id);
    }

    @PostMapping("/user/create")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        String path = String.format("/api/users/%s", createdUser.getId());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString());
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PostMapping("/roles/create")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        Role createdRole = userService.saveRole(role);
        String path = String.format("/api/roles/%s", createdRole.getId());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/users/assign")
    public  ResponseEntity<User> assignRoleToUser(@RequestBody AssignRoleToUserForm form) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.addRoleToUser(form.getUsername(), form.getRoleName()));
    }
}
