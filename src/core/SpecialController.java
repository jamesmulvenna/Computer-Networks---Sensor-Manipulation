package core;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by chris on 2016-12-03.
 */
public class SpecialController implements Initializable {
    @FXML
    private Button quitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert quitButton != null;
        this.quitButton.setOnAction(handleQuitButton());
    }

    private EventHandler<ActionEvent> handleQuitButton() {
        return event -> {
            System.out.println("Application is about to quit!");
            System.exit(0);
        };
    }
}
