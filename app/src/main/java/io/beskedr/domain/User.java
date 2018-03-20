package io.beskedr.domain;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String email;
    private String name;
    private String password;

    public User() {}

    public User(String username, String email, String name, String password) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return name;
    }

}
