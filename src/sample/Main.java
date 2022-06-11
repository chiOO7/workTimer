package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("workTimer.fxml"));
        primaryStage.setTitle("Work Timer");
        primaryStage.setScene(new Scene(root, 340, 160));
        primaryStage.setResizable(false);
        WorkTimerController.PRIMARY_STAGE = primaryStage;
        StopTimerController.PRIMARY_STAGE = primaryStage;
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);

    }
}
