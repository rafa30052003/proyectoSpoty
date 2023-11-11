package org.example.model.dto;

public class list {
    private int id;
    private String description;
    private String name_list;
    private String name_user;

    public list(int id, String description, String name_list, String name_user) {
        this.id = id;
        this.description = description;
        this.name_list = name_list;
        this.name_user = name_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_list() {
        return name_list;
    }

    public void setName_list(String name_list) {
        this.name_list = name_list;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name_list='" + name_list + '\'' +
                ", name_user='" + name_user + '\'' +
                '}';
    }

    public list() {
    }
}