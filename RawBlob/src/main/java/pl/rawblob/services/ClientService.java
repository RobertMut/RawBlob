package pl.rawblob.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.helpers.JsonHelper;
import pl.rawblob.helpers.NetworkHelper;
import pl.rawblob.interfaces.services.IClientService;
import pl.rawblob.interfaces.services.ICommandService;
import pl.rawblob.model.Client;

import java.io.IOException;
import java.net.Socket;

/**
 * ClientService class
 */
@Component
public class ClientService implements IClientService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private ICommandService commandService;

    /**
     * Manages client
     * @implSpec Sets Client then waits until input. If client terminated their connection shuts down socket.
     * @param socket connected socket
     */
    @Override
    public void manage(Socket socket) {
        LOGGER.info("Assigning client..");
        Client currentClient = new Client();

        currentClient.setTerminated(false);
        currentClient.setSocket(socket);

        while(!currentClient.isTerminated()){

            String input = NetworkHelper.read(socket);

            if(!input.isEmpty()) {
                LOGGER.info(socket.getRemoteSocketAddress()+": "+input);

                var response = commandService.parser(input, currentClient);
                String serializedResponse = JsonHelper.Serialize(response);
                NetworkHelper.write(socket, serializedResponse);
            }
        }

        try {
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
            LOGGER.info("Disconnected");
        } catch (IOException e){
            LOGGER.error(e.getMessage());
        }

    }
}
