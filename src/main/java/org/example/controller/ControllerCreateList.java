package org.example.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.model.dto.List;

import static org.example.controller.ControllerLogin.getLoggedInUserName;
import static org.example.model.DAO.ListDAO.addList;

public class ControllerCreateList {

    @FXML
    private TextArea DescriptionField;

    @FXML
    private TextField NameField;

    @FXML
    private Button Aceptarbtt;

    public void Createnewlist(MouseEvent mouseEvent) throws SQLException {
        List newlist = new List(1,DescriptionField.getText(), NameField.getText(), getLoggedInUserName());
        addList(newlist);
    }

    public void initialize() {
        String loggedInUserName = getLoggedInUserName();
    }
}
