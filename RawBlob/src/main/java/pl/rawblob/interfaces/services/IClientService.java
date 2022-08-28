package pl.rawblob.interfaces.services;

import java.net.Socket;

/**
 * ClientService interface
 */
public interface IClientService {

    /**
     * Manages new client
     * @param socket connected socket
     */
    void manage(Socket socket);
}
