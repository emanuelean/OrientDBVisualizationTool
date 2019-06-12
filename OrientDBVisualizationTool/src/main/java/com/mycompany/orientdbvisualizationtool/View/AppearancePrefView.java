package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.PreferencesMenuController;

/**
 * Used for the preference view appearance
 * 
 * @author Yona
 */
public class AppearancePrefView extends View {

    private PreferencesMenuController controller;

    /**
     * Constructor
     *
     */
    public AppearancePrefView() {
        super("fxml/PreferencesMenu.fxml");
        controller = fxmlLoader.getController();
    }

    /**
     * sets the controller for the view
     *
     * @return controller
     */
    public PreferencesMenuController getController() {
        return controller;
    }
}
