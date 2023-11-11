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

public class SongDAO extends Song implements iDAO<Song, Integer> {
    /*
     * QUERY
     */
    private final static String FINDALL ="SELECT id, name_song, gender, N_reproduction, duration, name_disk, archive_song, name_disk FROM song";

    private final static String FINBYID ="SELECT name_song, gender, N_reproduction, duration, name_disk,archive_song  from song WHERE id  =?";
    private final static String INSERT ="INSERT INTO song (id, name_song, gender,  N_reproduction, duration, name_disk,archive_song) VALUES (NULL,?,?,?,?,?,?)";
    private final static String UPDATE ="UPDATE song SET name_song = ?, gender = ?, N_reproduction = ?, duration = ?, name_disk = ? WHERE id=?";
    private final static String DELETE ="DELETE FROM song WHERE id=?";



    /**
     * Conexion
     */
    private Connection conn;


    public SongDAO(int id, String name_song, String gender, int nrepro, String duration, Album album, String archive_song, Connection conn) {
        super(id, name_song, gender, nrepro, duration, album, archive_song);
        this.conn = conn;
    }

    public SongDAO(String name_song, String gender, int nrepro, String duration, Album album, String archive_song, Connection conn) {
        super(name_song, gender, nrepro, duration, album, archive_song);
        this.conn = conn;
    }

    public SongDAO(Connection conn) {
        this.conn = conn;
    }

    public SongDAO() {
        this.conn= Connect.getConnect();
    }

    @Override
    public List<Song> findAll() throws SQLException {
        List<Song> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Song s = new Song();
                    s.setId(res.getInt("id"));
                    s.setName_song(res.getString("name_song"));
                    s.setGender(res.getString("gender"));
                    s.setNrepro(res.getInt("N_reproduction"));  // Corregido el nombre de la columna
                    s.setDuration(res.getString("duration"));
                    s.setArchive_song(res.getString("archive_song"));
                    String nameDisk = res.getString("name_disk");
                    AlbumDAO adao = new AlbumDAO(this.conn);

                    Album a = adao.findById(nameDisk); // Asumiendo que existe un método "findByName" en tu clase AlbumDAO.

                  

                    s.setAlbum(a);

                    result.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Song findById(Integer id) throws SQLException {
        Song result = null;
        try(PreparedStatement pst=this.conn.prepareStatement(FINBYID)){
            pst.setInt(1, id);
            try(ResultSet res = pst.executeQuery()){
                if(res.next()) {
                    result = new Song();
                    result.setName_song(res.getString("name_song"));
                    result.setGender(res.getString("gender"));
                    result.setNrepro(res.getInt("N_reproduction"));
                    result.setDuration(res.getString("duration"));
                    AlbumDAO adao = new AlbumDAO(this.conn);
                    Album a = adao.findById(res.getString("name_disk"));
                    result.setAlbum(a);
                    result.setArchive_song(res.getString("archive_song"));



                }
            }
        }
        return result;

    }

    @Override
    public Song save(Song entity) throws SQLException {
        Song result = new Song();
        if (entity != null) {
            Song s = findById(entity.getId());

            if (s == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getName_song());
                    pst.setString(2, entity.getGender());
                    pst.setInt(3, entity.getNrepro());
                    pst.setString(4, entity.getDuration());
                    pst.setString(5, entity.getAlbum().getName());
                    pst.setString(6, entity.getArchive_song());

                    pst.executeUpdate();
                }

            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1,entity.getName_song());
                    pst.setString(2, entity.getGender());
                    pst.setInt(3, entity.getNrepro());
                    pst.setString(4, entity.getDuration());
                    pst.setString(5, entity.getAlbum().getName());
                    pst.setInt(6,entity.getId());

                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }


    @Override
    public void delete(Song entity) throws SQLException {
        try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
        }
    }








    public void updateReproductionCount(Song song) throws SQLException {
        if (song != null) {
            String updateQuery = "UPDATE song SET N_reproduction = ? WHERE id = ?";
            try (PreparedStatement pst = this.conn.prepareStatement(updateQuery)) {
                pst.setInt(1, song.getNrepro());
                pst.setInt(2, song.getId());
                pst.executeUpdate();
            }
        }
    }
}
