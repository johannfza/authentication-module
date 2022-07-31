package io.treedays.authentication.service.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
