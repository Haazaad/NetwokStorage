package ru.haazad.cloud.client.service.impl;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.client.service.AlertService;

public class AlertServiceImpl implements AlertService {
    private static final Logger logger = LogManager.getLogger(AlertServiceImpl.class);

    private static AlertServiceImpl alertService;

    private AlertServiceImpl(){}

    public static AlertServiceImpl getAlertService() {
        alertService = new AlertServiceImpl();
        return alertService;
    }

    @Override
    public void showErrorAlert(Object cause) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        showAlert(alert, cause);
    }

    @Override
    public void showInfoAlert(Object cause) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        showAlert(alert, cause);
    }

    private void showAlert(Alert alert, Object cause) {
        alert.setTitle("Cloud Client");
        alert.setContentText((String) cause);
        alert.setHeaderText(null);
        alert.showAndWait();
        logger.error(cause);
    }
}
