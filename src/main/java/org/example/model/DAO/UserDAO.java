package org.example.model.DAO;

import org.example.conexion.Connect;
import org.example.interfaceDAO.iDAO;
import org.example.model.dto.Artist;
import org.example.model.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements iDAO<User,String> {
    private final static String FINDALL = "SELECT * from user";
    private final static String FINDBYNAME_USER = "SELECT name,mail,photo,password from user WHERE name=?";
    private final static String FINDBYNAME_ADMIN= "SELECT name from admin WHERE name=?";
    private final static String FINDBYNAME_pass_USER = "SELECT name,password from user WHERE name=? AND password=?";
    private final static String FINDBYNAME_pass_ADMIN = "SELECT name,password  from admin WHERE name=? AND password=?";
    private final static String INSERT = "INSERT INTO User (name,mail,photo,password) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE User SET Name=?, mail=?, photo=?, password=? where name=?";
    private final static String DELETE = "DELETE FROM User WHERE Name = ?";
    private final static String addsub = "Insert Into subscription(id_list,name_user)values(?,?)";
    private final static String deletesub = "DELETE FROM subscription WHERE name_user= ? and id_list=?";

    private Connection conn;

    public UserDAO()  {
        conn = Connect.getConnect();
        if (conn == null) {
            throw new RuntimeException("Unable to establish connection to the database.");
        }
    }
    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }

    @Override
    public User findById(String id) throws SQLException {
        return null;
    }

  

    @Override
    public User save(User entity) throws SQLException {
        if (entity != null) {
            // Verificar si el usuario ya existe en la base de datos
            User existingUser = findByName(entity.getName(), FINDBYNAME_USER);
            if (existingUser != null) {
                // Si el usuario ya existe, actualiza los datos
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getMail());
                    pst.setString(2, entity.getPhoto());
                    pst.setString(3, entity.getPassword());
                    pst.setString(4, entity.getName()); // Nombre para la cláusula WHERE
                    pst.executeUpdate();
                }
            } else {
                // Si el usuario no existe, inserta un nuevo usuario
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getMail());
                    pst.setString(3, entity.getPhoto());
                    pst.setString(4, entity.getPassword());
                    pst.executeUpdate();
                }
            }
        }
        return entity;
    }

    @Override
    public Artist save(Artist entity) throws SQLException {
        return null;
    }


    public User update(User entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setString(1, entity.getName());
                pst.setString(2,entity.getMail());
                pst.setString(3,entity.getPhoto());
                pst.setString(4,entity.getPassword());

                pst.executeUpdate();
            }
        }
        return entity;
    }

    public User findByNameUser(String name) throws SQLException {
        return findByNameAndPass(name,  FINDBYNAME_USER, FINDBYNAME_pass_USER);
    }
    public User findByNameAndPassUser(String name, String password) throws SQLException {
        return findByNameAndPass(name, password, FINDBYNAME_pass_USER);
    }
    private User findByNameAndPass(String name, String query, String FINDBYNAME_pass_USER) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(query)) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return createUserFromResultSet(resultSet);
            }
        }
        return null;
    }
    private User findByName(String name, String query) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(query)) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return createUserFromResultSet(resultSet);
            }
        }
        return null;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setName(resultSet.getString("name"));
        user.setMail(resultSet.getString("mail"));
        user.setPhoto(resultSet.getString("photo"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }


    @Override
    public void delete(User entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setString(1, entity.getName());
                pst.executeUpdate();
            }
        }

    }




    public void addSubscription(int idList, String userName) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(addsub)) {
            pst.setInt(1, idList);
            pst.setString(2, userName);
            pst.executeUpdate();
        }
    }
    public void deleteSubscription(String nameUser, int idList) throws SQLException {

        try (PreparedStatement pst = this.conn.prepareStatement(deletesub)) {
            pst.setString(1, nameUser);
            pst.setInt(2, idList);
            pst.executeUpdate();
        }
    }
    public boolean isAdmin(String name) throws SQLException {
        // Consulta para buscar un usuario en la tabla admin


        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYNAME_ADMIN)) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();

            // Si resultSet tiene al menos una fila, significa que el usuario existe en la tabla admin
            return resultSet.next();
        }
    }
}
