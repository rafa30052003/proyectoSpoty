package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.model.DAO.ListDAO;
import org.example.model.dto.list;

import java.sql.SQLException;

public class ControllerModList {
    @FXML
    private TextField nameList;
    @FXML
    private TextArea descriptionList;
    @FXML
    private Button addList;

    private static ControllerLogin HomeLogin;

    private static String loggedInUserName = HomeLogin.getLoggedInUserName();

    /**
     *
     */
    @FXML
    public void modifyList() {
        try {
            list lists=new list();
            ListDAO listDAO=new ListDAO();
            String name = nameList.getText();
            String description = descriptionList.getText();
            String Name_user = ControllerLogin.getLoggedInUserName();
            list newlist = new list(9,name, description,Name_user);
            listDAO.update(newlist);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("lista creada");
            alert.setHeaderText(null);
            alert.setContentText("lista modificada");
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
