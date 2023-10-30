package org.example.model.dto;

import java.util.Objects;

public class List {
    private int id;
    private String description;
    private String name_list;
    private String name_user;

    public List(int id, String description, String name_list, String name_user) {
        this.id = id;
        this.description = description;
        this.name_list = name_list;
        this.name_user = name_user;
    }
    public List() {
        this(1, "", "", "");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        List list = (List) o;
        return id == list.id && Objects.equals(description, list.description) && Objects.equals(name_list, list.name_list) && Objects.equals(name_user, list.name_user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, name_list, name_user);
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
}
