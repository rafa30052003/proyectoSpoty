package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.App;
import org.example.conexion.Connect;
import org.example.model.DAO.AlbumDAO;
import org.example.model.DAO.ArtistDAO;
import org.example.model.dto.Album;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.model.dto.Artist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ControllerAlbum {

    @FXML
    private TableColumn<Album, String> columArt;

    @FXML
    private TableColumn<Album, String> columFecha;


    @FXML
    private TableColumn<Album, String> columNombre;

    @FXML
    private TableColumn<Album, Integer> columRepro;

    @FXML
    private Button eliminar;

    @FXML
    private Button editar;

    @FXML
    private Button insertar;

    @FXML
    private TableView<Album> tableView;
    @FXML
    private TextField buscar;

    private AlbumDAO albumDAO;

    public ControllerAlbum() {
        Connection conn = Connect.getConnect();
        albumDAO = new AlbumDAO(conn);
    }
    @FXML
    public void initialize() {
        columNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        columFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPublic_time()).asString());
        columRepro.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNrepro()).asObject());
        columArt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName_artist().getName()));

        try {
            List<Album> albumList = albumDAO.findAll();
            ObservableList<Album> observableAlbumList = FXCollections.observableArrayList(albumList);
            tableView.setItems(observableAlbumList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buscar.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Llama al método filtrarAlbumesPorNombre con el nuevo valor del TextField
                try {
                    filtrarAlbumesPorNombre(newValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                tableView.getSelectionModel().clearSelection();
            }
        });

    }

    @FXML
    void eliminarAlbum(ActionEvent event) {
        Album selectedAlbum = tableView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que quieres eliminar este álbum?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    albumDAO.delete(selectedAlbum);
                    tableView.getItems().remove(selectedAlbum);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void editarAlbum(ActionEvent event) throws SQLException {
        Album selectedAlbum = tableView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Editar Información del Álbum");
            dialog.setHeaderText("Ingresa la nueva información del álbum:");

            TextField newNameField = new TextField(selectedAlbum.getName());
            LocalDate newPublicationDateField = new DatePicker().getValue();

            ComboBox<String> artistComboBox = new ComboBox<>();
            ArtistDAO artistDAO = new ArtistDAO();
            List<String> artistNames = artistDAO.findNames();
            artistComboBox.getItems().addAll(artistNames);

            GridPane grid = new GridPane();
            grid.add(new Label("Nuevo Nombre:"), 1, 1);
            grid.add(newNameField, 2, 1);

            grid.add(new Label("Nueva Fecha de Publicación:"), 1, 2);
            DatePicker datePicker = new DatePicker(newPublicationDateField);
            grid.add(datePicker, 2, 2);

            grid.add(new Label("Nuevo Nombre del Artista:"), 1, 3);
            grid.add(artistComboBox, 2, 3);

            dialog.getDialogPane().setContent(grid);

            ButtonType saveButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    String newName = newNameField.getText();
                    LocalDate newPublicationDate = datePicker.getValue();
                    String newArtistName = artistComboBox.getValue();

                    try {
                        AlbumDAO albumDAO = new AlbumDAO(Connect.getConnect());
                        Artist newArtist = new Artist();
                        newArtist.setName(newArtistName);

                        albumDAO.updateAlbum(newName, Date.valueOf(newPublicationDate), newArtist, selectedAlbum.getName());

                        List<Album> updatedAlbums = albumDAO.findAll();
                        ObservableList<Album> observableAlbumList = FXCollections.observableArrayList(updatedAlbums);
                        tableView.setItems(observableAlbumList);

                        // Refrescar la tabla
                        tableView.refresh();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            });

            dialog.showAndWait();
        }
    }




    @FXML
    void buscarNombre(ActionEvent event) {
        String letra = buscar.getText();
        try {
            filtrarAlbumesPorNombre(letra);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void filtrarAlbumesPorNombre(String letra) throws SQLException {
        List<Album> albumesFiltrados = (List<Album>) albumDAO.findByName(letra);
        if(albumesFiltrados != null) {
            ObservableList<Album> listaObservableAlbumes = FXCollections.observableArrayList(albumesFiltrados);
            tableView.setItems(listaObservableAlbumes);
        } else {
            tableView.setItems(FXCollections.emptyObservableList());
        }
    }

    @FXML
    void btninsertar(ActionEvent event) throws  IOException {
        App.setRoot("addAlbum");
    }

}