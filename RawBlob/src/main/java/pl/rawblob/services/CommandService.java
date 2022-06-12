package pl.rawblob.services;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.annotations.Command;
import pl.rawblob.dtos.MessageDto;
import pl.rawblob.interfaces.services.ICommandService;
import pl.rawblob.interfaces.services.azure.IBlobService;
import pl.rawblob.models.Client;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.MethodsAnnotated;

@Component
public class CommandService implements ICommandService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandService.class);

    @Autowired
    private IBlobService blobService;

    @Override
    public Object Parser(String command, Client currentClient) {
        LOGGER.info("Got "+ command);
        String[] arguments = command.split(" ");

        switch(arguments[0]){
            case "List":
                return blobService.GetBlobs();
            case "Delete":
                blobService.DeleteBlob(arguments[1]);
                return new MessageDto("Deleted");
            case "Download":
                return blobService.GetBlobByName(arguments[1]);
            case "Upload":
                blobService.UploadBlob(arguments[1], arguments[2]);
                return new MessageDto("Created");
            default:
                return new MessageDto("Command not found!");
        }
    }
}
