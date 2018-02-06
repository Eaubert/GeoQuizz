package org.boundary.exception;

public class PartieNotFound extends RuntimeException {
    public PartieNotFound(String p) {
        super(p);
    }
}
