package ru.haazad.cloud.client.service;

import ru.haazad.cloud.command.Command;

public interface NetworkService {

    void sendCommand(Command command);

    void closeConnection();

    boolean isConnected();

}
