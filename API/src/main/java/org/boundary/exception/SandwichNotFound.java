package org.boundary.exception;

public class SandwichNotFound extends RuntimeException {
    public SandwichNotFound(String s) {
        super(s);
    }
}
