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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.DAO.*;
import org.example.model.dto.*;
import org.mindrot.jbcrypt.BCrypt;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ControllerUserHome {
    private int selectedListId;  // Variable para almacenar el ID de la lista seleccionada
    SongDAO songDAO = new SongDAO();
    ListDAO listDAO = new ListDAO();
    CommentDAO commentDAO = new CommentDAO();
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
    private Slider barra;

    @FXML
    private Button buttonSong;
    @FXML
    private Button stopButton;
    @FXML
    private Button buttonSongofList;
    @FXML
    private Button buttonAlbun;
    @FXML
    private Button buttonAlbunSong;
    @FXML
    private Button buttonDeleteList;
    @FXML
    private Button buttonexit;
    @FXML
    private Button buttonAddList;
    @FXML
    private Button buttonAddSongofList;
    @FXML
    private Button buttonDelteSongofList;
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
    private TableView<Comment> tableComment;

    @FXML
    private TableColumn<Comment, String> columnnComment;
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
    private ListView<String> listMyList;
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
    @FXML
    private MenuItem addComment;

    @FXML
    private MenuItem listSubcrip;
    @FXML
    private MenuItem activeComment;
    //////////////////////////////////////////////////////////////////Campos de la cancion
    @FXML
    private Label labelDurecion;
    @FXML
    private Label LabeNameSong;
    @FXML
    private Label generoLabel;
    private final SimpleStringProperty selectedSongName = new SimpleStringProperty("");
    private final SimpleStringProperty selectedSongDuration = new SimpleStringProperty("");
    private final SimpleStringProperty selectedSong_archive = new SimpleStringProperty("");
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

    /**
     * funcion para abrir la ventana para crear una lista
     */

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

    /**
     * esta funcion es para modifycar la lista
     *
     * @throws IOException
     */

    @FXML
    private void buttonModifyList() throws IOException {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModListView.fxml"));
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

    /**
     * esta funcion es para crear comentarios  en las listas
     * @throws IOException
     */

    @FXML
    private void buttonAddComment() throws IOException {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addComment.fxml"));
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

    /**
     *
     * @throws IOException
     */
    @FXML
    private void buttonDeleteComment() throws IOException {
        int id_comment = 1;

    }


    // Función para activar o desactivar la tabla de canciones
    // Función para activar la tabla de canciones y desactivar otras
    public void activeTablaSong() {
        tableSong.setVisible(true);
        tableAlbun.setVisible(false);
        panelModify.setVisible(false);
        tableComment.setVisible(false);

        // Desactiva otras tablas si las tienes
    }

    /**
     * Funcion para activar la tabla Albun
     */
    public void activeTablaAlbun() {
        tableSong.setVisible(false);
        tableAlbun.setVisible(true);
        panelModify.setVisible(false);
        tableComment.setVisible(false);


    }

    /**
     * Funcion para activar el panel de modificación de el usuario
     */
    public void activePanelModify() {
        tableSong.setVisible(false);
        tableAlbun.setVisible(false);
        panelModify.setVisible(true);
        tableComment.setVisible(false);


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
        tableComment.setVisible(false);
        panelModify.setVisible(false);
        // Configura las celdas de las columnas para la tabla de canciones
        columnnSong_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnnSong_Name.setCellValueFactory(new PropertyValueFactory<>("name_song"));
        columnnSong_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnnSong_N_repro.setCellValueFactory(new PropertyValueFactory<>("nrepro"));
        columnnSong_Duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        columnnSong_Name_Disk_Song.setCellValueFactory(cellData -> {
            Album album = cellData.getValue().getAlbum();
            String albumName = (album != null) ? album.getName() : "";
            return new SimpleStringProperty(albumName);
        });

        /////

        // Configura las celdas de las columnas para la tabla de álbumes
        columnnName_Albun.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnn_Publication_dateAlbun.setCellValueFactory(new PropertyValueFactory<>("public_time"));
        columnn_N_reproduction.setCellValueFactory(new PropertyValueFactory<>("nrepro"));
        columnn_Albun_NameArtistAlbun.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName_artist().getName()));
        stopButton.setOnAction(event -> stopMp3());


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
            MouseEvent mouseEvent = (MouseEvent) event;
            Object selectedListName = listMyList.getSelectionModel().getSelectedItem();

            if (selectedListName != null) {
                // Llama a la función para obtener la ID de la lista
                handleListSelection(mouseEvent);
            }
        });
        //tabla comentarios


    }

    /**
     * este boton sumara 1 cada vez que le de a reprocucir le numero
     */


    private AdvancedPlayer player;

    private Thread playerThread;
    private boolean isPlaying = false;

    @FXML
    private void buttonReproduction() {
        if (tableAlbun.isVisible()) {
            // Resto del código para álbumes
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

                // Reproduce la canción desde la URL del archivo MP3
                playMp3(selectedSong.getArchive_song());
            }
        }
    }

    private void playMp3(String mp3Url) {
        stopMp3(); // Detener la reproducción actual si la hay

        try {
            FileInputStream fileInputStream = new FileInputStream(mp3Url);
            player = new AdvancedPlayer(fileInputStream);

            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    // Reproducción finalizada
                    stopMp3();
                }
            });

            // Iniciar la reproducción en un nuevo hilo para no bloquear la interfaz de usuario
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    private void stopMp3() {
        if (player != null) {
            player.close();
            player = null;
        }
    }


    /**
     * funcion para ver las canciones de el albun
     */

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

    /**
     * esta funcion es para que se muestre la informacion de la cancion sobre la que clico
     *
     * @param mouseEvent
     */

    @FXML
    public void handleSongSelection(javafx.scene.input.MouseEvent mouseEvent) {
        Song selectedSong = tableSong.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            // Actualiza las propiedades observables con los datos de la canción seleccionada
            selectedSongName.set(selectedSong.getName_song());
            selectedSongDuration.set(selectedSong.getDuration());
            selectedSongGender.set(selectedSong.getGender());
            selectedSong_archive.set(selectedSong.getArchive_song());

            // Actualiza las etiquetas (labels) en la interfaz gráfica

            labelDurecion.setText(selectedSongDuration.get());
            generoLabel.setText(selectedSongGender.get());
            LabeNameSong.setText(selectedSongName.get());
        }
    }

    /**
     * funcion para mostrar todas las canciones
     */

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


    /**
     * funcion para mostrar todas las listas de canciones
     *
     * @param <list>
     */
    @FXML
    private <list> void buttonShowList() {
        try {
            // Llama a tu DAO para obtener todas las listas en la base de datos
            ListDAO listDAO = new ListDAO();
            List<list> allLists = (List<list>) listDAO.findAllNameLists();

            // Convierte la lista de listas en un ObservableList para mostrarla en tu ListView
            ObservableList<list> allListsObservable = FXCollections.observableArrayList(allLists);

            // Asigna las listas al ListView 'listMyList'
            listMyList.setItems((ObservableList<String>) allListsObservable);

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
        }
    }

    /**
     * funcion para guardar la id de la lista sobre la que clico
     *
     * @throws SQLException
     */
    @FXML
    private void handleListSelection(MouseEvent event) {
        String selectedListName = listMyList.getSelectionModel().getSelectedItem();

        if (selectedListName != null) {
            try {
                int listId = listDAO.findIdByName(selectedListName);
                selectedListId = listId;
                System.out.println("ID de la lista seleccionada: " + selectedListId);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database errors
            }
        }
    }

    /**
     * funcion para subcribirme a una lista
     */

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

    /**
     * funcion para borrar la subscripcion
     */

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

    /**
     * estas funcion es para mostrar solo las listas que ha creado el usuario
     */
    @FXML
    private void buttonShowMyList() {
        try {
            // Llama a tu DAO para obtener todas las listas en la base de datos
            ListDAO listDAO = new ListDAO();
            List<String> allLists = listDAO.findAllNameListsByUser(ControllerLogin.getLoggedInUserName());

            // Convierte la lista de listas en un ObservableList para mostrarla en tu ListView
            ObservableList<String> allListsObservable = FXCollections.observableArrayList(allLists);

            // Asigna las listas al ListView 'listMyList'
            listMyList.setItems(allListsObservable);

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
        }
    }

    /**
     * funcion para borras las listas de la base de datos
     */
    @FXML
    private void handleDeleteList() {
        // Verifica que se haya seleccionado una lista antes de intentar borrar
        if (selectedListId != -1) {
            try {
                listDAO.delete(selectedListId);
                showInformation("Lista borrada", "Lista borrada con éxito");
            } catch (SQLException e) {
                showValidationError("Error al borrar la lista");
            }
        } else {
            showValidationError("No se ha seleccionado ninguna lista para borrar.");
        }
    }

    /**
     * funcion para mostrar todas las listas en las que estoy subscrito
     */
    @FXML
    private void buttonShowMyListSub() {
        try {
            // Llama a tu DAO para obtener todas las listas en la base de datos
            ListDAO listDAO = new ListDAO();
            List<String> allLists = listDAO.findSubscribedLists(ControllerLogin.getLoggedInUserName());

            // Convierte la lista de listas en un ObservableList para mostrarla en tu ListView
            ObservableList<String> allListsObservable = FXCollections.observableArrayList(allLists);

            // Asigna las listas al ListView 'listMyList'
            listMyList.setItems(allListsObservable);

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
        }
    }

    /**
     * esta funcion es para mostrar todos los commentarios de la lista seleccionado
     * @param event
     */

    @FXML
    private void showCommentsForList(ActionEvent event) {
        tableComment.setVisible(true);
        try {
            int id = selectedListId;

            // Obtén todos los comentarios de la tabla comment para la lista seleccionada
            List<Comment> comments = commentDAO.findCommentsByListId(selectedListId);

            // Verifica si la lista de comentarios no es nula ni está vacía antes de procesarla
            if (comments != null && !comments.isEmpty()) {
                // Convertir la lista en un ObservableList para mostrarla en la tabla
                ObservableList<Comment> commentsObservable = FXCollections.observableArrayList(comments);

                // Asignar los comentarios a la tabla y configurar las celdas de la columna
                tableComment.setItems(commentsObservable);

                // Configurar las celdas de la columna para mostrar el texto del comentario
                columnnComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
            } else {
                // La lista de comentarios es nula o está vacía, puedes mostrar un mensaje o manejarlo según tu lógica
                System.out.println("La lista de comentarios es nula o está vacía");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de la base de datos
        }
    }


    /**
     * esta funcion es para añadir canciones a las listas
     */
    @FXML
    private void addSongonList() {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addSongofList.fxml"));
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

    /**
     * esta funcion es para  ver todas las canciones de la lista
     * @param event
     */
    @FXML
    private void listSongofList(ActionEvent event) {
        tableSong.setVisible(true);
        if (selectedListId > 0) {
            try {
                // Llama a la función `findAll` en tu DAO para obtener todas las canciones de la base de datos
                List<Song> allSongs = listDAO.findSongsByListId(selectedListId);

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

        }else{
            System.out.println();

        }
    }

    /**
     * esta funcion es para borrar la cancion de las listas
     * @param event
     */
    @FXML
    private void deleteSelectedSong(ActionEvent event) {
        // Obtén la canción seleccionada desde la tabla
        Song selectedSong = tableSong.getSelectionModel().getSelectedItem();

        if (selectedSong != null) {
            try {
                // Obtén el ID de la lista seleccionada
                int listId = selectedListId;

                // Elimina la canción seleccionada de la lista
                listDAO.deleteSongOfList(selectedSong.getId(), listId);

                // Vuelve a cargar la lista de canciones después de la eliminación
                listSongofList(event);
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar errores de la base de datos
            }
        } else {
            System.out.println("Selecciona una canción para eliminar.");
        }
    }
}
