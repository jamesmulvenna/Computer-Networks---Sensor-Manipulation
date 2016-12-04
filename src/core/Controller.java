package core;

import io.IO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static ArrayList<Node> originalWindow = new ArrayList<>();
    static int windowWidth = 700, windowHeight = 500;
    private static float numberOfNodes = 1, nodeRadius = 100;
    @FXML
    public Pane windowPane;
    @FXML
    private Button quitButton;
    @FXML
    private Button createGraph;
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

        this.createGraph.setOnMouseClicked(event -> {
            System.out.println("Application is about to create graph.");
        });

        this.nodeField.setOnKeyReleased(event -> {
            System.out.println("Number of nodes has changed.");
            handleNumberOfNodesChanged();
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
            redraw();
        } else {
            System.err.println("Invalid input. Waiting for a positive integer value.");
        }
    }

    private void redraw() {
        nodeRadius = windowWidth / numberOfNodes;

        windowPane.getChildren().clear();
        windowPane.getChildren().addAll(originalWindow);

        for (float i = nodeRadius / 2; i < numberOfNodes * nodeRadius; i += nodeRadius) {
            Ellipse e = new Ellipse(i, xAxis.getStartY() + xAxis.getEndY() + nodeRadius / 2, nodeRadius / 2, nodeRadius / 2);
            e.setCenterY(225);
            windowPane.getChildren().add(e);
        }

        radiusField.setText("" + nodeRadius);

    }


}
