package com.manolitsas.david.handler;

import com.manolitsas.david.exception.ArmorBadRequestException;
import com.manolitsas.david.model.armor.ArmorError;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ExceptionHandler {

//  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @org.springframework.web.bind.annotation.ExceptionHandler(ArmorBadRequestException.class)
  public ResponseEntity<ArmorError> handleArmorBadRequestException(ArmorBadRequestException exception) {
    final ArmorError error = new ArmorError(
        exception.getSlot(),
        exception.getMessage()
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

}
