package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.example.model.DAO.CommentDAO;
import org.example.model.DAO.ListDAO;
import org.example.model.dto.Comment;
import org.example.model.dto.list;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAddComment implements Initializable {

    @FXML
    private TextArea textComment;

    @FXML
    private Button addComment;

    @FXML
    private ListView<String> listMyList;

    private int selectedListId = -1;

    private ListDAO listDAO;

    /**
     * esta funcin es para poder iniciar la tabla de list en los datos
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
        }
    }

    /**
     * esta funcion agregara el comment a la lista que seleccionemos
     * @param event
     */
    @FXML
    public void addComment(ActionEvent event) {
        try {
            CommentDAO commentDAO = new CommentDAO();
            String comment = textComment.getText();


            if (selectedListId != -1) {
                String name_user = ControllerLogin.getLoggedInUserName();
                Comment newComment = new Comment(0, name_user, selectedListId, comment);
                commentDAO.save(newComment);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Comentario");
                alert.setHeaderText(null);
                alert.setContentText("Comentario agregado");
                alert.showAndWait();

                // Limpia los campos de texto después de agregar el comentario
                textComment.clear();
            } else {
                showValidationError("Seleccione una lista antes de agregar un comentario.");
            }
        } catch (SQLException e) {
            showValidationError("Error de base de datos al agregar el comentario.");
            e.printStackTrace();
        }
    }
    //mensages de error

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
