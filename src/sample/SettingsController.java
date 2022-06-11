package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class SettingsController {
    public TextField workMinField;
    public TextField workSecField;
    public TextField stopMinField;
    public TextField stopSecField;

    static int getWorkTime() {
        return workTime;
    }

    static int getStopTime() {
        return stopTime;
    }

    private static int workTime; //установленное время рабочего таймера
    private static int stopTime; //установленное время таймера перерыва

    public void confirmBtnAction(ActionEvent actionEvent) {
        int workTimeMinutes;
        try {
            workTimeMinutes = Integer.parseInt(workMinField.getText());
        } catch (NumberFormatException e) {
            workTimeMinutes = 0;
        }
        int workTimeSeconds;
        try {
            workTimeSeconds = Integer.parseInt(workSecField.getText());
        } catch (NumberFormatException e) {
            workTimeSeconds = 0;
        }

        int stopTimeMinutes;
        try {
            stopTimeMinutes = Integer.parseInt(stopMinField.getText());
        } catch (NumberFormatException e) {
            stopTimeMinutes = 0;
        }

        int stopTimeSeconds;
        try {
            stopTimeSeconds = Integer.parseInt(stopSecField.getText());
        } catch (NumberFormatException e) {
            stopTimeSeconds = 0;
        }

        workTime = workTimeMinutes * 60 + workTimeSeconds;
        stopTime = stopTimeMinutes * 60 + stopTimeSeconds;
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void cancelBtnAction(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

}
