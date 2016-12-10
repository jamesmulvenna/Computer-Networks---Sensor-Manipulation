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
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
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
    static final int WINDOW_WIDTH = 700, WINDOW_HEIGHT = 500;
    /**
     * The duration in seconds for a transition to last.
     */
    private static final int TRANSITION_DURATION = 5;
    /**
     * Mirrors the elements contained by the original window.
     */
    private static ArrayList<Node> ORIGINAL_WINDOW = new ArrayList<>();
    /**
     * Default number of nodes and respective radius.
     * These are changed almost instantly at runtime.
     */
    private static double NUMBER_OF_NODES = 1, NODE_RADIUS = 100;
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
            ORIGINAL_WINDOW.add(e);
        }

    }

    private void handleNumberOfNodesChanged() {
        String text = this.nodeField.getText();
        if (IO.isInteger(text) && Integer.valueOf(text) > 0 && Integer.valueOf(text) < 2 * WINDOW_WIDTH) {
            handleValidNodeChange(text);
        } else if (IO.isInteger(text) && Integer.valueOf(text) == 0) {
            handleZeroNodes();
        } else if (text.length() == 0) {
            // The user is probably entering a new number.
            System.err.println("Invalid input. Waiting for a positive integer value.");
        } else {
            // The user is probably inputting characters or negative values.
            handleInvalidNumberOfSensors();
        }
    }

    /**
     * Handles the event when a valid number is entered for the number of nodes.
     *
     * @param text The text that was entered into the number of nodes.
     */
    private void handleValidNodeChange(String text) {
        System.out.println("Valid integer. Changing number of nodes.");
        NUMBER_OF_NODES = Integer.valueOf(text);
        System.out.println("Number of nodes is now: " + NUMBER_OF_NODES);
        System.out.println("Redrawing nodes...");
        redrawNodes();
    }

    /**
     * Handles the event when 0 is entered.
     */
    private void handleZeroNodes() {
        System.out.println("Value is 0. Removing nodes...");
        NUMBER_OF_NODES = 0;
        NODE_RADIUS = 0;
        redrawNodes();
        radiusField.setText("0");
    }


    /**
     * Displays an alert message, sets the number of nodes to 0 and sets the radius input field
     * to an error message.
     */
    private void handleInvalidNumberOfSensors() {
        // Remove the nodes due to invalid input.
        NUMBER_OF_NODES = 0;
        this.nodeField.setText("0");
        redrawNodes();
        radiusField.setText("0");
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
            if (Integer.valueOf(text) > WINDOW_WIDTH) {
                handleInvalidRadius();
                return;
            }
            System.out.println("Valid integer. Changing node radius.");
            NODE_RADIUS = Integer.valueOf(text);
            NUMBER_OF_NODES = WINDOW_WIDTH / NODE_RADIUS;
            System.out.println("Node radius is now: " + NODE_RADIUS);
            System.out.println("Adjusted required number of nodes.");
            System.out.println("Number of Nodes: " + NUMBER_OF_NODES);
            System.out.println("Redrawing nodes...");
            redrawRadius();
        } else if (IO.isInteger(text) && Integer.valueOf(text) == 0) {
            handleZeroNodes();
        } else if (text.length() == 0) {
            // The user is probably entering a new number.
            System.err.println("Invalid input. Waiting for a positive integer value.");
        } else {
            // The user is probably inputting characters or negative values.
            handleInvalidRadius();
        }
    }

    private void handleInvalidRadius() {
        // Remove the nodes due to invalid input.
        NUMBER_OF_NODES = 0;
        NODE_RADIUS = 0;
        this.nodeField.setText("0");
        redrawNodes();
        radiusField.setText("0");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Radius Entered!");
        alert.setContentText("Please ensure that you have entered a valid sensor radius!.\nPlease ensure that the specified radius is smaller than the available width of the window (" + WINDOW_WIDTH + ") units.");
        alert.showAndWait();
    }


    /**
     * Redraws all of the nodes on the foreground.
     */
    private void redrawNodes() {
        NODE_RADIUS = WINDOW_WIDTH / NUMBER_OF_NODES;
        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(ORIGINAL_WINDOW);
        redrawSpriteLoop();
        radiusField.setText("" + NODE_RADIUS);

    }

    /**
     * Redraws the radius of the nodes.
     * Calls the sprite update method.
     */
    private void redrawRadius() {
        NUMBER_OF_NODES = (int) (WINDOW_WIDTH / NODE_RADIUS);
        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(ORIGINAL_WINDOW);
        redrawSpriteLoop();
        nodeField.setText("" + NUMBER_OF_NODES);

    }

    /**
     * Causes the sprites to be called and move around "performing the algorithm".
     */
    private void redrawSpriteLoop() {
        TranslateTransition transition;
        double x, y;

        for (double i = 0; i < NUMBER_OF_NODES * NODE_RADIUS; i += NODE_RADIUS) {
            // Generate random coordinates.
            int randomXPosition = WINDOW_WIDTH - (int) NODE_RADIUS, randomYPosition = WINDOW_HEIGHT - (int) NODE_RADIUS;
            // This is a hacky fix for the 1 node runtime error.
            if (randomXPosition < 2) randomXPosition = 1;
            if (randomYPosition < 2) randomYPosition = 1;

            // Assign random coordinates.
            x = new Random().nextInt(randomXPosition);
            y = new Random().nextInt(randomYPosition);

            // Create a Circle object to represent a sensor radius to be drawn.
            Circle sensorToDraw = new Circle(x, y, NODE_RADIUS / 2);
            // Make the nodes look cute.
            Stop[] gradientStops = new Stop[]{new Stop(0, Color.BLACK), new Stop(1, Color.POWDERBLUE)};
            RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.2, true, CycleMethod.NO_CYCLE, gradientStops);
            sensorToDraw.setFill(gradient);

            transition = new TranslateTransition();

            // Assign re-calculated coordinates to node i.
            x = i + (NODE_RADIUS / 2) - x;
            y = 225 - y;
            transition.setToX(x);
            transition.setToY(y);

            // transition node i in a direction for the duration of a transition.
            transition.setDuration(Duration.seconds(TRANSITION_DURATION));
            transition.setNode(sensorToDraw);
            transition.play();
            windowPane.getChildren().add(sensorToDraw);
        }
    }


}