package org.example.model.dto;

import java.util.Date;
import java.util.Objects;

public class Album {
    private String name;
    private String photo;
    private Date public_time;
    private int nrepro;
    private Artist name_artist;

    public Album() {
    }

    public Album(String name, String photo, Date public_time, int nRepro, Artist name_artist) {
        this.name = name;
        this.photo = photo;
        this.public_time = public_time;
        this.nrepro = nRepro;
        this.name_artist = name_artist;
    }

    public Album(String photo, Date public_time, int nRepro, Artist name_artist) {
        this.photo = photo;
        this.public_time = public_time;
        this.nrepro = nRepro;
        this.name_artist = name_artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getPublic_time() {
        return public_time;
    }

    public void setPublic_time(Date public_time) {
        this.public_time = public_time;
    }

    public int getNrepro() {
        return nrepro;
    }

    public void setNrepro(int nrepro) {
        this.nrepro = nrepro;
    }

    public Artist getName_artist() {
        return name_artist;
    }

    public void setName_artist(Artist name_artist) {
        this.name_artist = name_artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album disc = (Album) o;
        return Objects.equals(name, disc.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", public_time=" + public_time +
                ", nRepro=" + nrepro +
                ", name_artist=" + name_artist +
                '}';
    }
}
