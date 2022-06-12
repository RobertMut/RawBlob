package pl.rawblob.interfaces.services;

import pl.rawblob.models.Client;

public interface ICommandService {
    Object Parser(String command, Client currentClient);
}
