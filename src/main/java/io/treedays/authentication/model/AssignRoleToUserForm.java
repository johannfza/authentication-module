package io.treedays.authentication.model;

import lombok.Data;

@Data
public class AssignRoleToUserForm {
    private String username;
    private String roleName;
}
