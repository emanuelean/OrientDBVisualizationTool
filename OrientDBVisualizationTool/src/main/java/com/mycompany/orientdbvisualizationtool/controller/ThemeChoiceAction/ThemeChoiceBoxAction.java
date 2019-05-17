package com.mycompany.orientdbvisualizationtool.controller.ThemeChoiceAction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.Objects;

/**
 * Action taken when theme choice box is used/clicked.
 */
public class ThemeChoiceBoxAction implements ChangeListener<Number> {

    private AnchorPane Center_Anchor_Pane;
    private ChoiceBox Theme_Choice_Box;

    /**
     * constructor
     * @param Center_Anchor_Pane anchor pane to which theme adjustments are made to.
     * @param Theme_Choice_Box choice box that contains options for different themes.
     */
    public ThemeChoiceBoxAction(AnchorPane Center_Anchor_Pane, ChoiceBox Theme_Choice_Box) {
        this.Center_Anchor_Pane = Center_Anchor_Pane;
        this.Theme_Choice_Box = Theme_Choice_Box;
        setThemeChoiceBoxProperty();
    }

    /**
     * Setting the check choice box property
     */
    private void setThemeChoiceBoxProperty() {
        Theme_Choice_Box.getItems().addAll("Default Theme", "Dark Mode", "Natural Blue", "S.B. Green", "Crimson Red");
        Theme_Choice_Box.getSelectionModel().selectFirst();
    }

    /**
     * changes to be made when a new value is picked from choice box
     * @param observable observable number or index value
     * @param oldValue the index of the theme that was selected prior to the new selection
     * @param newValue the index of the theme that is newly selected
     */
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        File folder = new File(getClass().getResource("/styles").getPath());
        Center_Anchor_Pane.getScene().getStylesheets().remove("styles/" + Objects.requireNonNull(folder.listFiles())[oldValue.intValue()].getName());
        Center_Anchor_Pane.getScene().getStylesheets().add("styles/" + Objects.requireNonNull(folder.listFiles())[newValue.intValue()].getName());
    }
}
