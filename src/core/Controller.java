package core;

import io.IO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    static int windowWidth = 700, windowHeight = 500;
    private static int numberOfNodes = 1, nodeRadius = 100;
    @FXML
    public Pane windowPane;
    @FXML
    private Button quitButton;
    @FXML
    private Button createGraph;
    @FXML
    private TextField nodeField;

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

        this.nodeField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("Number of nodes has changed.");
                handleNumberOfNodesChanged();
            }
        });

        this.windowPane.getChildren().add(new Ellipse(200, 200, 200, 200));
    }
    private void handleNumberOfNodesChanged() {
        String text = this.nodeField.getText();
        if (IO.isInteger(text) && Integer.valueOf(text) > 0) {
            System.out.println("Valid integer. Changing number of nodes.");
            numberOfNodes = Integer.valueOf(text);
            System.out.println("Number of nodes is now: " + numberOfNodes);
            System.out.println("Redrawing nodes...");
        } else {
            System.err.println("Invalid input. Waiting for a positive integer value.");
        }
    }



}
