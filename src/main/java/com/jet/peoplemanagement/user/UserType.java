package com.jet.peoplemanagement.user;

public enum UserType {
    PROVIDER(),
    CLIENT();

    public String getName() {
        return this.name().toLowerCase();
    }
}
