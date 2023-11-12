package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.DAO.UserDAO;
import org.example.model.dto.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerAddUser {
    @FXML
    private TextField textName;
    @FXML
    private TextField textmail;
    @FXML
    private PasswordField password;
    @FXML
    private Button buttonImg;
    @FXML
    private Button buttonexit;
    @FXML
    private Button buttonadd;

    private UserDAO userDAO = new UserDAO();

    private String photoPath = ""; // Ruta de la imagen seleccionada

    /**
     * funcion para  salir de el fxml
     * @throws IOException
     */
    @FXML
    private void exit() throws IOException {
        App.setRoot("login");
    }

    /**
     * Funcion poara crear tu usuario en la base de datos
     * @param event
     */
    @FXML
    private void addUser(ActionEvent event) {
        if (areFieldsFilled()) {
            String name = textName.getText();
            String mail = textmail.getText();
            String userPassword = password.getText();

            // Validar formato de correo electrónico
            if (!Utils.validateEmail(mail)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Formato de correo incorrecto");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, introduzca un correo electrónico válido.");
                alert.showAndWait();
                return;
            }

            if (photoPath.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Imagen no seleccionada");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione una imagen.");
                alert.showAndWait();
                return;
            }

            try {
                User existingUser = userDAO.findByNameUser(name);

                if (existingUser != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nombre duplicado");
                    alert.setHeaderText(null);
                    alert.setContentText("El nombre de usuario ya existe. Por favor, elija otro.");
                    alert.showAndWait();
                    return;
                }

                // Hashear la contraseña antes de guardarla
                String hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());

                User newUser = new User(name, mail, photoPath, hashedPassword);
                userDAO.save(newUser);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Usuario Agregado");
                alert.setHeaderText(null);
                alert.setContentText("Usuario creado con éxito");
                alert.showAndWait();

                // Limpia los campos de texto después de agregar el usuario
                textName.clear();
                textmail.clear();
                password.clear();
                photoPath = ""; // Restablece la ruta de la imagen
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de base de datos");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo crear el usuario en la base de datos.");
                alert.showAndWait();
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();
        }
    }

    /**
     * Funcion para subir una imagen a la Base de datos
     * @param event
     */
    @FXML
    private void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Guarda la ruta del archivo seleccionado en la variable photoPath
            photoPath = selectedFile.getAbsolutePath();
        }
    }

    /**
     * Funcion que revisa que los campos estan completos
     * @return devuelve un true si esta completo
     */
    private boolean areFieldsFilled() {
        return !textName.getText().isEmpty() &&
                !textmail.getText().isEmpty() &&
                !password.getText().isEmpty();
    }
}
