package com.jet.peoplemanagement.user;

public enum UserType {
    PROVIDER(),
    ADMIN,
    CLIENT();

    public String getName() {
        return this.name().toLowerCase();
    }
}
