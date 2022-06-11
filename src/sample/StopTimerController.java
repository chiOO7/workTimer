package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StopTimerController {
    @FXML
    public static Stage PRIMARY_STAGE;
    public Label textLabel;
    public Label stopTimerLabel;
    public Button stopTimerBtn;
    private int time;
    private Timeline stopTimeLine;
    private Timeline tickTimeLine;
    private boolean stopIsOver = false;

    public void stopTimerAction(ActionEvent actionEvent) {
        int settedTime = SettingsController.getStopTime(); //установленное время (основного) таймера перерыва
        stopTimerBtn.setDisable(true);
        textLabel.setText("Перерыв закончится через:");
        if (!stopIsOver) { //если таймер перерыва запущен следующий код не исполняется
            time = settedTime;
            stopTimerLabel.setText((time / 60) + ":" + (time - (time / 60) * 60));
            stopTimeLine = new Timeline(new KeyFrame(
                    Duration.millis(time * 1000),
                    ae -> {
                        textLabel.setText("Перерыв окончен");
                        stopTimeLine.stop();
                        tickTimeLine.stop();
                        stopTimerBtn.setDisable(false);
                        stopTimerBtn.setText("Приступить к работе!");
                        stopIsOver = true;
                    }
            ));
            tickTimeLine = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    ae -> {
                        time--;
                        stopTimerLabel.setText((time / 60) + ":" + (time - (time / 60) * 60));
                    }
            ));
            tickTimeLine.setCycleCount(time);
        }
        tickTimeLine.play();
        stopTimeLine.play();
        if (stopIsOver) {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            PRIMARY_STAGE.setIconified(false);
        }
    }
}
