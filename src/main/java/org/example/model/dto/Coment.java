package org.example.model.dto;

import java.util.Objects;

public class Coment {
    private int id;
    private String name_user;
    private  int id_list;
    private String comment;

    public Coment(int id, String name_user, int id_list, String comment) {
        this.id = id;
        this.name_user = name_user;
        this.id_list = id_list;
        this.comment = comment;
    }

    public Coment() {
        this(1, "", 1,"");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {
        this.id_list = id_list;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coment coment = (Coment) o;
        return id == coment.id && id_list == coment.id_list && Objects.equals(name_user, coment.name_user) && Objects.equals(comment, coment.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_user, id_list, comment);
    }

    @Override
    public String toString() {
        return "Coment{" +
                "id=" + id +
                ", name_user='" + name_user + '\'' +
                ", id_list=" + id_list +
                ", comment='" + comment + '\'' +
                '}';
    }
}