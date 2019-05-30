package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.PreferencesMenuController;

public class ApperancePrefView extends View {

    private PreferencesMenuController controller;

    /**
     * Constructor
     *
     */
    public ApperancePrefView() {
        super("fxml/PreferencesMenu.fxml");
        controller = fxmlLoader.getController();
    }

    public PreferencesMenuController getController() {
        return controller;
    }
}
