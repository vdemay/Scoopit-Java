package com.scoopit.model;

public class ResolverResult {
    public static enum Type {
        User, Topic
    }

    public Type type;
    public Long id;
}
