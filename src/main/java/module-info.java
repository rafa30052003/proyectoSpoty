module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires jbcrypt;
    requires java.xml;
    requires jlayer;

    opens org.example to javafx.fxml;
    opens org.example.conexion to java.xml.bind; // Corregido el nombre de la carpeta

    exports org.example;
    exports org.example.model.dto;
    exports org.example.controller;

    opens org.example.controller to javafx.fxml;
}
