package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;

        try {
            System.out.println("Loading main interface...");
            root = FXMLLoader.load(getClass().getClassLoader().getResource("core/MainApplication.fxml"));
        } catch (IOException e) {
            System.err.println("(FATAL) Missing file: MainApplication.fxml");
            System.exit(-1);
        }
        System.out.println("Main interface loaded.");
        primaryStage.setTitle("COMP 3203 Project");
        primaryStage.setScene(new Scene(root, 700, 500));


        primaryStage.show();
    }
}
