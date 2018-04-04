package io.beskedr.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private String username;
    private String email;
    private String name;
    private String password;
    private Map<String, String> contacts;

    public User() {}

    public User(String username, String email, String name, String password) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        contacts = new HashMap<>();
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

    public Map<String, String> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return name;
    }

}
