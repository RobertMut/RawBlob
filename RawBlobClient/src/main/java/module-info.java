module pl.rawblob.rawblobclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires spring.core;
    requires spring.context;
    requires spring.beans;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires spring.aspects;


    opens pl.rawblobclient to javafx.fxml;
    exports pl.rawblobclient;
    exports pl.rawblobclient.view;
    opens pl.rawblobclient.view to javafx.fxml;
}