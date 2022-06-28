package pl.rawblobclient.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblobclient.interfaces.services.ICommandService;
import pl.rawblobclient.model.BlobListItem;
import pl.rawblobclient.viewmodel.MainViewModel;

public class MainView {

    private final MainViewModel viewModel;
    @FXML
    private ObservableList<BlobListItem> items;

    @FXML
    private ListView<BlobListItem> listView;
    
    public MainView(ICommandService commandService) {
        this.viewModel = new MainViewModel(commandService);
    }

    public void initialize(){
        items = viewModel.getBlobList();
    }
}