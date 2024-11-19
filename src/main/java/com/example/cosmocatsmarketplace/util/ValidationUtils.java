package com.example.cosmocatsmarketplace.util;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;

public class ValidationUtils {

  public static ProblemDetail getErrorResponseOfFieldErrors(List<FieldError> fieldErrors) {
    ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, fieldErrors.stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", ")));
    problemDetail.setType(create("validation-error"));
    problemDetail.setTitle("Field Validation Failed");
    return problemDetail;
  }
}
