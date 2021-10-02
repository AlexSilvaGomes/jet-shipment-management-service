package com.jet.peoplemanagement.model;

public interface UserProfile {
    String getName();
    String getId();
    String getEmail();
    int getSellerId();
    default String getCpf(){
        return "";
    }
}
