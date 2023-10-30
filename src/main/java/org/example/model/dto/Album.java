package org.example.model.dto;

public class Album {
    private int id_song;
    private int id_list;

    public Album(int id_song, int id_list) {
        this.id_song = id_song;
        this.id_list = id_list;
    }

    public int getId_song() {
        return id_song;
    }

    public void setId_song(int id_song) {
        this.id_song = id_song;
    }

    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {
        this.id_list = id_list;
    }
}
