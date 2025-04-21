package com.example.keymanager.controller.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ERR_500000("Something Went Wrong"),
    ERR_404000("No Resource Found"),
    ERR_400000("Bad Request"),
    ERR_409000("Already Exists"),
    ERR_410000("Password Expired"),
    ERR_400001("Missing required fields"),
    ERR_401000("Unauthorized");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }
}