package com.eltropy.bankingsystem.error;

import lombok.Getter;
import lombok.ToString;

@ToString
public class  CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private ErrorCode code;

    public CustomException (ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException (Throwable e) {
        super(e);
    }

    public CustomException (ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode;
    }
}
