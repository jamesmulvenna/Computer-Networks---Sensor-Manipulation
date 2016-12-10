package core;

import io.IO;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

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
     * Used to keep track of the last string of text entered into the
     * TextField for the number of nodes.
     */
    private static String LAST_ENTERED_NUMBER_OF_NODES = "";

    /**
     * Used to keep track of the last string of text entered into the
     * TextField for the radius of each node.
     */
    private static String LAST_ENTERED_RADIUS = "";
    /**
     * The y position that sensors should be drawn at.
     */
    private static double y = (WINDOW_HEIGHT / 2) - 25;
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
     * Specifies the algorithm to use for drawing the nodes.
     */
    @FXML
    private ChoiceBox<String> algorithmSelector;
    private ObservableList<String> algorithmList;

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
            if (!this.nodeField.getText().trim().equals(LAST_ENTERED_NUMBER_OF_NODES)) {
                LAST_ENTERED_NUMBER_OF_NODES = this.nodeField.getText().trim();
                System.out.println("Number of nodes has changed.");
                handleNumberOfNodesChanged();
            }
        });

        /*
         * Causes the application to handle the number of nodes/radius on a radius change.
         */
        this.radiusField.setOnKeyReleased(event -> {
            if (!this.radiusField.getText().trim().equals(LAST_ENTERED_RADIUS)) {
                System.out.println("Radius has changed.");
                LAST_ENTERED_RADIUS = this.radiusField.getText().trim();
                handleRadiusChange();
            }
        });


        this.algorithmSelector.setItems(FXCollections.observableArrayList("Algorithm 1", "Algorithm 2"));
        this.algorithmSelector.setTooltip(new Tooltip("Select the algorithm to be used when moving the sensors."));
        /*
         * Adds the elements contained by the original program to the mirror.
         */
        for (Node e : windowPane.getChildren()) {
            ORIGINAL_WINDOW.add(e);
        }

    }

    private void handleNumberOfNodesChanged() {
        String text = this.nodeField.getText().trim();
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
        redrawNodes();
        this.nodeField.setText("0");
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
        String text = this.radiusField.getText().trim();
        if (IO.isInteger(text) && Integer.valueOf(text) > 0 && Integer.valueOf(text) <= WINDOW_WIDTH) {
            System.out.println("Valid integer. Changing node radius.");
            NODE_RADIUS = Integer.valueOf(text);
            NUMBER_OF_NODES = WINDOW_WIDTH / NODE_RADIUS;
            System.out.println("Node radius is now: " + NODE_RADIUS);
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

    /**
     * Called when an invalid radius is entered by the user.
     */
    private void handleInvalidRadius() {
        // Remove the nodes due to invalid input.
        NUMBER_OF_NODES = 0;
        NODE_RADIUS = 0;
        redrawNodes();
        this.nodeField.setText("0");
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
        radiusField.setText("" + NODE_RADIUS);
        redrawSprites();
    }

    /**
     * Redraws the radius of the nodes.
     * Calls the sprite update method.
     */
    private void redrawRadius() {
        NUMBER_OF_NODES = (int) (WINDOW_WIDTH / NODE_RADIUS);
        // For the case that the nodes can't fill the domain after type casting.
        if (WINDOW_WIDTH / NODE_RADIUS != NUMBER_OF_NODES) NUMBER_OF_NODES += 1;

        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(ORIGINAL_WINDOW);
        nodeField.setText("" + NUMBER_OF_NODES);
        redrawSprites();
    }

    /**
     * Redraws the sprites on screen by calling
     * a function respective to the current algorithm.
     */
    private void redrawSprites() {
        try {
            if (this.algorithmSelector.getValue().equals(this.algorithmSelector.getItems().get(0))) {
                System.out.println("Using algorithm 1...");
                redrawSpritesWithAlgorithm1();
            } else if (this.algorithmSelector.getValue().equals(this.algorithmSelector.getItems().get(1))) {
                System.out.println("Using algorithm 2...");
                redrawSpritesWithAlgorithm2();
            }
        } catch (NullPointerException e) {
            errorSpecifyAlgorithm();
        }
    }

    /**
     * Called when the user has not yet specified an algorithm.
     * Displays a warning that notifies the user to specify an algorithm
     * and sets the input fields to "0".
     */
    private void errorSpecifyAlgorithm() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Algorithm");
        alert.setHeaderText("Algorithm");
        alert.setContentText("You must select an algorithm before anything else.");
        alert.showAndWait();
        this.radiusField.setText("0");
        this.nodeField.setText("0");

    }

    /**
     * Causes the sprites to be called and move around using algorithm 1.
     */
    private void redrawSpritesWithAlgorithm1() {
        TranslateTransition transition;
        double x;

        for (double i = 0; i < NUMBER_OF_NODES * NODE_RADIUS; i += NODE_RADIUS) {
            // Generate and assign random coordinates.
            x = assignRandomXPosition();
            // Create a Circle object to represent a sensor radius to be drawn.
            Circle sensorToDraw = new Circle(x, y, NODE_RADIUS / 2);
            // Make the nodes look cute.
            prettifySensor(sensorToDraw, 1);

            transition = new TranslateTransition();

            // Assign re-calculated coordinates to node i.
            x = i + (NODE_RADIUS / 2) - x;
            transition.setToX(x);

            // transition node i in a direction for the duration of a transition.
            transition.setDuration(Duration.seconds(TRANSITION_DURATION));
            transition.setNode(sensorToDraw);
            transition.play();
            windowPane.getChildren().add(sensorToDraw);
        }
    }

    /**
     * Determines what the sensors should look like. Called by each algorithm method.
     *
     * @param sensor          The sensor to apply to.
     * @param algorithmNumber The algorithm number to specify prettyness for.
     */
    private void prettifySensor(Circle sensor, int algorithmNumber) {
        Stop[] gradientStops;
        RadialGradient gradient = null;
        if (algorithmNumber == 1) {
            gradientStops = new Stop[]{new Stop(0, Color.BLACK), new Stop(1, Color.POWDERBLUE)};
            gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.2, true, CycleMethod.NO_CYCLE, gradientStops);
        } else if (algorithmNumber == 2) {
            // Make the nodes look cute.
            gradientStops = new Stop[]{new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
            gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.2, true, CycleMethod.NO_CYCLE, gradientStops);
        } else {
            System.err.println("No prettyness to add.");
        }
        sensor.setOpacity(0.9);
        sensor.setFill(gradient);
    }

    /**
     * Generates a random X value somewhere on the line.
     *
     * @return int
     */
    private int assignRandomXPosition() {
        int randomXPosition = WINDOW_WIDTH - (int) NODE_RADIUS;
        return new Random().nextInt(randomXPosition < 2 ? 1 : randomXPosition);
    }


    /**
     * Causes the sprites to be called and move around using algorithm 2.
     */
    private void redrawSpritesWithAlgorithm2() {
        TranslateTransition transition;
        double x;
        Queue<Circle> nodePositionQueue = new ArrayDeque<>((int) NUMBER_OF_NODES);
        LinkedList<Double> coordinates = getSensorPositions();
        for (double i = 0; i < NUMBER_OF_NODES * NODE_RADIUS; i += NODE_RADIUS) {
            x = assignRandomXPosition();

            // Create a Circle object to represent a sensor radius to be drawn.
            Circle sensorToDraw = new Circle(x, y, NODE_RADIUS / 2);
            prettifySensor(sensorToDraw, 2);

            transition = new TranslateTransition();

            x = i + (NODE_RADIUS / 2) - x;
            transition.setToX(coordinates.pop());

            // transition node i in a direction for the duration of a transition.
            transition.setDuration(Duration.seconds(TRANSITION_DURATION));
            transition.setNode(sensorToDraw);
            transition.play();
            windowPane.getChildren().add(sensorToDraw);
        }
    }

    /**
     * Generates a list of the correct node positions given the current number of nodes.
     *
     * @return LinkedList<Double>
     */
    private LinkedList<Double> getSensorPositions() {
        LinkedList<Double> list = new LinkedList<>();
        for (double coordinate = 0; coordinate < NUMBER_OF_NODES * NODE_RADIUS; coordinate += NODE_RADIUS) {
            list.addLast(coordinate);
        }
        return list;
    }


}