package org.example.model.DAO;

import org.example.interfaceDAO.iDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.conexion.Connect;
import org.example.conexion.ConnectionData;
import org.example.model.dto.List;
import java.sql.*;
import java.util.ArrayList;


public class ListDAO extends List implements iDAO<List, Integer> {

    private static Connection con;

    public ListDAO(int id, String description, String name_list, String name_user, Connection con) {
        super(id, description, name_list, name_user);
        this.con = con;
    }

    public ListDAO() {
        this.con = Connect.getConnect() ;
    }



    public static void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spotifyproject", "root", "");
        } catch (SQLException var1) {
            var1.printStackTrace();
        }

    }

    public static void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException var1) {
            var1.printStackTrace();
        }

    }
    public static void addList(List b) throws SQLException {
        conectar();
        PreparedStatement stat = null;
        stat = con.prepareStatement("insert into list(id,name_user,name_list,description) values(null,?,?,?);");
        stat.setString(2, b.getName_list());
        stat.setString(1, String.valueOf(b.getName_user()));
        stat.setString(3, b.getDescription());
        stat.executeUpdate();
    }
    public static ArrayList<List> getAllList() throws SQLException {
        conectar();
        ArrayList<List> obs = new ArrayList<>();
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("SELECT name_list,name_user,description,id from LIST");

        while(rs.next()) {
            new List();
            String nameL = rs.getString("name_list");
            String nameU = rs.getString("name_user");
            String  Ldescripition = rs.getString("description");
            int Lid = Integer.parseInt(rs.getString("id"));
            List e = new List(Lid,nameL,Ldescripition,nameU);
            obs.add(e);
        }

        return obs;
    }

    public static void Modlist(List b) throws SQLException {
        conectar();
        PreparedStatement stat = null;
        stat = con.prepareStatement("UPDATE LIST " +
                "SET List.name_list = ?, \n" +
                "    List.description = ?\n " +
                "WHERE list.id = ?;");
        stat.setString(1, b.getName_list());
        stat.setString(2, b.getDescription());
        stat.setInt(3, b.getId());
        stat.executeUpdate();
    }

    @Override
    public java.util.List<List> findAll() throws SQLException {
        return null;
    }

    @Override
    public List findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List save(List entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(List entity) throws SQLException {

    }

    public static void deleteById(int id) throws SQLException {
        conectar();
        PreparedStatement stat = null;
        stat = con.prepareStatement("DELETE FROM list WHERE list.id = ?");
        stat.setInt(1, id);
        stat.executeUpdate();
    }
}
