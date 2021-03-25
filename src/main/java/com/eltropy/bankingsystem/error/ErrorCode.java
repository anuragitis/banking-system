package com.eltropy.bankingsystem.error;

import lombok.Getter;

public enum ErrorCode {
    UNKNOWN("UNKNOWN"),
    INVALID("INVALID"),
    NOT_FOUND("NOT_FOUND"),
    BAD_REQUEST("BAD_REQUEST"),
    UNAUTHORIZED("UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN"),
    UNPROCESSABLE_ENTITY("UNPROCESSABLE_ENTITY"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    EMPLOYEE_ALREADY_FOUND("EMPLOYEE_ALREADY_FOUND"),
	EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND"),
	ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND"),
	CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND"),
	INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE");

    @Getter
    private final String name;

    @Getter
    private String message;

    private ErrorCode (String name) {
        this.name = name;
    }

    private ErrorCode (String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String toString () {
        return this.name;
    }
}

