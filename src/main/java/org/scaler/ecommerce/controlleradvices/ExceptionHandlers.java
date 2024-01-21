package org.scaler.ecommerce.controlleradvices;

import org.scaler.ecommerce.dto.ExceptionDto;
import org.scaler.ecommerce.exceptions.ProductDoesNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ProductDoesNotExistException.class)
    public ResponseEntity<ExceptionDto> handleProductNotExistException(ProductDoesNotExistException exception) {
        ExceptionDto dto = new ExceptionDto();
        dto.setMessage(exception.getMessage());
        dto.setDetail("Check the product id. It probably doesn't exist.");
        return ResponseEntity.ok(dto);
    }
}
