package com.sosadwaden.sobestesttask.api.response;

import com.sosadwaden.sobestesttask.exception.ValidationError;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationExceptionResponse {

    String source;
    List<ValidationError> errors;

}
