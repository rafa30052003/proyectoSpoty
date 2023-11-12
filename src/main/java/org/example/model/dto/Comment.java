package org.example.model.dto;

public class Comment {
    private  int id;
    private  String name_user;
    private int id_list;
    private  String comment;

    public Comment(int id, String name_user, int id_list, String comment) {
        this.id = id;
        this.name_user = name_user;
        this.id_list = id_list;
        this.comment = comment;
    }

    public Comment(String commentText) {
        this.comment = commentText;
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
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name_user='" + name_user + '\'' +
                ", id_list=" + id_list +
                ", comment='" + comment + '\'' +
                '}';
    }
}
