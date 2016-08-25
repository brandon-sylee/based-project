package com.brandon.rest.exceptions;

/**
 * Created by brandon Lee on 2016-08-25.
 */
public class NewFeedException extends Exception {
    public NewFeedException() {
        super();
    }

    public NewFeedException(String message) {
        super(message);
    }

    public NewFeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewFeedException(Throwable cause) {
        super(cause);
    }

    protected NewFeedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
