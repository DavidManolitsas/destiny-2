package com.manolitsas.david.exception;

import com.manolitsas.david.dto.ArmorSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ArmorBadRequestException extends RuntimeException {

  private final HttpStatus status = HttpStatus.BAD_REQUEST;
  private ArmorSlot slot;
  private String message;

}
