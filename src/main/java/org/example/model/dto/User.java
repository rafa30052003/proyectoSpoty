package org.example.model.dto;

import java.util.Objects;

public class User {
    private String name;
    private String mail;
    private String photo;
    private String password;

    public User(String name, String mail, String photo, String password) {
        this.name = name;
        this.mail = mail;
        this.photo = photo;
        this.password = password;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", photo='" + photo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
