package com.mycompany.orientdbvisualizationtool.controller.ThemeChoiceAction;

import com.mycompany.orientdbvisualizationtool.View.View;
import com.mycompany.orientdbvisualizationtool.View.VisApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.util.Objects;
import javafx.scene.Scene;

/**
 * Action taken when theme choice box is used/clicked.
 */
public class ThemeChoiceBoxAction implements ChangeListener<Number> {

    private ChoiceBox Theme_Choice_Box;

    /**
     * constructor
     *
     * @param Theme_Choice_Box   choice box that contains options for different themes.
     */
    public ThemeChoiceBoxAction(ChoiceBox Theme_Choice_Box) {
        this.Theme_Choice_Box = Theme_Choice_Box;
        setThemeChoiceBoxProperty();
    }

    /**
     * Setting the check choice box property
     */
    private void setThemeChoiceBoxProperty() {
        Theme_Choice_Box.getItems().addAll("Default Theme", "Dark Mode", "Natural Blue", "S.B. Green", "Crimson Red", "Custom Theme");
        Theme_Choice_Box.getSelectionModel().selectFirst();
    }

    /**
     * changes to be made when a new value is picked from choice box
     *
     * @param observable observable number or index value
     * @param oldValue   the index of the theme that was selected prior to the new selection
     * @param newValue   the index of the theme that is newly selected
     */
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (newValue.intValue() != 5) {
            File folder = new File(getClass().getResource("/styles").getPath());
            Scene currentScene = VisApplication.getInstance().getScene();
            currentScene.getStylesheets().remove("styles/" + Objects.requireNonNull(folder.listFiles())[oldValue.intValue()].getName());
            currentScene.getStylesheets().add("styles/" + Objects.requireNonNull(folder.listFiles())[newValue.intValue()].getName());
        } else {
            VisApplication.getInstance().changeToPreferences();
        }
    }
}
