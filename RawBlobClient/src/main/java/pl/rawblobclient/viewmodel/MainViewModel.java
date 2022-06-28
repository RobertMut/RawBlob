package pl.rawblobclient.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblobclient.interfaces.services.ICommandService;
import pl.rawblobclient.model.BlobListItem;

@Component
public class MainViewModel {

    @Autowired
    private ICommandService commandService;

    @Autowired
    public MainViewModel(ICommandService commandService){
        this.commandService = commandService;
    }
    
    public ObservableList<BlobListItem> getBlobList(){
        var blobs = commandService.ListBlobs();

        return FXCollections.observableList(blobs.Blobs);
    }
}
