package org.scaler.ecommerce.exceptions;

public class CategoryDoesNotExistException extends Exception {
    public CategoryDoesNotExistException(String message) {
        super(message);
    }
}
