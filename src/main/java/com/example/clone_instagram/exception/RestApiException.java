package com.example.clone_instagram.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String errorMessage;
    private int httpStatusCode;
}
