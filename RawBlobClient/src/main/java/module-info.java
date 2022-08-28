module pl.rawblob.rawblobclient {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;

    opens pl.rawblobclient to com.fasterxml.jackson.databind, com.fasterxml.jackson.core;
    opens pl.rawblobclient.dtos to com.fasterxml.jackson.databind, com.fasterxml.jackson.core;
    opens pl.rawblobclient.controllers;

    exports pl.rawblobclient;
    exports pl.rawblobclient.dtos to com.fasterxml.jackson.databind, com.fasterxml.jackson.core;
    exports pl.rawblobclient.controllers;
}