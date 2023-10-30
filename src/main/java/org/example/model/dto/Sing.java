package org.example.model.dto;

public class Sing {
    private int id;
    private String name_song;
    private String gender;
    private int N_reproduction;
    private String duration;
    private String Name_disk;
    private String archive_song;

    public Sing(int id, String name_song, String gender, int n_reproduction, String duration, String name_disk, String archive_song) {
        this.id = id;
        this.name_song = name_song;
        this.gender = gender;
        N_reproduction = n_reproduction;
        this.duration = duration;
        Name_disk = name_disk;
        this.archive_song = archive_song;
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

    public int getN_reproduction() {
        return N_reproduction;
    }

    public void setN_reproduction(int n_reproduction) {
        N_reproduction = n_reproduction;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName_disk() {
        return Name_disk;
    }

    public void setName_disk(String name_disk) {
        Name_disk = name_disk;
    }

    public String getArchive_song() {
        return archive_song;
    }

    public void setArchive_song(String archive_song) {
        this.archive_song = archive_song;
    }
}
