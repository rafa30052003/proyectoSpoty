package org.example.model.dto;

import java.util.Objects;

public class Artist {
    private String name;
    private Nationality nacionality;
    private String photo;

    public Artist() {
    }

    public Artist(String name, Nationality nacionality, String photo) {
        this.name = name;
        this.nacionality = nacionality;
        this.photo = photo;
    }

    public Artist(Nationality nacionality, String photo) {
        this.nacionality = nacionality;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nationality getNacionality() {
        return nacionality;
    }

    public void setNacionality(Nationality nacionality) {
        this.nacionality = nacionality;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}
