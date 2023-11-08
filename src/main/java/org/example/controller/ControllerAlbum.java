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
import org.example.model.dto.Album;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ControllerAlbum {
    @FXML
    private TableColumn<Album, String> columArt;

    @FXML
    private TableColumn<Album, String> columFecha;

    @FXML
    private TableColumn<Album, String> columFoto;

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
        columFoto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoto()));
        columFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPublic_time()).asString());
        columRepro.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getnRepro()).asObject());
        columArt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName_artist().getName()));
        try {
            List<Album> albumList = albumDAO.findAll();
            ObservableList<Album> observableAlbumList = FXCollections.observableArrayList(albumList);
            tableView.setItems(observableAlbumList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        buscar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    filtrarAlbumesPorNombre(newValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    void eliminarAlbum(ActionEvent event) {
        Album selectedAlbum = tableView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            try {
                albumDAO.delete(selectedAlbum);
                tableView.getItems().remove(selectedAlbum);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void editarAlbum(ActionEvent event) {
        Album selectedAlbum = tableView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Editar Nombre del Álbum");
            dialog.setHeaderText("Ingresa el nuevo nombre del álbum:");

            TextField newNameField = new TextField(selectedAlbum.getName());

            GridPane grid = new GridPane();
            grid.add(new Label("Nuevo Nombre:"), 1, 1);
            grid.add(newNameField, 2, 1);

            dialog.getDialogPane().setContent(grid);

            ButtonType saveButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    String newName = newNameField.getText();
                    try {
                        AlbumDAO albumDAO = new AlbumDAO(Connect.getConnect());
                        albumDAO.updateAlbumName(newName, selectedAlbum.getName());

                        List<Album> updatedAlbums = albumDAO.findAll();
                        ObservableList<Album> observableAlbumList = FXCollections.observableArrayList(updatedAlbums);
                        tableView.setItems(observableAlbumList);
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
