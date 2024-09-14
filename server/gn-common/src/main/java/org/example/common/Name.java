package org.example.common;

public record Name(String name) {
    public static Name of (String name) {
        return new Name(name);
    }
}
