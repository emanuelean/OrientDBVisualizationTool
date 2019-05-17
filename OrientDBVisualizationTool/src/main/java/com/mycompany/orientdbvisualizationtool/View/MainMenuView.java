package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainMenuController;

/**
 * MainView sets the view components from fxml file and adds scene for the main
 * menu
 *
 * @author Niels
 */
public class MainMenuView extends View {

    private MainMenuController controller;

    /**
     * Constructor
     */
    public MainMenuView() {
        super("fxml/MainMenu.fxml");
        controller = fxmlLoader.getController();
    }

}
