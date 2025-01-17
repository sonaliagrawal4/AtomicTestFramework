package org.selenium.pom.factory.objects;

public class Users {
    public Users(){}

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Users setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    private String username;
    private String password;
    private String email;

    public String getEmail() {
        return email;
    }


    public Users setEmail(String email) {
        this.email = email;
        return this;
    }
}
