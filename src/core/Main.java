package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage mainStage;
    private static Canvas canvas;
    private static Scene scene;
    private static Parent root;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void redraw(int numberOfNodes, int nodeRadius) {
        Circle circle = new Circle(200, 200, 30);
        circle.setFill(Color.BLACK);
        canvas.getGraphicsContext2D().lineTo(100, 400);
    }

    @Override
    public void start(Stage primaryStage) {
        root = null;
        mainStage = primaryStage;
        canvas = new Canvas(Controller.windowWidth, Controller.windowHeight);
        try {
            System.out.println("Loading main interface...");
            root = FXMLLoader.load(getClass().getClassLoader().getResource("core/MainApplication.fxml"));
        } catch (IOException e) {
            System.err.println("(FATAL) Missing file: MainApplication.fxml");
            System.exit(-1);
        }
        System.out.println("Main interface loaded.");
        primaryStage.setTitle("COMP 3203 Project");
        scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


