package org.example.model.DAO;

import org.example.conexion.Connect;
import org.example.interfaceDAO.iDAO;
import org.example.model.dto.Album;
import org.example.model.dto.Comment;
import org.example.model.dto.User;
import org.example.model.dto.list;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDAO implements iDAO<Comment,Object> {
    private final static String INSERT ="INSERT INTO comment (id, name_user,id_list, comment) VALUES (?,?,?,?)";

    private final static String DELETE ="DELETE from comment WHERE id=?";

    private final static String FINDBYNAME="SELECT id, name_user,id_list, comment from comment WHERE id_list=?";

    private final static String UPDATE="UPDATE comment SET id=?, name_user=?,id_list=?, comment=? WHERE name=?";

    private final static String FINDALL="SELECT id, name_user,id_list, comment from commen FROM comment";

    private Connection conn;
    public CommentDAO(Connection conn) {
        this.conn = conn;
    }
    public CommentDAO() {
        this.conn= Connect.getConnect();
    }



    public Comment findAll(Comment entity) throws SQLException {
        return null;
    }



    @Override
    public List<Comment> findAll() throws SQLException {
        return null;
    }

    @Override
    public Comment findById(Object id) throws SQLException {
        return null;
    }


    public  Comment save(Comment entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getName_user());
                pst.setInt(3, entity.getId_list());
                pst.setString(4, entity.getComment());

                pst.executeUpdate();
            }

        }
        return entity;
    }

    @Override
    public void delete(Comment entity) throws SQLException {

    }
    public Comment findCommentById(int listId) throws SQLException {
        Comment comment = null;

        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setInt(1, listId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Selecciona las columnas correctas según tu consulta SQL

                    String commentText = rs.getString("comment");

                    // Crea un nuevo objeto Comment con los datos recuperados
                    comment = new Comment( commentText);
                }
            }
        }

        return comment;
    }

}
