package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.model.DAO.ListDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAddListonSong implements Initializable {

    @FXML
    private TextField textid;

    @FXML
    private Button addsongList;

    @FXML
    private ListView<String> listMyList;

    private int selectedListId = -1;

    private ListDAO listDAO;

    /**
     * funcion para mostrar las listas al iniciar el fxml
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializa el ListDAO
        listDAO = new ListDAO();

        try {
            // Llama a tu DAO para obtener todas las listas en la base de datos
            List<String> allLists = listDAO.findAllNameLists();

            // Limpia cualquier elemento existente antes de agregar nuevos elementos
            listMyList.getItems().clear();

            // Asigna las listas al ListView 'listMyList'
            listMyList.getItems().addAll(allLists);

            // Configura la selección de lista por defecto
            listMyList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    int listId = listDAO.findIdByName(newValue);
                    selectedListId = listId;
                    System.out.println("ID de la lista seleccionada: " + selectedListId);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle database errors
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
            // Aquí podrías mostrar un mensaje de error al usuario si es necesario
        }
    }

    /**
     * funcion para añadir una cancion a la lista selecionada
     * @param event
     */
    @FXML
    public void addSongofList(ActionEvent event) {
        // Obtén el nombre de usuario logueado desde ControllerLogin
        int id_song = Integer.parseInt(textid.getText());
        if (selectedListId > 0) {
            // Llama a la función para agregar una suscripción
            try {
                listDAO.insertSongInList(selectedListId, id_song);
                showInformation("cancion Agregada", "la cancion a sido agregada");
            } catch (SQLException e) {
                showValidationError("No se pudo agregar la cancion ");
            }
        } else {
            showValidationError("Por favor, seleccione una lista para suscribirse.");
        }
    }

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
