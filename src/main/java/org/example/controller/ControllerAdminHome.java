package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import org.example.App;
import org.example.model.DAO.ArtistDAO;
import org.example.model.dto.Artist;
import org.example.model.dto.Nationality;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAdminHome implements Initializable {

    /**
     * SIDEBAR
     */

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    public ControllerAdminHome() throws SQLException {
    }

    @FXML
    private void artist (MouseEvent event) {
        bp.setCenter(ap);
    }
    @FXML
    private void album (MouseEvent event) {
        loadPage("album");
    }


    @FXML
    private void song(MouseEvent event) {
        loadPage("song");
    }

    @FXML
    private void switchLogin() throws IOException {
        App.setRoot("login");

    }

    private void loadPage (String page) {


        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/controller/" + page +".fxml"));
            bp.setCenter(root);
        }catch (IOException ex){
            Logger.getLogger(ControllerAdminHome.class.getName()).log(Level.SEVERE,null, ex);
        }
    }





    /**
     * ARTIST CONTROLLER
     */

    ArtistDAO adao = new ArtistDAO();
    private List<Artist> artistsList =(List<Artist>) adao.findAll();
    private final ObservableList<Artist> listaActualizable = FXCollections.observableArrayList(artistsList);

    @FXML private TableView<Artist> myartists;
    @FXML private TableColumn<Artist, String> photo;
    @FXML private TableColumn<Artist, String> name;
    @FXML private TableColumn<Artist, Nationality> nationality;
    @FXML private TextField search;
    //@FXML private Button btnInsert;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
    this.allArtist();
    myartists.setItems(listaActualizable);

    //Buscar
        FilteredList<Artist> filteredData = new FilteredList<>(listaActualizable, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Artist>) artist-> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    if (artist.getName().contains(newValue)) {
                        return true;
                    }
                    if (artist.getNationality().equals(Nationality.valueOf(newValue))) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Artist> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(myartists.comparatorProperty());
            myartists.setItems(sortedData);

        });
    }

    /**
     * Muestra todos los artistas en una tabla
     */
    private void allArtist(){
        //photo
        photo.setCellValueFactory(artist ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(artist.getValue().getPhoto());
            return ssp;
        });
        photo.setCellFactory(TextFieldTableCell.forTableColumn());
        photo.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Artist, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Artist, String> t) {
                Artist selected = (Artist) t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setPhoto(t.getNewValue());
                try {
                    adao.save(selected);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });
        //name

        name.setCellValueFactory(artist ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(artist.getValue().getName());
            return ssp;
        });
       
        //nationality
        nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        nationality.setCellFactory(new Callback<TableColumn<Artist, Nationality>, TableCell<Artist, Nationality>>() {
            @Override
            public TableCell<Artist, Nationality> call(TableColumn<Artist, Nationality> param) {
                return new ComboBoxTableCell<>(Nationality.values());
            }
        });

        nationality.setOnEditCommit(event -> {
            Artist selected = event.getRowValue();
            selected.setNationality(event.getNewValue());
            try {
                adao.save(selected);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        myartists.setEditable(true);
        myartists.getSelectionModel().cellSelectionEnabledProperty().set(true);
    }


    @FXML
    private void deleteArtist(){
        if(myartists.getSelectionModel().isEmpty()) {
            // utils.alerta("Error", "", "Debes seleccionar un cliente");
            //Validates.alertError("ERROR", "ERROR EN VIAJES", "Debes seleccionar un viaje.");
        }else {
            try {
                adao.delete(myartists.getSelectionModel().getSelectedItem());
            } catch (SQLException e) {

                e.printStackTrace();
            }

            listaActualizable.remove(myartists.getSelectionModel().getSelectedItem());
        }
    }
    @FXML
    private void switchToAddArtist() throws IOException {
        App.setRoot("addArtist");

    }

}
