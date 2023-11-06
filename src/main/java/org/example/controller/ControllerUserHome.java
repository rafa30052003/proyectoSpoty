package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.DAO.UserDAO;
import org.example.model.dto.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ControllerUserHome {
    @FXML
    private AnchorPane panelModify;
    @FXML
    private AnchorPane panelDelete;
    @FXML
    private TableView tableSong;
    @FXML
    private  TableView tableAlbun;
    @FXML
    private  TableView tableSongMyList;
    @FXML
    private  TableView tableListSongSubcription;
    @FXML
    private Button buttonSong;
    @FXML
    private Button buttonAlbun;
    @FXML
    private Button buttonexit;
    @FXML
    private Button buttonList;
    @FXML
    private Button buttonOption;
    @FXML
    private MenuItem OpModifyUser;
    @FXML
    private MenuItem OpDeleteUser;
    @FXML
    private ImageView imgUser;
    @FXML
    private Label NameUser;
    @FXML
    private Label labelName;
    @FXML
    private ListView ListSong;
    @FXML
    private TextField search;
    //modify
    ////////////////////////////////////////////////////////
    @FXML
    private TextField textName;
    @FXML
    private TextField textmail;
    @FXML
    private PasswordField password;
    @FXML
    private Button buttonImg;
    @FXML
    private Button buttonModify;
    private String photoPath = "";
    private UserDAO userDAO = new UserDAO();
    /////////////////////////////////////////////////////////////


    /**
     * esta funcion es para volver al login
     * @throws IOException
     */
    @FXML
    private void buttonExit() throws IOException {
        App.setRoot("login");
    }


    // Función para activar o desactivar la tabla de canciones
    // Función para activar la tabla de canciones y desactivar otras
    public void activeTablaSong() {
        tableSong.setVisible(true);
        tableAlbun.setVisible(false);
        panelModify.setVisible(false);

        // Desactiva otras tablas si las tienes
    }

    // Función para activar la tabla de álbumes y desactivar otras
    public void activeTablaAlbun() {
        tableSong.setVisible(false);
        tableAlbun.setVisible(true);
        panelModify.setVisible(false);

        // Desactiva otras tablas si las tienes
    }

    public void activePanelModify() {
        tableSong.setVisible(false);
        tableAlbun.setVisible(false);
        panelModify.setVisible(true);


        // Accede a los datos del usuario logueado
        User loggedInUser = getLoggedInUser();

        // Verifica si el usuario está logueado y sus datos no son nulos
        if (loggedInUser != null) {
            textName.setText(loggedInUser.getName());
            textmail.setText(loggedInUser.getMail());
            // No establece la contraseña automáticamente
            password.clear();
            // Establece la foto actual del usuario
            photoPath = loggedInUser.getPhoto();
            // Actualiza la imagen en la interfaz si existe una foto
            if (!photoPath.isEmpty()) {
                Image userImage = new Image("file:" + photoPath);
                imgUser.setImage(userImage);
            }
        }
    }

    private User getLoggedInUser() {
        // Accede a los datos del usuario logueado desde ControllerLogin
        String loggedInUserName = ControllerLogin.getLoggedInUserName();
        String loggedInUserMail = ControllerLogin.getLoggedInUserMail();
        String loggedInUserPhotoPath = ControllerLogin.getLoggedInUserPhoto();

        // Verifica que todos los campos obligatorios estén llenos
        if (!loggedInUserName.isEmpty() && !loggedInUserMail.isEmpty() && !loggedInUserPhotoPath.isEmpty()) {
            // Devuelve un objeto User con los datos
            return new User(loggedInUserName, loggedInUserMail, loggedInUserPhotoPath, null);
        } else {
            // Si falta alguno de los campos obligatorios, devuelve null
            return null;
        }
    }

    public void modifyUser() {
        User loggedInUser = getLoggedInUser();

        if (loggedInUser == null) {
            // Manejar el caso en el que no se pueda obtener el usuario logueado
            return;
        }

        String currentName = loggedInUser.getName();
        String currentMail = loggedInUser.getMail();
        String currentPhotoPath = loggedInUser.getPhoto();

        String name = textName.getText();
        String mail = textmail.getText();
        String userPassword = password.getText();

        if (photoPath.isEmpty()) {
            photoPath = currentPhotoPath; // Mantén la foto actual si no se selecciona una nueva
        }

        try {
            // Verifica si se ha modificado el nombre de usuario
            if (!name.equals(currentName)) {
                // Verifica si el nuevo nombre ya existe
                User existingUser = userDAO.findByNameUser(name);
                if (existingUser != null && !existingUser.getName().equals(currentName)) {
                    showValidationError("El nombre de usuario ya existe. Por favor, elija otro.");
                    return;
                }
            }

            // Actualiza los campos del usuario con los valores ingresados
            loggedInUser.setName(name);
            loggedInUser.setMail(mail);

            // Comprueba si se ha modificado la contraseña
            if (!userPassword.isEmpty()) {
                // Hashear la nueva contraseña antes de guardarla
                userPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());
                loggedInUser.setPassword(userPassword);
            }

            // Actualiza la foto actual del usuario
            loggedInUser.setPhoto(photoPath);

            // Realiza la operación de actualización en la base de datos
            userDAO.update(loggedInUser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario Modificado");
            alert.setHeaderText(null);
            alert.setContentText("Usuario modificado con éxito");
            alert.showAndWait();

            // No necesitas limpiar los campos de texto después de modificar el usuario, ya que se reflejarán los nuevos valores
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de base de datos");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo modificar el usuario en la base de datos.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    @FXML
    private void deleteUser() {
        // Obtener la contraseña del usuario a través de un cuadro de diálogo de entrada de texto
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Eliminar cuenta");
        dialog.setHeaderText("Confirmación de eliminación de cuenta");
        dialog.setContentText("Por favor, ingrese su contraseña para confirmar la eliminación:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(inputPassword -> {
            // Obtener el nombre de usuario del controlador de inicio de sesión
            String loggedInUserName = ControllerLogin.getLoggedInUserName();
            String loggedInUserPasswordHash = ControllerLogin.getLoggedInUserPassword(); // Obtener la contraseña con hash

            // Verificar si la contraseña ingresada coincide con la contraseña con hash
            if (BCrypt.checkpw(inputPassword, loggedInUserPasswordHash)) {
                // Las contraseñas coinciden, se puede eliminar al usuario
                try {
                    UserDAO userDAO = new UserDAO();
                    // Buscar el usuario en la base de datos
                    User userToDelete = userDAO.findByNameUser(loggedInUserName);
                    if (userToDelete != null) {
                        // Eliminar el usuario
                        userDAO.delete(userToDelete);

                        // Mostrar un mensaje de éxito y redirigir al usuario al inicio de sesión
                        showInformation("Cuenta eliminada", "Su cuenta ha sido eliminada correctamente.");
                        App.setRoot("login");
                    }
                } catch (SQLException | IOException e) {
                    // Manejar errores de eliminación de cuenta
                    showValidationError("No se pudo eliminar la cuenta. Por favor, inténtelo de nuevo más tarde.");
                }
            } else {
                // La contraseña ingresada no coincide
                showValidationError("La contraseña ingresada no coincide con la utilizada para iniciar sesión. Por favor, inténtelo de nuevo.");
            }
        });
    }





    private void showInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





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


    public void initialize() {
        // Accede a los datos del usuario logueado desde ControllerLogin
        String loggedInUserName = ControllerLogin.getLoggedInUserName();
        String loggedInUserMail = ControllerLogin.getLoggedInUserMail();
        String loggedInUserPhotoPath = ControllerLogin.getLoggedInUserPhoto();
        // Establece el nombre de usuario en el Label
        NameUser.setText(loggedInUserName);
        // Carga la imagen del usuario en imgUser
        Image userImage = new Image("file:" + loggedInUserPhotoPath);
        imgUser.setImage(userImage);
        tableSong.setVisible(false);
        tableAlbun.setVisible(false);
        panelModify.setVisible(false);

    }

}
