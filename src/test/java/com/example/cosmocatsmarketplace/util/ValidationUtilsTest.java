package com.example.cosmocatsmarketplace.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.cosmocatsmarketplace.common.CustomErrorResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

class ValidationUtilsTest {

  @Test
  void testGetErrorResponseOfFieldErrors() {
    FieldError fieldError1 = new FieldError("objectName", "field1", "Field1 error message");
    FieldError fieldError2 = new FieldError("objectName", "field2", "Field2 error message");
    List<FieldError> fieldErrors = List.of(fieldError1, fieldError2);

    WebRequest webRequest = mock(WebRequest.class);
    when(webRequest.getDescription(false)).thenReturn("uri=/test");

    CustomErrorResponse errorResponse = ValidationUtils
        .getErrorResponseOfFieldErrors(fieldErrors, webRequest);

    assertEquals(400, errorResponse.getStatus());
    assertEquals("Bad Request", errorResponse.getError());
    assertEquals("Field1 error message, Field2 error message", errorResponse.getMessage());
    assertEquals("/test", errorResponse.getPath());
  }
}
