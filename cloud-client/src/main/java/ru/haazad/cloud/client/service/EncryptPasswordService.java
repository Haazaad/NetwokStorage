package ru.haazad.cloud.client.service;

@FunctionalInterface
public interface EncryptPasswordService {
    String encryptPassword(String password);
}

