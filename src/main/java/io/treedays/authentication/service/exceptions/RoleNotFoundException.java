package io.treedays.authentication.service.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
