package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.model.DAO.ListDAO;
import org.example.model.DAO.UserDAO;
import org.example.model.dto.User;
import org.example.model.dto.list;

import java.sql.SQLException;
import java.util.List;

public class ControllerCreateList {
    @FXML
    private TextField nameList;
    @FXML
    private TextArea descriptionList;
    @FXML
    private Button addList;

    private static ControllerLogin HomeLogin;
    // Puedes obtener el valor de loggedInUserName desde tu aplicación
    private static String loggedInUserName = HomeLogin.getLoggedInUserName();

    // Otros métodos y atributos...

    // Agregar esta función para agregar una lista a la base de datos
    @FXML
    public void addList() {
        try {
            list lists=new list();
            ListDAO listDAO=new ListDAO();
            String name = nameList.getText();
            String description = descriptionList.getText();
            String Name_user = ControllerLogin.getLoggedInUserName();
            list newlist = new list(0,name, description,Name_user);
            listDAO.save(newlist);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("lista creada");
            alert.setHeaderText(null);
            alert.setContentText("lista creda");
            alert.showAndWait();

            // Limpia los campos de texto después de agregar el usuario

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de base de datos");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo crear la lista en la base de datos.");
            alert.showAndWait();
            e.printStackTrace();


        }
    }
}


