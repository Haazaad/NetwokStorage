package ru.haazad.cloud.client.service;

@FunctionalInterface
public interface Callback {
    void callback(Object... args);
}
