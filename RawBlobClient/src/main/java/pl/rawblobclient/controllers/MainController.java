package pl.rawblobclient.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import pl.rawblobclient.interfaces.services.ICommandService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

/**
 * MainController class
 */
public class MainController implements Initializable {

    @FXML
    TableView tableView;
    @FXML
    TableColumn<LinkedHashMap, String> blobName;
    @FXML
    TableColumn<LinkedHashMap, String> uploadDate;
    @FXML
    TableColumn<LinkedHashMap, String> size;

    private ICommandService commandService;

    /**
     * Constructs MainController
     * @param commandService commandService
     * @see pl.rawblobclient.services.CommandService
     */
    public MainController(ICommandService commandService){
        this.commandService = commandService;

    }

    /**
     * Refreshes table
     * @param event ActionEvent
     */
    public void onRefresh(ActionEvent event){
        tableView.getItems().clear();
        tableView.getItems().addAll(commandService.listBlobs());
    }

    /**
     * Downloads item selected on table
     * @param event ActionEvent
     */
    public void onDownload(ActionEvent event){
        LinkedHashMap<String, String> item = (LinkedHashMap<String, String>)tableView.getSelectionModel().getSelectedItem();
        String itemName = item.get("blobName");

        commandService.downloadBlob(itemName);
    }

    /**
     * Uploads file from dialog
     * @param event ActionEvent
     * @throws FileNotFoundException When file not found
     */
    public void onUpload(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file to upload");

        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            commandService.upload(file.getName(), new FileInputStream(file));
        }
    }

    /**
     * Deletes selected item from storage
     * @param event ActionEvent
     * @throws IOException When file wasn't deleted
     */
    public void onDelete(ActionEvent event) throws IOException {
        LinkedHashMap<String, String> item = (LinkedHashMap<String, String>)tableView.getSelectionModel().getSelectedItem();
        String itemName = item.get("blobName");

        commandService.delete(itemName);
    }

    /**
     * Initializes elements
     * @param location URL
     * @param resources ResourceBundle
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blobName.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().get("blobName").toString()));
        uploadDate.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().get("uploadDate").toString()));
        size.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().get("size").toString()));

        tableView.getItems().addAll(commandService.listBlobs());
    }
}
