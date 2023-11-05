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
    private final static String FINDALL ="SELECT id, name_song, gender, nrepro, duration, name_disk,archive_song from song";
    private final static String FINBYID ="SELECT name_song, gender, nrepro, duration, name_disk,archive_song  from song WHERE id  =?";
    private final static String INSERT ="INSERT INTO song (id, name_song, gender, nrepro, duration, name_disk,archive_song) VALUES (NULL,?,?,?,?,?,?)";
    private final static String UPDATE ="UPDATE name_song = ?, gender = ?, nrepro = ?, duration = ?, name_disk,archive_song = ? WHERE id=?";
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
        List <Song> result = new ArrayList<>();
        try(PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()) {
                    Song s = new Song();
                    s.setId(res.getInt("id"));
                    s.setName_song(res.getString("name_song"));
                    s.setGender(res.getString("gender"));
                    s.setNrepro(res.getInt("nrepo"));
                    s.setDuration(res.getString("duration"));
                    AlbumDAO adao = new AlbumDAO(this.conn);
                    Album a = adao.findById(res.getString("name"));
                    s.setAlbum(a);
                    s.setArchive_song(res.getString("archive_song"));



                    result.add(s);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
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
                    result.setNrepro(res.getInt("nrepo"));
                    result.setDuration(res.getString("duration"));
                    AlbumDAO adao = new AlbumDAO(this.conn);
                    Album a = adao.findById(res.getString("name"));
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
                    pst.setString(1, entity.getGender());
                    pst.setInt(2, entity.getNrepro());
                    pst.setString(3, entity.getDuration());
                    pst.setString(4, entity.getAlbum().getName());

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
}

