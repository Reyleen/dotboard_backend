package it.uniroma3.siw.dotboard_backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public interface Validator {
  static void validate(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String message = "Input data not valid at field " + Objects.requireNonNull(bindingResult.getFieldError()).getField();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
  }
}
