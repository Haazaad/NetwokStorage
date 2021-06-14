package ru.haazad.cloud.client.service;

public interface NetworkService {

    void sendCommand(String command);

    String readCommandResult();

    void closeConnection();

    boolean isConnected();

}
