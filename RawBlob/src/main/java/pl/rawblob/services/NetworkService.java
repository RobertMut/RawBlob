package pl.rawblob.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.Bootstrapper;
import pl.rawblob.interfaces.services.IClientService;
import pl.rawblob.interfaces.services.INetworkService;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

@Component
public class NetworkService implements INetworkService {

    private final static Logger LOGGER = LoggerFactory.getLogger(NetworkService.class);
    private ServerSocket socket;
    private InetSocketAddress address;
    private IClientService clientService;

    @Autowired
    public void NetworkService(String listenPort, String listenAddress, IClientService clientService){
        this.clientService = clientService;
        int port = Integer.parseInt(listenPort);
        this.address = new InetSocketAddress(listenAddress, port);

        try
        {
            LOGGER.info("Binding ServerSocket..");
            this.socket = new ServerSocket();
            this.socket.bind(this.address);
        } catch (IOException ioException){

        }
    }

    @Override
    public void listen(){
        try
        {
            while(true)
            {
                Socket acceptSocket = this.socket.accept();
                CompletableFuture.runAsync(() -> {
                    LOGGER.info("Client connected "+ acceptSocket.getRemoteSocketAddress());
                    clientService.Manage(acceptSocket);
                });
            }
        }
        catch (IOException ioException)
        {

        }
    }


}
