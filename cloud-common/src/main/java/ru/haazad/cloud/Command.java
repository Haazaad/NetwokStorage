package ru.haazad.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Command implements Serializable {
    private String commandName;
    private Object[] args;
}
