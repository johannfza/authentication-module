package io.treedays.authentication.service;

import io.treedays.authentication.domain.Role;
import io.treedays.authentication.domain.User;
import io.treedays.authentication.service.exceptions.RoleNotFoundException;
import io.treedays.authentication.service.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    User addRoleToUser(String username, String roleName) throws UserNotFoundException, RoleNotFoundException;
    User getUser(String username) throws UserNotFoundException;
    List<User> getUsers();

    User getUserById(Long id) throws UserNotFoundException;

    Role getRoleById(Long id) throws RoleNotFoundException;
}
