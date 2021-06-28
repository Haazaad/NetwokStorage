package ru.haazad.cloud.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.haazad.cloud.client.controller.LoginWindowController;

public class ClientApp extends Application {
    private static String username;

    private static Initializable activeController;
    private static Initializable secondaryController;

    public static Initializable getActiveController() {
        return activeController;
    }

    public static void setActiveController(Initializable controller) {
       activeController = controller;
    }

    public static Initializable getSecondaryController() {
        return secondaryController;
    }

    public static void setSecondaryController(Initializable secondaryController) {
        ClientApp.secondaryController = secondaryController;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String name) {
        username = name;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/loginWindow.fxml"));
        Parent parent = loader.load();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Cloud Client");
        primaryStage.setResizable(true);

        LoginWindowController controller = loader.getController();
        controller.setStage(primaryStage);
        setActiveController(controller);
        primaryStage.setOnCloseRequest((event) -> controller.disconnect());
        primaryStage.show();
    }
}
