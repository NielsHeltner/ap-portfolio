package io.beskedr.domain;

public class User {

    public String username;
    public String email;
    public String name;
    public String password;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
