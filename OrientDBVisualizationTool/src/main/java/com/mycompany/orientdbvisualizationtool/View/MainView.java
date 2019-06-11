package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;

/**
 * MainView sets the view components from fxml file and adds scene for the tree
 * view
 *
 * @author Yona, Niels, Albert
 */
public class MainView extends View {

    private MainController controller;
    private boolean hasStarted = false;

    /**
     * Constructor
     */
    public MainView() {
        super("fxml/MainDesign.fxml");
        controller = fxmlLoader.getController();
    }

    /**
     * Sets the controller
     *
     */
    @Override
    public void start() {
        if (!hasStarted) {
            controller.setPlaceManager(PlaceManager.getInstance());
            controller.addRootNodeToPane();
            controller.populateTreeView("");
            hasStarted = true;
        }
    }
}
