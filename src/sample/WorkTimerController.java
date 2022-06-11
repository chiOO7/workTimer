package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class WorkTimerController {

    @FXML
    public static Stage PRIMARY_STAGE;
    @FXML
    public Button startBtn;
    @FXML
    public Label textLabel;
    @FXML
    public Label workTimerLabel;
    @FXML
    public Button stopBtn;

    private boolean workTimerIsStarted;  //таймер запущен или нет
    private int settedTime;  //установленное время работы таймера
    private int time; //переменная хранит время работы таймера для отображения в ходе его работы
    private Timeline workTimeLine; //таймер появления окна (основной)
    private Timeline tickTimeLine; //таймер для обновления времени таймера, показываемого в ходе работы основного

    private void clearTimer(){   //метод сброса таймера
        textLabel.setText("Таймер остановлен");
        workTimeLine.stop();
        tickTimeLine.stop();
        startBtn.setDisable(false);
        workTimerIsStarted = false;
        workTimerLabel.setText((settedTime / 60) + ":" + (settedTime - (settedTime / 60) * 60)); //создание строки с оставшимся временем работы основного таймера
    }

    public void startBtnAction (ActionEvent actionEvent){
        settedTime = SettingsController.getWorkTime();      //установленное время рабочего (основного) таймера
        int settedStopTime = SettingsController.getStopTime();  //установленное время таймера перерыва
        if (settedTime > 0 && settedStopTime > 0) {  //если время любого таймера 0, то основной и вспомогательный таймеры не запустятся
            startBtn.setDisable(true);
            stopBtn.setDisable(false);
            textLabel.setText("Перерыв начнётся через:");
            if (!workTimerIsStarted) { //если основной таймер запущен следующий код не выполняется
                PRIMARY_STAGE.setIconified(true);
                time = settedTime;
                workTimerLabel.setText((time / 60) + ":" + (time - (time / 60) * 60));
                workTimeLine = new Timeline(new KeyFrame(  //создание экземпляра основного таймера
                        Duration.millis(time * 1000),  //выдержка времени основного таймера
                        ae -> {
                            try {  //вывод окна окончания рабочего периода
                                Stage stage = new Stage();
                                Parent root = FXMLLoader.load(getClass().getResource("stopTimer.fxml"));
                                stage.setTitle("Перерыв!!!");
                                stage.setScene(new Scene(root, 200, 200));
                                stage.setResizable(false);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                                stage.setAlwaysOnTop(true);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            clearTimer(); //сброс основного и вспомогательного таймеров
                        }
                ));
                tickTimeLine = new Timeline(new KeyFrame( //создание экземпляра вспомогательного таймера
                        Duration.millis(1000),
                        ae -> {
                            time--;
                            workTimerLabel.setText((time / 60) + ":" + (time - (time / 60) * 60));
                        }
                ));
                tickTimeLine.setCycleCount(time); //количество циклов работы вспомогательного таймера
            }
            tickTimeLine.play(); //запуск вспомогательного таймера
            workTimeLine.play(); //запуск основного таймера
            workTimerIsStarted = true;
        }
        else textLabel.setText("Установите время таймеров больше ноля");
    }

    public void stopBtnAction(){
       if (workTimerIsStarted) {
        textLabel.setText("Таймер остановлен");
        stopBtn.setDisable(true);
        startBtn.setDisable(false);
        workTimeLine.pause();
        tickTimeLine.pause();
       }
    }

    public void clrBtnAction() {
        if (workTimerIsStarted) clearTimer();
    }

    public void setsBtnAction(ActionEvent actionEvent) {
        try {  //вывод окна настроек
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
            stage.setTitle("Настройки таймеров");
            stage.setScene(new Scene(root, 270, 200));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
