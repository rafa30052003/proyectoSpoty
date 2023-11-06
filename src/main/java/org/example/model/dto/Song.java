package org.example.model.dto;

import java.util.Objects;

public class Song {
    private int id;
    private String name_song;
    private String gender;
    private int nrepro;
    private String duration;
    private Album album;
    private String archive_song;

    public Song(int id, String name_song, String gender, int nrepro, String duration, Album album, String archive_song) {
        this.id = id;
        this.name_song = name_song;
        this.gender = gender;
        this.nrepro = nrepro;
        this.duration = duration;
        this.album = album;
        this.archive_song = archive_song;
    }

    public Song(String name_song, String gender, int nrepro, String duration, Album album, String archive_song) {
        this.name_song = name_song;
        this.gender = gender;
        this.nrepro = nrepro;
        this.duration = duration;
        this.album = album;
        this.archive_song = archive_song;
    }

    public Song() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_song() {
        return name_song;
    }

    public void setName_song(String name_song) {
        this.name_song = name_song;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNrepro() {
        return nrepro;
    }

    public void setNrepro(int nrepro) {
        this.nrepro = nrepro;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getArchive_song() {
        return archive_song;
    }

    public void setArchive_song(String archive_song) {
        this.archive_song = archive_song;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return id == song.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);


    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name_song='" + name_song + '\'' +
                ", gender='" + gender + '\'' +
                ", nrepro=" + nrepro +
                ", duration='" + duration + '\'' +
                ", album=" + album +
                ", archive_song='" + archive_song + '\'' +
                '}';
    }
}
