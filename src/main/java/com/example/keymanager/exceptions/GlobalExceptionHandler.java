package com.example.keymanager.exceptions;

import com.example.keymanager.controller.model.ErrorCode;
import com.example.keymanager.controller.model.ErrorDto;
import com.example.keymanager.controller.model.ResponseDto;
import com.example.keymanager.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.info("Resource not found: ", ex);
        ResponseDto<Object> responseDto = ResponseDto.builder()
                .data(List.of())
                .errors(List.of(buildErrorDto(ex.getErrorCode(), ex.getMessage())))
                .message(Constants.FAILED)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ResponseDto<Object>> handleAlreadyExistsException(AlreadyExistsException ex) {
        log.info("Resource already exists: ", ex);
        ResponseDto<Object> responseDto = ResponseDto.builder()
                .errors(List.of(buildErrorDto(ex.getErrorCode(), ex.getMessage())))
                .message(Constants.FAILED)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto<Object>> handleBadRequestException(BadRequestException ex) {
        log.info("Bad request: ", ex);
        ResponseDto<Object> responseDto = ResponseDto.builder()
                .data(List.of())
                .errors(List.of(buildErrorDto(ex.getErrorCode(), ex.getMessage())))
                .message(Constants.FAILED)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleRuntimeException(Exception ex) {
        log.error("General exception handler.", ex);
        ResponseDto<Object> responseDto = ResponseDto.builder()
                .errors(List.of(buildErrorDto(ErrorCode.ERR_500000, ex.getMessage())))
                .message(Constants.FAILED)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDto buildErrorDto(ErrorCode errorCode, String message) {
        return ErrorDto.builder()
                .message(message)
                .code(errorCode)
                .build();
    }
}