module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires jbcrypt;


  opens org.example to javafx.fxml;
    opens org.example.Conections to java.xml.bind; // Abre el paquete org.example.Conections
    exports org.example;
    exports org.example.model.dto;
    opens org.example.domain to javafx.fxml;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml;


}