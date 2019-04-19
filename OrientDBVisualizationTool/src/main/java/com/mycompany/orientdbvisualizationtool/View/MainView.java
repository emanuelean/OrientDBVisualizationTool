package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.VisTool;
import com.mycompany.orientdbvisualizationtool.controller.MainController;
//import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
//import com.mycompany.orientdbvisualizationtool.model.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
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
     *
     * @param primaryStage The basic underlying frame
     * @throws Exception for Application error handling
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("icons/sb-icon.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainDesign.fxml"));
        Parent root = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.setPlaceManager(PlaceManager.getInstance());
        controller.addRootNodeToPane();
        controller.populateTreeView();
        primaryStage.setTitle("Sustainable Buildings orientDB Visualizing Tool");
        Scene scene = new Scene(root, WIDTH, WIDTH * 9 / 16);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}