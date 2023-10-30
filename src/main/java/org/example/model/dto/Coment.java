package org.example.model.dto;

import java.util.Objects;

public class Coment {
    private String id;
    private int name;
    private  int id_list;

    public Coment(String id, int name, int id_list) {
        this.id = id;
        this.name = name;
        this.id_list = id_list;
    }
    public Coment() {
        this("", 1, 1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {
        this.id_list = id_list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coment coment = (Coment) o;
        return name == coment.name && id_list == coment.id_list && Objects.equals(id, coment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, id_list);
    }

    @Override
    public String toString() {
        return "Coment{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", id_list=" + id_list +
                '}';
    }
}
