package pl.rawblob.interfaces.services;

import pl.rawblob.model.Client;

/**
 * CommandService interface
 */
public interface ICommandService {

    /**
     * Parses command
     * @param command command to be parsed
     * @param currentClient connected client
     * @return object to be deserialized
     */
    Object parser(String command, Client currentClient);
}
