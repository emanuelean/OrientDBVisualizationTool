package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainMenuController;

/**
 *
 * @author Niels
 */
public class MainMenuView extends View {

    private MainMenuController controller;

    public MainMenuView() {
        super("fxml/MainMenu.fxml");
        controller = fxmlLoader.getController();
    }

}
