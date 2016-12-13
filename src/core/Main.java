package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 *   This is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License.
 *   If not, see <http://www.gnu.org/licenses/>.
 *
 *  Authors:
 *  Christopher McMorran (100968013)
 *  James Mulvenna (100965629)
 *  Jenish Zalavadiya (100910343)
 *
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        Canvas canvas = new Canvas(Controller.WINDOW_WIDTH, Controller.WINDOW_HEIGHT);
        try {
            System.out.println("Loading main interface...");
            root = FXMLLoader.load(getClass().getClassLoader().getResource("core/MainApplication.fxml"));
        } catch (IOException e) {
            System.err.println("(FATAL) Missing file: MainApplication.fxml");
            System.exit(-1);
        }
        System.out.println("Main interface loaded.");
        primaryStage.setTitle("COMP 3203 Project");
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


