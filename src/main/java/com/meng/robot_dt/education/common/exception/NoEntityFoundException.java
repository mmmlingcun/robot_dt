package com.meng.robot_dt.education.common.exception;

public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException() {
        super();
    }

    public NoEntityFoundException(String message) {
        super(message);
    }

    public NoEntityFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEntityFoundException(Throwable cause) {
        super(cause);
    }

    protected NoEntityFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
