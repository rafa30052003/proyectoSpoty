package org.example.model.DAO;

import org.example.conexion.Connect;
import org.example.interfaceDAO.iDAO;
import org.example.model.dto.Album;
import org.example.model.dto.Artist;
import org.example.model.dto.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO implements iDAO <Album,Object> {

    private final static String INSERT ="INSERT INTO album (name,photo,publication_date,n_reproduction,name_artist) VALUES (?,?,?,?,?)";

    private final static String DELETE ="DELETE from album WHERE name=?";

    private final static String FINDBYNAME="SELECT * from album WHERE name=?";

    private final static String UPDATE="UPDATE album SET name=? WHERE name=?";

    private final static String FINDALL="SELECT * FROM album";

    private Connection conn;
    public AlbumDAO(Connection conn) {
        this.conn = conn;
    }
    public AlbumDAO() {
        this.conn= Connect.getConnect();
    }

    @Override
    public List<Album> findAll() throws SQLException {
        List<Album> albums = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Album album = new Album();
                    album.setName(rs.getString("name"));
                    album.setPhoto(rs.getString("photo"));
                    album.setPublic_time(rs.getDate("publication_date"));
                    album.setnRepro(rs.getInt("n_reproduction"));

                    Artist artist = new Artist();
                    artist.setName(rs.getString("name_artist"));
                    album.setName_artist(artist);

                    albums.add(album);
                }
            }
        }
        return albums;
    }

    @Override
    public Album findById(Object id) throws SQLException {
        return null;
    }


    @Override
    public void delete(Album entity) throws SQLException {
        if(entity!=null) {
            try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
                pst.setString(1, entity.getName());
                pst.executeUpdate();
            }
        }
    }
    public void updateAlbumName(String newName, String name) throws SQLException {
        if (newName != null && name != null && !newName.isEmpty() && !name.isEmpty()) {
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setString(1, newName);
                pst.setString(2, name);
                pst.executeUpdate();
            }
        }
    }

    @Override
    public Album save(Album entity) throws SQLException {
        if(entity != null) {
            try(PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setString(1, entity.getName());
                pst.setString(2, entity.getPhoto());
                pst.setDate(3, new java.sql.Date(entity.getPublic_time().getTime()));
                pst.setInt(4, entity.getnRepro());
                pst.setString(5, entity.getName_artist().getName());

                pst.executeUpdate();
            }
        }
        return entity;
    }
    public List<Album> findByName(String name) throws SQLException {
        List<Album> albums = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Album album = new Album();
                    album.setName(rs.getString("name"));
                    album.setPhoto(rs.getString("photo"));
                    album.setPublic_time(rs.getDate("publication_date"));
                    album.setnRepro(rs.getInt("n_reproduction"));

                    Artist artist = new Artist();
                    artist.setName(rs.getString("name_artist"));
                    album.setName_artist(artist);

                    albums.add(album);
                }
            }
        }
        return albums;
    }

    public void updateReproductionCount(Album album) throws SQLException {
        if (album != null) {
            String updateQuery = "UPDATE album SET n_reproduction = ? WHERE name = ?";
            try (PreparedStatement pst = this.conn.prepareStatement(updateQuery)) {
                pst.setInt(1, album.getNrepro());
                pst.setString(2, album.getName());
                pst.executeUpdate();
            }
        }
    }
    public List<Song> findSongsByAlbumName(String albumName) throws SQLException {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT song.* FROM song INNER JOIN album ON song.name_disk = album.name WHERE album.name = ?";
        try (PreparedStatement pst = this.conn.prepareStatement(query)) {
            pst.setString(1, albumName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Song song = new Song();
                    song.setId(rs.getInt("id"));
                    song.setName_song(rs.getString("name_song"));
                    song.setGender(rs.getString("gender"));

                    song.setDuration(rs.getString("duration"));
                    // También necesitarás establecer el álbum al que pertenece esta canción aquí
                    Album album = new Album();
                    album.setName(rs.getString("name_disk"));
                    song.setAlbum(album);
                    songs.add(song);
                }
            }
        }
        return songs;
    }
}