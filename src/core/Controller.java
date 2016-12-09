package core;

import io.IO;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
    /**
     * Default height and width of the UI Window.
     */
    static int windowWidth = 700, windowHeight = 500;
    /**
     * Mirrors the elements contained by the original window.
     */
    private static ArrayList<Node> originalWindow = new ArrayList<>();
    /**
     * Default number of nodes and respective radius.
     * These are changed almost instantly at runtime.
     */
    private static double numberOfNodes = 1, nodeRadius = 100;
    /**
     * The actual window that you see.
     */
    @FXML
    public Pane windowPane;
    /**
     * The button used to quit the application.
     */
    @FXML
    private Button quitButton;
    /**
     * The textfield that allows you specify how many nodes are to be drawn.
     */
    @FXML
    private TextField nodeField;
    /**
     * The X axis drawn across the center of the screen.
     */
    @FXML
    private Line xAxis;
    /**
     * The radius of the nodes.
     */
    @FXML
    private TextField radiusField;

    /**
     * Called after the Class constructor by JavaFX to inject code.
     * This is used to set the actions of the various JavaFX UI elements.
     *
     * @param location  the location.
     * @param resources the resources to use.
     */
    public void initialize(URL location, ResourceBundle resources) {

        /*
         * Causes the application to quit from the main window when the Quit Application Button is pressed.
         */
        this.quitButton.setOnMouseClicked(event -> {
            System.out.println("Application is about to quit.");
            System.exit(0);
        });

        /*
         * Causes the application to handle the number of nodes to be presented.
         */
        this.nodeField.setOnKeyReleased(event -> {
            System.out.println("Number of nodes has changed.");
            handleNumberOfNodesChanged();
        });

        /*
         * Causes the application to handle the number of nodes/radius on a radius change.
         */
        this.radiusField.setOnKeyReleased(event -> {
            System.out.println("Radius has changed.");
            handleRadiusChange();
        });

        /*
         * Adds the elements contained by the original program to the mirror.
         */
        for (Node e : windowPane.getChildren()) {
            originalWindow.add(e);
        }

    }

    private void handleNumberOfNodesChanged() {
        String text = this.nodeField.getText();
        if (IO.isInteger(text) && Integer.valueOf(text) > 0) {
            handleValidNodeChange(text);
        } else if (IO.isInteger(text) && Integer.valueOf(text) == 0) {
            handleZeroNodes();
        } else if (text.length() == 0) {
            // The user is probably entering a new number.
            System.err.println("Invalid input. Waiting for a positive integer value.");
        } else {
            // The user is probably inputting characters or negative values.
            handleInvalidNumberOfSesnors();
        }
    }

    /**
     * Handles the event when a valid number is entered for the number of nodes.
     *
     * @param text The text that was entered into the number of nodes.
     */
    private void handleValidNodeChange(String text) {
        System.out.println("Valid integer. Changing number of nodes.");
        numberOfNodes = Integer.valueOf(text);
        System.out.println("Number of nodes is now: " + numberOfNodes);
        System.out.println("Redrawing nodes...");
        redrawNodes();
    }

    /**
     * Handles the event when 0 is entered.
     */
    private void handleZeroNodes() {
        System.out.println("Value is 0. Removing nodes...");
        numberOfNodes = 0;
        redrawNodes();
        radiusField.setText("No Sensors.");
    }


    /**
     * Displays an alert message, sets the number of nodes to 0 and sets the radius input field
     * to an error message.
     */
    private void handleInvalidNumberOfSesnors() {
        // Remove the nodes due to invalid input.
        numberOfNodes = 0;
        this.nodeField.setText("0");
        redrawNodes();
        radiusField.setText("Invalid Input.");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Number of Sensors!");
        alert.setContentText("Please ensure that you have entered a valid number of sensors.");
        alert.showAndWait();
    }

    /**
     * Called to change the value of the sensor radix.
     */
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


    /**
     * Redraws all of the nodes on the foreground.
     */
    private void redrawNodes() {
        nodeRadius = windowWidth / numberOfNodes;
        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(originalWindow);
        redrawSpriteLoop();
        radiusField.setText("" + nodeRadius);

    }

    /**
     * Redraws the radius of the nodes.
     * Calls the sprite update method.
     */
    private void redrawRadius() {
        numberOfNodes = (int) (windowWidth / nodeRadius) + 1;
        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(originalWindow);
        redrawSpriteLoop();
        nodeField.setText("" + numberOfNodes);

    }

    /**
     * Causes the sprites to be called and move around "performing the algorithm".
     */
    private void redrawSpriteLoop() {
        TranslateTransition transition;
        double x;
        double y;

        for (double i = 0; i < numberOfNodes * nodeRadius; i += nodeRadius) {
            int ww = windowWidth - (int) nodeRadius, wh = windowHeight - (int) nodeRadius;
            // This is a hacky fix for the 1 node runtime error.
            if (ww < 2) ww = 1;
            if (wh < 2) wh = 1;
            x = new Random().nextInt(ww);
            y = new Random().nextInt(wh);
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
    }


}