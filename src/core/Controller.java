package core;

import io.IO;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static ArrayList<Node> originalWindow = new ArrayList<>();
    static int windowWidth = 700, windowHeight = 500;
    private static double numberOfNodes = 1, nodeRadius = 100;
    @FXML
    public Pane windowPane;
    @FXML
    private Button quitButton;
    @FXML
    private TextField nodeField;
    @FXML
    private Line xAxis;
    @FXML
    private TextField radiusField;

    public void initialize(URL location, ResourceBundle resources) {

        /*
         * Causes the application to quit from the main window when the Quit Application Button is pressed.
         */
        this.quitButton.setOnMouseClicked(event -> {
            System.out.println("Application is about to quit.");
            System.exit(0);
        });

        this.nodeField.setOnKeyReleased(event -> {
            System.out.println("Number of nodes has changed.");
            handleNumberOfNodesChanged();
        });

        this.radiusField.setOnKeyReleased(event -> {
            System.out.println("Radius has changed.");
            handleRadiusChange();
        });


        for (Node e : windowPane.getChildren()) {
            originalWindow.add(e);
        }

    }

    private void handleNumberOfNodesChanged() {
        String text = this.nodeField.getText();
        if (IO.isInteger(text) && Integer.valueOf(text) > 0) {
            System.out.println("Valid integer. Changing number of nodes.");
            numberOfNodes = Integer.valueOf(text);
            System.out.println("Number of nodes is now: " + numberOfNodes);
            System.out.println("Redrawing nodes...");
            redrawNodes();
        } else {
            System.err.println("Invalid input. Waiting for a positive integer value.");
        }
    }

    private void handleRadiusChange() {
        String text = this.radiusField.getText();
        if (IO.isInteger(text) && Integer.valueOf(text) > 0) {
            System.out.println("Valid integer. Changing node radius.");
            nodeRadius = Integer.valueOf(text);
            System.out.println("Node radius is now: " + nodeRadius);
            System.out.println("Redrawing nodes...");
            redrawRadius();
        } else {
            System.err.println("Invalid input. Waiting for a positive integer value.");
        }
    }


    private void redrawNodes() {
        nodeRadius = windowWidth / numberOfNodes;

        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(originalWindow);

        TranslateTransition transition;
        double x;
        double y;

        for (double i = 0; i < numberOfNodes * nodeRadius; i += nodeRadius) {
            x = new Random().nextInt(windowWidth - (int) nodeRadius);
            y = new Random().nextInt(windowHeight - (int) nodeRadius);
            Circle e = new Circle(x, y, nodeRadius / 2);
            transition = new TranslateTransition();
            x = i + (nodeRadius / 2) - x;
            y = 225 - y;
            transition.setToX(x);
            transition.setToY(y);

            transition.setDuration(Duration.seconds(5));
            transition.setNode(e);
            transition.play();
            windowPane.getChildren().add(e);
        }

        radiusField.setText("" + nodeRadius);

    }

    private void redrawRadius() {
        numberOfNodes = (int) (windowWidth / nodeRadius) + 1;

        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(originalWindow);

        TranslateTransition transition;
        double x;
        double y;

        for (double i = 0; i < numberOfNodes * nodeRadius; i += nodeRadius) {
            x = new Random().nextInt(windowWidth - (int) nodeRadius);
            y = new Random().nextInt(windowHeight - (int) nodeRadius);
            Circle e = new Circle(x, y, nodeRadius / 2);
            transition = new TranslateTransition();
            x = i + (nodeRadius / 2) - x;
            y = 225 - y;
            transition.setToX(x);
            transition.setToY(y);

            transition.setDuration(Duration.seconds(5));
            transition.setNode(e);
            transition.play();
            windowPane.getChildren().add(e);
        }

        nodeField.setText("" + numberOfNodes);

    }


}