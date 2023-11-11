package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.DAO.ArtistDAO;
import org.example.model.dto.Artist;
import org.example.model.dto.Nationality;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControllerAddArtist implements Initializable {

    @FXML
    private ImageView imageView;

    private File selectedImageFile;

    @FXML
    private TextField txtName;


    @FXML
    private ComboBox<Nationality> txtNationality;

    @FXML
    private Button foto;

     ArtistDAO artistDAO = new ArtistDAO();
/*
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArtistDAO artistDAO = new ArtistDAO();
            List<String> artistNames = artistDAO.findNames();
            ObservableList<String> namesObservableList = FXCollections.observableArrayList(artistNames);
            txtArtista.setItems(namesObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 */
    @FXML
    private void addArtist() {
        String name = txtName.getText();

        Nationality nationality = txtNationality.getValue();

        if (name.isEmpty() ||    nationality == Nationality.UNKNOWN || imageView==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Incompletos");
            alert.setHeaderText("Por favor complete todos los campos.");
            alert.showAndWait();
            return;
        }
        try {
            File selectedFile = elegirFoto();
            FileInputStream inputStream = new FileInputStream(selectedFile);
            byte[] photo = new byte[inputStream.available()];
            inputStream.read(photo);
            Artist a = new Artist(name, nationality,Arrays.toString(photo) );
            artistDAO.save(a);
            App.setRoot("HomeAdmin");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private File elegirFoto() {
        if (selectedImageFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar Foto");
            selectedImageFile = fileChooser.showOpenDialog(new Stage());

            if (selectedImageFile != null) {
                try {
                    FileInputStream inputStream = new FileInputStream(selectedImageFile);
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Por favor seleccione una imagen.");
                }
            }
        }
        return selectedImageFile;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtNationality.getItems().setAll(Nationality.values());
    }
}
