package org.example.model.dto;

public class Subscription {
    private User name_user;
    private List id_list;

    public Subscription(User name_user, List id_list) {
        this.name_user = name_user;
        this.id_list = id_list;
    }

    public User getName_user() {
        return name_user;
    }

    public void setName_user(User name_user) {
        this.name_user = name_user;
    }

    public List getId_list() {
        return id_list;
    }

    public void setId_list(List id_list) {
        this.id_list = id_list;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "name_user=" + name_user +
                ", id_list=" + id_list +
                '}';
    }
}
