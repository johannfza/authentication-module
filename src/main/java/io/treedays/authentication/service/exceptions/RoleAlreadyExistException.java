package io.treedays.authentication.service.exceptions;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
