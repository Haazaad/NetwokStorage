package ru.haazad.cloud.client.service;

public interface AlertService {
    void showErrorAlert(Object cause);

    void showInfoAlert(Object cause);
}
