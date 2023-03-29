module com.example.azertgabjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;

    opens com.example.azertgabjava to javafx.fxml;
    exports com.example.azertgabjava;
    exports com.example.azertgabjava.entities;
}