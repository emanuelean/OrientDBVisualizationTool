package com.mycompany.orientdbvisualizationtool.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * MainView sets the view components from fxml file and adds scene
 */
public class MainView extends Application {

    public static final int WIDTH = 1400;

    /**
     * Scene is created and the fxml file is loaded.
     * @param primaryStage The basic underlying frame
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("sb-icon.png"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainDesign.fxml"));
        primaryStage.setTitle("Sustainable Buildings orientDB Visualizing Tool");
        Scene scene = new Scene(root, WIDTH, WIDTH * 9 / 16);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
