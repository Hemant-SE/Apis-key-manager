package com.example.keymanager.exceptions;

import com.example.keymanager.controller.model.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public BadRequestException(ErrorCode errorCode, String message, Object... args) {
        super(buildMessage(message, args));
        this.errorCode = errorCode;
    }

    private static String buildMessage(String message, Object... args) {
        return MessageFormat.format(message, args);
    }
}
