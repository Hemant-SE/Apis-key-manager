package com.example.keymanager.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private ErrorCode code;
    private String message;
    private String field;
}
