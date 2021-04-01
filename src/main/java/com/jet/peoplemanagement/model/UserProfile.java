package com.jet.peoplemanagement.model;

public interface UserProfile {
    String getName();
    String getId();
    String getEmail();
    default String getCpf(){
        return "";
    }
}
