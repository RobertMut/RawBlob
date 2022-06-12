package pl.rawblob.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.dtos.MessageDto;
import pl.rawblob.helpers.JsonHelper;
import pl.rawblob.helpers.NetworkHelper;
import pl.rawblob.interfaces.services.IClientService;
import pl.rawblob.interfaces.services.ICommandService;
import pl.rawblob.models.Client;
import pl.rawblob.models.Message;

import java.net.Socket;

@Component
public class ClientService implements IClientService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private ICommandService commandService;

    @Override
    public void Manage(Socket socket){
        LOGGER.info("Assigning client..");
        Client currentClient = new Client();
        currentClient.setTerminated(false);
        String welcomeMessage = JsonHelper.Serialize(new MessageDto("Write command!"));
        NetworkHelper.write(socket, welcomeMessage);
        while(!currentClient.isTerminated()){
            String input = NetworkHelper.read(socket);
            if(!input.isEmpty()) {
                LOGGER.info(socket.getRemoteSocketAddress()+": "+input);
                //var obj = JsonHelper.Deserialize(input, Message.class);
                var response = commandService.Parser(input, currentClient);
                String serializedResponse = JsonHelper.Serialize(response);
                NetworkHelper.write(socket, serializedResponse);
            }

        }
    }
}
