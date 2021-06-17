package ru.haazad.cloud.client.service;

import ru.haazad.cloud.Command;

public interface NetworkService {

    void sendCommand(Command command);

    String readCommandResult();

    void closeConnection();

    boolean isConnected();

}
