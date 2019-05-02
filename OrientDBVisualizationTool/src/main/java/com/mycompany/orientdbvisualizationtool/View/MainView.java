package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
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
public class MainView extends View{
    
    private MainController controller;

    public MainView() {
        super("fxml/MainDesign.fxml");
        controller = fxmlLoader.getController();
    }

    /**
     * Scene is created and the fxml file is loaded.
     *
     * @throws Exception for Application error handling
     */
    @Override
    public void start() {
        controller.setPlaceManager(PlaceManager.getInstance());
        controller.addRootNodeToPane();
        controller.populateTreeView();
    }
}
