package org.example.model.DAO;

import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import org.example.model.dto.List;
import javafx.collections.ObservableList;

public class ListDAO {
    private static final String bd = "spotifyproject";
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String login = "root";
    private static final String password = "";
    private static Connection c = null;


    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public static void conectar() {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventos_programacion", "root", "");
        } catch (SQLException var1) {
            var1.printStackTrace();
        }

    }

    public static void close() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException var1) {
            var1.printStackTrace();
        }

    }
    public static void addList(List b) throws SQLException {
        conectar();
        PreparedStatement stat = null;
        stat = c.prepareStatement("insert into list(name_user,name_list,description,id) values(?,?,?,?)");
        stat.setString(1, b.getName_list());
        stat.setString(2, b.getName_user());
        stat.setString(2, b.getDescription());
        stat.setInt(3, b.getId());
        stat.executeUpdate();
    }
    public static ObservableList<List> getAllList() throws SQLException {
        conectar();
        ObservableList<List> obs = FXCollections.observableArrayList();
        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery("SELECT name_list,name_user,descripcion,id from LIST");

        while(rs.next()) {
            new List();
            String nameL = rs.getString("name_list");
            String nameU = rs.getString("name_user");
            String  Ldescripition = rs.getString("descripcion");
            int Lid = Integer.parseInt(rs.getString("id"));
            List e = new List(Lid,nameU,Ldescripition,nameL);
            obs.add(e);
        }

        return obs;
    }

}
