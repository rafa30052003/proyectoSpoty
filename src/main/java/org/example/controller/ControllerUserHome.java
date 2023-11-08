package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.DAO.AlbumDAO;
import org.example.model.DAO.ListDAO;
import org.example.model.DAO.SongDAO;
import org.example.model.DAO.UserDAO;
import org.example.model.dto.Album;
import org.example.model.dto.Song;
import org.example.model.dto.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ControllerUserHome {
    private int selectedListId;  // Variable para almacenar el ID de la lista seleccionada
    SongDAO songDAO = new SongDAO();
    ListDAO listDAO = new ListDAO();
    @FXML
    private AnchorPane panelModify;
    //////////////////////////////////////las cancionas0
    @FXML
    private TableView<Song> tableSong;

    @FXML
    private TableColumn<Song, String> columnnSong_Name;

    @FXML
    private TableColumn<Song, Integer> columnnSong_Id;

    @FXML
    private TableColumn<Song, String> columnnSong_Duration;

    @FXML
    private TableColumn<Song, String> columnnSong_Gender;

    @FXML
    private TableColumn<Song, Integer> columnnSong_N_repro;

    @FXML
    private TableColumn<Song, String> columnnSong_Name_Disk_Song;

    ///////////////////////////////// los albuns
    @FXML
    private TableView<Album> tableAlbun;
    @FXML
    private TableColumn<Album, String> columnnName_Albun;
    @FXML
    private TableColumn<Album, Date> columnn_Publication_dateAlbun;
    @FXML
    private TableColumn<Album, Integer> columnn_N_reproduction;
    @FXML
    private TableColumn<Album, String> columnn_Albun_NameArtistAlbun;

    //////////////////////////////// las listas

    @FXML
    private Button buttonSong;
    @FXML
    private Button buttonAlbun;
    @FXML
    private Button buttonAlbunSong;
    @FXML
    private Button buttonexit;
    @FXML
    private Button buttonAddList;
    @FXML
    private Button buttonModifyList;
    @FXML
    private Button buttonReproduction;///
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
    ///////////////////////////////////////////////////////////// listas de canciones
    @FXML
    private ListView<List> listMyList;
    @FXML
    private MenuItem addSub;
    @FXML
    private MenuItem deleteSub;
    @FXML
    private MenuItem mylist;
    @FXML
    private MenuItem list;
    @FXML
    private MenuItem listSub;
    //////////////////////////////////////////////////////////////////Campos de la cancion
    @FXML
    private Label labelDurecion;
    @FXML
    private Label LabeNameSong;
    @FXML
    private Label generoLabel;
    private final SimpleStringProperty selectedSongName = new SimpleStringProperty("");
    private final SimpleStringProperty selectedSongDuration = new SimpleStringProperty("");
    private final SimpleStringProperty selectedSongGender = new SimpleStringProperty("");


    /**
     * esta funcion es para volver al login
     *
     * @throws IOException
     */
    @FXML
    private void buttonExit() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void buttonCreateList() {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateListView.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena y ventana
            Stage subStage = new Stage();
            subStage.initModality(Modality.APPLICATION_MODAL); // Esta línea hace que la ventana sea modal
            subStage.setTitle("Subventana");

            // Establecer la escena en la nueva ventana
            Scene scene = new Scene(root);
            subStage.setScene(scene);

            // Mostrar la nueva ventana
            subStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonModifyList() throws IOException {
        App.setRoot("ModListView");
    }


    // Función para activar o desactivar la tabla de canciones
    // Función para activar la tabla de canciones y desactivar otras
    public void activeTablaSong() {
        tableSong.setVisible(true);
        tableAlbun.setVisible(false);
        panelModify.setVisible(false);

        // Desactiva otras tablas si las tienes
    }

    /**
     * Funcion para activar la tabla Albun
     */
    public void activeTablaAlbun() {
        tableSong.setVisible(false);
        tableAlbun.setVisible(true);
        panelModify.setVisible(false);


    }

    /**
     * Funcion para activar el panel de modificación de el usuario
     */
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

    /**
     * trae los datos de el usuario logeado
     *
     * @return
     */
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

    /**
     * funcion para modificar los campos de el usuario en la tabla user
     */

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

    /**
     * funcion que habre una sub ventana para pedir la contraseña de el usuario para poder eliminar la cuenta
     */
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

    //mensages de errores
    private void showInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //mensages de error
    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * fucnion para subir una imagen a la base de datos
     *
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
     * esta fucion inicaliza todos los datos tanto de las tablas como las listas y los datos de el usuairo
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
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
        // Configura las celdas de las columnas para la tabla de canciones
        columnnSong_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnnSong_Name.setCellValueFactory(new PropertyValueFactory<>("name_song"));
        columnnSong_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnnSong_N_repro.setCellValueFactory(new PropertyValueFactory<>("nrepro"));
        columnnSong_Duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        columnnSong_Name_Disk_Song.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlbum().getName()));
        /////

        // Configura las celdas de las columnas para la tabla de álbumes
        columnnName_Albun.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnn_Publication_dateAlbun.setCellValueFactory(new PropertyValueFactory<>("public_time"));
        columnn_N_reproduction.setCellValueFactory(new PropertyValueFactory<>("nrepro"));
        columnn_Albun_NameArtistAlbun.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName_artist().getName()));




        // Obtén los datos de álbumes desde la base de datos
        // Obtén los datos de canciones desde la base de datos
        try {
            SongDAO songDAO = new SongDAO();
            List<Song> songs = songDAO.findAll();
            ObservableList<Song> songObservableList = FXCollections.observableArrayList(songs);

            // Asigna los datos de álbumes a la tabla
            tableSong.setItems(songObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja los errores de base de datos
        }
        //inicializa los datos de albun
        try {
            AlbumDAO albumDAO = new AlbumDAO();
            List<Album> albums = albumDAO.findAll();
            ObservableList<Album> albumObservableList = FXCollections.observableArrayList(albums);

            // Asigna los datos de álbumes a la tabla
            tableAlbun.setItems(albumObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja los errores de base de datos
        }
        listMyList.setOnMouseClicked(event -> {
            List selectedListName = listMyList.getSelectionModel().getSelectedItem();
            if (selectedListName != null) {
                // Llama a la función para obtener la ID de la lista
                try {
                    handleListSelection(selectedListName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }

    /**
     * este boton sumara 1 cada vez que le de a reprocucir le numero
     */
    @FXML
    private void buttonReproduction() {
        if (tableAlbun.isVisible()) {
            // Si la tabla de álbumes está activa
            Album selectedAlbum = tableAlbun.getSelectionModel().getSelectedItem();
            if (selectedAlbum != null) {
                // Incrementa el contador de reproducciones del álbum en 1
                selectedAlbum.setNrepro(selectedAlbum.getNrepro() + 1);

                // Actualiza la tabla de álbumes reflejando los cambios en la interfaz gráfica
                tableAlbun.refresh(); // Actualiza la tabla

                // Llama a la función de actualización en la base de datos
                try {
                    AlbumDAO albumDAO = new AlbumDAO();
                    albumDAO.updateReproductionCount(selectedAlbum);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Manejar errores de actualización en la base de datos
                }
            }
        } else if (tableSong.isVisible()) {
            // Si la tabla de canciones está activa
            Song selectedSong = tableSong.getSelectionModel().getSelectedItem();
            if (selectedSong != null) {
                // Incrementa el contador de reproducciones de la canción en 1
                selectedSong.setNrepro(selectedSong.getNrepro() + 1);

                // Actualiza la tabla de canciones reflejando los cambios en la interfaz gráfica
                tableSong.refresh(); // Actualiza la tabla

                // Llama a la función de actualización en la base de datos
                try {
                    SongDAO songDAO = new SongDAO();
                    songDAO.updateReproductionCount(selectedSong);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Manejar errores de actualización en la base de datos
                }
            }
        }
    }

    @FXML
    private void showAlbumSongs() {
        // Obtén el álbum seleccionado en la tabla de álbumes
        Album selectedAlbum = tableAlbun.getSelectionModel().getSelectedItem();
        AlbumDAO albunDao = new AlbumDAO();

        if (selectedAlbum != null) {
            try {
                // Llama a la función `findSongsByAlbumName` en tu DAO para obtener las canciones del álbum
                List<Song> albumSongs = albunDao.findSongsByAlbumName(selectedAlbum.getName());

                // Convierte la lista de canciones en un ObservableList para mostrarla en la tabla
                ObservableList<Song> albumSongsObservable = FXCollections.observableArrayList(albumSongs);

                // Establece los datos en la tabla de canciones
                tableSong.setItems(albumSongsObservable);

                // Activa la tabla de canciones y desactiva la tabla de álbumes
                activeTablaSong();
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar errores de base de datos
            }
        }
    }

    @FXML
    public void handleSongSelection(javafx.scene.input.MouseEvent mouseEvent) {
        Song selectedSong = tableSong.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            // Actualiza las propiedades observables con los datos de la canción seleccionada
            selectedSongName.set(selectedSong.getName_song());
            selectedSongDuration.set(selectedSong.getDuration());
            selectedSongGender.set(selectedSong.getGender());

            // Actualiza las etiquetas (labels) en la interfaz gráfica

            labelDurecion.setText(selectedSongDuration.get());
            generoLabel.setText(selectedSongGender.get());
            LabeNameSong.setText(selectedSongName.get());
        }
    }

    @FXML
    private void buttonShowAllSongs() {
        try {
            // Llama a la función `findAll` en tu DAO para obtener todas las canciones de la base de datos
            List<Song> allSongs = songDAO.findAll();

            // Convierte la lista de canciones en un ObservableList para mostrarla en la tabla
            ObservableList<Song> allSongsObservable = FXCollections.observableArrayList(allSongs);

            // Establece los datos en la tabla de canciones
            tableSong.setItems(allSongsObservable);

            // Activa la tabla de canciones y desactiva la tabla de álbumes
            activeTablaSong();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
        }
    }


    @FXML
    private void buttonShowList() {
        try {
            // Llama a tu DAO para obtener todas las listas en la base de datos
            ListDAO listDAO = new ListDAO();
            List<List> allLists = Collections.singletonList(listDAO.findAll());

            // Convierte la lista de listas en un ObservableList para mostrarla en tu ListView
            ObservableList<List> allListsObservable = FXCollections.observableArrayList(allLists);

            // Asigna las listas al ListView 'listMyList'
            listMyList.setItems(allListsObservable);

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
        }
    }
    private void handleListSelection(List selectedListName) throws SQLException {
        try {
            // Debes llamar a tu DAO para obtener el ID de la lista en función del nombre
            int listId = listDAO.findById(selectedListName.toString()).getId();

            // Asigna el ID de la lista a la variable selectedListId
            selectedListId = listId;
            System.out.println("ID de la lista seleccionada: " + selectedListId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
        }
    }
    @FXML
    private void addSub() {
        // Obtén el nombre de usuario logueado desde ControllerLogin
        String loggedInUserName = ControllerLogin.getLoggedInUserName();

        if (loggedInUserName != null && selectedListId > 0) {
            // Llama a la función para agregar una suscripción
            try {
                userDAO.addSubscription(selectedListId, loggedInUserName);
                showInformation("Suscripción Agregada", "Has sido suscrito a la lista seleccionada.");
            } catch (SQLException e) {
                showValidationError("No se pudo agregar la suscripción. Por favor, inténtelo de nuevo más tarde.");
            }
        } else {
            showValidationError("Por favor, seleccione una lista para suscribirse.");
        }
    }

    @FXML
    private void deleteSub() {
        // Obtén el nombre de usuario logueado desde ControllerLogin
        String loggedInUserName = ControllerLogin.getLoggedInUserName();

        if (loggedInUserName != null && selectedListId > 0) {
            // Llama a la función para eliminar la suscripción
            try {
                userDAO.deleteSubscription(loggedInUserName, selectedListId);
                showInformation("Suscripción Eliminada", "Has sido eliminado de la lista seleccionada.");
            } catch (SQLException e) {
                showValidationError("No se pudo eliminar la suscripción. Por favor, inténtelo de nuevo más tarde.");
            }
        } else {
            showValidationError("Por favor, seleccione una lista para eliminar la suscripción.");
        }
    }
}









