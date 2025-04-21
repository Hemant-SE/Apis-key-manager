package com.example.keymanager.controller.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private String message;
    private List<T> data;
    private List<ErrorDto> errors;
}
