package org.example.model.DAO;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.conexion.Connect;
import org.example.interfaceDAO.iDAO;
import org.example.model.dto.Album;
import org.example.model.dto.Song;
import org.example.model.dto.list;

public class ListDAO extends list implements iDAO<list, Integer> {
    private final static String FINDALL ="SELECT id, description, name_list, name_user FROM list";

    private final static   String ListSub ="SELECT l.name_list FROM list l JOIN subscription s ON l.id = s.id_list WHERE s.name_user  = ?";


    private final static String FINBYID ="SELECT s.* FROM song s JOIN song_list sl ON s.id = sl.id_song JOIN list l ON sl.id_list = l.id WHERE l.id = ?";

    private final static String INSERT ="INSERT INTO list (id, name_list, description, name_user) VALUES (?,?,?,?)";

    private final static String INSERTSonginList ="INSERT INTO song_list (id_list,id_song) VALUES (?,?)";
    private final static String UPDATE ="UPDATE id = ?, , description = ?, , name_list= ?, name_user  = ? WHERE id=?";
    private final static String DELETE ="DELETE FROM list WHERE id=?";
    private final static String DELETESongofList ="DELETE FROM song_list  WHERE id_song=? and id_list=?";

    private Connection conn;
    public ListDAO(Connection conn) {
        super();
        this.conn = conn;
    }
    public ListDAO() {
        super();
        this.conn= Connect.getConnect();
    }

    /**
     * funcion para mostrar las listas de la base de datos
     * @return
     * @throws SQLException
     */
    public List<String> findAllNameLists() throws SQLException {
        List<String> nameLists = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String nameList = rs.getString("name_list");
                    int id = rs.getInt("id");
                    nameLists.add(nameList);
                }
            }
        }
        return nameLists;
    }


    @Override
    public List<list> findAll() throws SQLException {
        return null;
    }

    @Override
    public list findById(Integer id) throws SQLException {
        return null;
    }

    /**
     * funcion para guardar la list en la base de datos
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public list save(list entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getDescription());
                pst.setString(3, entity.getName_list());
                pst.setString(4, entity.getName_user());
                pst.executeUpdate();
            }
        }
        return entity;
    }

    @Override
    public void delete(list entity) throws SQLException {

    }

    /**
     * funcion para borrar las listas
     * @param id
     * @throws SQLException
     */
    public void delete(int id) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }


    /**
     * funcion mara mostrar solo las listas de el usuario
     * @param loggedInUserName
     * @return
     * @throws SQLException
     */
    public List<String> findAllNameListsByUser(String loggedInUserName) throws SQLException {
        List<String> nameLists = new ArrayList<>();

        String query = "SELECT name_list FROM list WHERE name_user = ?";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, loggedInUserName);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String nameList = rs.getString("name_list");
                    nameLists.add(nameList);
                }
            }
        }
        return nameLists;
    }


    public int findIdByName(String listName) throws SQLException {
        int listId = -1; // Valor predeterminado si no se encuentra la lista

        String query = "SELECT id FROM list WHERE name_list = ?";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, listName);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    listId = rs.getInt("id");

                }
            }
        }

        return listId;
    }

    /**
     * funcion para modificar las listas
     * @param entity
     * @return
     * @throws SQLException
     */
    public list update(list entity) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
            pst.setInt(1, entity.getId());
            pst.setString(2, entity.getDescription());
            pst.setString(3, entity.getName_list());
            pst.setString(4, entity.getName_user());
            pst.executeUpdate();
        }
        return entity;
    }

    /**
     * funcion para mostrar las listas a las que el usuario esta subcrito
     * @param userName
     * @return
     * @throws SQLException
     */
    public List<String> findSubscribedLists(String userName) throws SQLException {
        List<String> subscribedLists = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(ListSub)) {
            pst.setString(1, userName);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String nameList = rs.getString("name_list");
                    subscribedLists.add(nameList);
                }
            }
        }

        return subscribedLists;
    }

    /**
     * fUNCION PARA GUARDAR LA CANCION EN LAS LISTA QUE SELECCIONAMOS
     * @param idList
     * @param songId
     * @throws SQLException
     */
    public void insertSongInList(int idList, int songId) throws SQLException {
        // La conexión a la base de datos debería estar establecida antes de llamar a este método
        try (PreparedStatement pst = this.conn.prepareStatement(INSERTSonginList)) {
            pst.setInt(1, idList);
            pst.setInt(2, songId);
            pst.executeUpdate();
        }
    }

    /**
     * FUNCION PARA MOSTRAS LAS CANCIONES DE LA LISTA SELECIONADO
     * @param listId
     * @return
     * @throws SQLException
     */

    public List<Song> findSongsByListId(int listId) throws SQLException {
        List<Song> songs = new ArrayList<>();

        try (PreparedStatement pst = this.conn.prepareStatement(FINBYID)) {
            pst.setInt(1, listId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Song song = new Song();
                    song.setId(rs.getInt("id"));
                    song.setName_song(rs.getString("name_song"));
                    song.setGender(rs.getString("gender"));
                    song.setArchive_song(rs.getString("archive_song"));

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

    /**
     * Funcion para borrar la cancion de la lista
     * @param songId
     * @param listId
     * @throws SQLException
     */
    public void deleteSongOfList(int songId, int listId) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETESongofList)) {
            pst.setInt(1, songId);
            pst.setInt(2, listId);

            // Ejecutar la consulta de eliminación
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("La canción se eliminó correctamente de la lista.");
            } else {
                System.out.println("No se encontró la relación entre la canción y la lista.");
                // Puedes manejar este caso según tus necesidades
            }
        }
    }



}
