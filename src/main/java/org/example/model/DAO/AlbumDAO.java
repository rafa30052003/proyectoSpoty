package org.example.model.DAO;

import org.example.conexion.Connect;
import org.example.interfaceDAO.iDAO;
import org.example.model.dto.Album;
import org.example.model.dto.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO implements iDAO <Album,Object> {

    private final static String INSERT ="INSERT INTO album (name,photo,publication_date,n_reproduction,name_artist) VALUES (?,?,?,?,?)";

    private final static String DELETE ="DELETE from album WHERE name=?";

    private final static String FINDBYNAME="SELECT name,photo,publication_date,n_reproduction,name_artist from album WHERE name=?";

    private final static String UPDATE="UPDATE album SET name=? WHERE name=?";

    private final static String FINDALL="SELECT name,photo,publication_date,n_reproduction,name_artist FROM album";

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
                    album.setNrepro(rs.getInt("n_reproduction"));
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
                pst.setInt(4, entity.getNrepro());
                pst.setString(5, entity.getName_artist().getName());

                pst.executeUpdate();
            }
        }
        return entity;
    }
    public Album findByName(String name) throws SQLException {
        Album album = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    album = new Album();
                    album.setName(rs.getString("name"));
                    album.setPhoto(rs.getString("photo"));
                    album.setPublic_time(rs.getDate("publication_date"));
                    album.setNrepro(rs.getInt("n_reproduction"));

                    Artist artist = new Artist();
                    artist.setName(rs.getString("name_artist"));
                    album.setName_artist(artist);
                }
            }
        }
        return album;
    }
}
