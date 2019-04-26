/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
import com.mycompany.orientdbvisualizationtool.controller.MainMenuController;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class MainMenuView extends Application {

    public static final int WIDTH = 1400;

    /**
     * Scene is created and the fxml file is loaded.
     * @param primaryStage The basic underlying frame
     * @throws Exception for Application error handling
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("icons/sb-icon.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainMenu.fxml"));
        Parent root = fxmlLoader.load();
        MainMenuController controller = fxmlLoader.getController();
        primaryStage.setTitle("Sustainable Buildings orientDB Visualizing Tool");
        Scene scene = new Scene(root, WIDTH, WIDTH * 9 / 16);
        primaryStage.setScene(scene);

//TODO:: UNCOMMENT        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
