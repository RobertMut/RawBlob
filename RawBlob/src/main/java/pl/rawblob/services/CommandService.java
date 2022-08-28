package pl.rawblob.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.interfaces.services.ICommandService;
import pl.rawblob.interfaces.services.azure.IBlobService;
import pl.rawblob.model.Client;
import pl.rawblob.dtos.Message;

/**
 * CommandService interface
 */
@Component
public class CommandService implements ICommandService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandService.class);

    @Autowired
    private IBlobService blobService;

    /**
     * Parses command
     * @implSpec Separates command by space then uses first element of input to execute command.
     * @implNote It could be done using reflection with making this class more generic.
     * @param command command to be parsed
     * @param currentClient connected client
     * @return
     */
    @Override
    public Object parser(String command, Client currentClient) {
        LOGGER.info("Got "+ command);
        String[] arguments = command.split(" ");

        switch(arguments[0]){
            case "List":
                return blobService.getBlobs();
            case "Delete":
                blobService.deleteBlob(arguments[1]);
                return new Message("Deleted");
            case "Download":
                return blobService.getBlobByName(arguments[1], currentClient.getSocket());
            case "Upload":
                blobService.uploadBlob(arguments[1], currentClient.getSocket());
                return new Message("Created");
            case "Disconnect":
                currentClient.setTerminated(true);
            default:
                return new Message("Command not found!");
        }
    }
}
