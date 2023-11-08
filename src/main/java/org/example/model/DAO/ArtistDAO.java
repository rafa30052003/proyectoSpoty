package org.example.model.DAO;

import org.example.conexion.Connect;
import org.example.interfaceDAO.iDAO;
import org.example.model.dto.Artist;
import org.example.model.dto.Nationality;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO extends Artist implements iDAO<Artist,String> {

    /*
     * QUERY
     */
    private final static String FINDALL = "SELECT name, nationality, photo from artist";
    private final static String FINBYID = "SELECT name, nationality, photo  from artist WHERE name =?";
    private final static String INSERT = "INSERT INTO artist (name, nationality, photo) VALUES (?,?,?)";
    private final static String UPDATE = "UPDATE artist SET  nationality=?, photo=? WHERE name=?";
    private final static String DELETE = "DELETE FROM artist WHERE name=?";
    private final static String FINDBYNATIONALITY = "SELECT name, nationality, photo FROM artist WHERE nationality = ?";
    private final static String FIND_NAMES = "SELECT name FROM artist";

    private Connection conn;

    public ArtistDAO(String name, Nationality nacionality, String photo, Connection conn) {
        super(name, nacionality, photo);
        this.conn = conn;
    }

    public ArtistDAO(Nationality nacionality, String photo, Connection conn) {
        super(nacionality, photo);
        this.conn = conn;
    }

    public ArtistDAO() {
        this.conn = Connect.getConnect();
    }





    @Override
    public List<Artist> findAll() throws SQLException {
        List<Artist> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Artist a = new Artist();
                    a.setName(res.getString("name"));
                    String nacionalityStr = res.getString("nationality");
                    Nationality nationality = convertToNationality(nacionalityStr);
                    a.setNationality(nationality);
                    a.setPhoto(res.getString("photo"));

                    result.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Artist findById(String id) throws SQLException {
        Artist result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINBYID)) {
            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Artist();
                    result.setName(res.getString("name"));
                    result.setNationality(Nationality.valueOf(res.getString("nationality")));


                }
            }
        }
        return result;
    }

    @Override
    public Artist save(Artist entity) throws SQLException {
        Artist result = new Artist();
        if (entity != null) {
            Artist a = findById(entity.getName());

            if (a == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getNationality().name());
                    pst.setString(3, entity.getPhoto());

                    pst.executeUpdate();
                }

            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(3, entity.getName());
                    pst.setString(1, entity.getNationality().name());
                    pst.setString(2, entity.getPhoto());

                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    @Override
    public void delete(Artist entity) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
            pst.setString(1, entity.getName());
            pst.executeUpdate();
        }
    }

    public List<String> findNames () throws SQLException {
        List<String> names = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FIND_NAMES)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    String name = res.getString("name");
                    names.add(name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }


    public List<Artist> findByNationality (Nationality nationality) throws SQLException {
        List<Artist> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYNATIONALITY)) {
            pst.setString(1, nationality.name()); // Convierte el enum a una cadena para usarlo en la consulta
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Artist artist = new Artist();
                    artist.setName(res.getString("name"));
                    artist.setNationality(nationality); // Establece la nacionalidad directamente desde el parámetro
                    artist.setPhoto(res.getString("photo"));
                    result.add(artist);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private Nationality convertToNationality(String nationalityStr){
        try {
            return Nationality.valueOf(nationalityStr);
        } catch (IllegalArgumentException e) {
            // Handle the case where the string doesn't match any enum value.
            return Nationality.UNKNOWN; // You can define a default value for this case.
        }
    }


}
