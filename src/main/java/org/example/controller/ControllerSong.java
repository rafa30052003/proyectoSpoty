package org.example.controller;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.example.App;
import org.example.model.DAO.AlbumDAO;
import org.example.model.DAO.SongDAO;


import org.example.model.dto.Album;
import org.example.model.dto.Artist;
import org.example.model.dto.Nationality;
import org.example.model.dto.Song;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerSong implements Initializable {
    SongDAO sdao = new SongDAO();
    AlbumDAO adao = new AlbumDAO();

    private List <Song> songsList = (List<Song>) sdao.findAll();
    private final ObservableList<Song> listaActualizable = FXCollections.observableArrayList(songsList);

    @FXML private TableView<Song> mysongs;
    @FXML private TableColumn<Song, Integer> id;
    @FXML private TableColumn<Song, String> name;
    @FXML private TableColumn<Song, String> duration;
    @FXML private TableColumn<Song, String> gender;
    @FXML private TableColumn<Song, Integer> repro;
    @FXML private TableColumn<Song, Album> album;
    @FXML private TextField search;
    public ControllerSong() throws SQLException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.allSong();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mysongs.setItems(listaActualizable);
        //Buscar
        FilteredList<Song> filteredData = new FilteredList<>(listaActualizable, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Song>) song-> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    if (song.getName_song().contains(newValue)) {
                        return true;
                    }
                    if (song.getGender().contains(newValue)) {
                        return true;
                    }
                    if (String.valueOf(song.getId()).contains(newValue)) {
                        return true;
                    }
                    if (song.getAlbum().getName().contains(newValue)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Song> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(mysongs.comparatorProperty());
            mysongs.setItems(sortedData);

        });

    }


    /**
     * Muestra todas las conciones en una tabla
     * edita tambien
     */
    private void allSong() throws SQLException {
        //id
        id.setCellValueFactory(song -> {
            ObservableValue<Integer> ov = new SimpleIntegerProperty().asObject();
            ((ObjectProperty<Integer>) ov).setValue(song.getValue().getId());
            return ov;
        });
        //name
        name.setCellValueFactory(song ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(song.getValue().getName_song());
            return ssp;
        });
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Song, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Song, String> t) {
                Song selected = (Song) t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setName_song(t.getNewValue());
                try {
                    sdao.save(selected);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });
        //duration
        duration.setCellValueFactory(song ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(song.getValue().getDuration());
            return ssp;
        });
        duration.setCellFactory(TextFieldTableCell.forTableColumn());
        duration.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Song, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Song, String> t) {
                Song selected = (Song) t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setDuration(t.getNewValue());
                try {
                    sdao.save(selected);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });
        //gender
        gender.setCellValueFactory(song ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(song.getValue().getGender());
            return ssp;
        });
        gender.setCellFactory(TextFieldTableCell.forTableColumn());
        gender.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Song, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Song, String> t) {
                Song selected = (Song) t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setGender(t.getNewValue());
                try {
                    sdao.save(selected);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });
        //repro
        repro.setCellValueFactory(song -> {
            ObservableValue<Integer> ov = new SimpleIntegerProperty().asObject();
            ((ObjectProperty<Integer>) ov).setValue(song.getValue().getNrepro());
            return ov;
        });
        repro.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        repro.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Song, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Song, Integer> t) {
                Song selected = t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setNrepro(t.getNewValue());
                try {
                    sdao.save(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        List <Album> songsAlbum = (List<Album>) adao.findAll();
         final ObservableList<Album> albumList = FXCollections.observableArrayList(songsAlbum);

        //album
        album.setCellValueFactory(song -> new SimpleObjectProperty<>(song.getValue().getAlbum()));
        album.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Album>() {
            @Override
            public String toString(Album album) {
                return (album != null) ? album.getName() : "";
            }

            @Override
            public Album fromString(String string) {
                // Implementa esto si deseas convertir de nuevo a un objeto Album
                return null;
            }
        }, albumList));

        album.setOnEditCommit(event -> {
            Song selected = event.getRowValue();
            // Verifica si event.getNewValue() es null antes de intentar acceder a su nombre
            selected.setAlbum((event.getNewValue() != null) ? event.getNewValue() : new Album());
            try {
                sdao.save(selected);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        mysongs.setEditable(true);
        mysongs.getSelectionModel().cellSelectionEnabledProperty().set(true);
    }



    @FXML
    private void deleteArtist(){
        if(mysongs.getSelectionModel().isEmpty()) {
            // utils.alerta("Error", "", "Debes seleccionar un cliente");
            //Validates.alertError("ERROR", "ERROR EN VIAJES", "Debes seleccionar un viaje.");
        }else {
            try {
                sdao.delete(mysongs.getSelectionModel().getSelectedItem());
            } catch (SQLException e) {

                e.printStackTrace();
            }

            listaActualizable.remove(mysongs.getSelectionModel().getSelectedItem());
        }
    }


    @FXML
    private void switchToAddSong() throws IOException {
        App.setRoot("addSong");

    }




}
