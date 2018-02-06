package org.boundary.exception;

public class PhotoNotFound extends RuntimeException {
    public PhotoNotFound(String p) {
        super(p);
    }
}
