package ru.haazad.cloud.client.gui.service;

public interface AlertService {
    void showErrorAlert(Object cause);

    void showInfoAlert(Object cause);
}
