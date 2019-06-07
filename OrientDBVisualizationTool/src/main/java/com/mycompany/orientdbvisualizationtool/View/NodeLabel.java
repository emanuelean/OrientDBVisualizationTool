package com.mycompany.orientdbvisualizationtool.View;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.text.Font;

/**
 * NodeLabel represents the label to display the name for node
 */
public class NodeLabel extends Label {

    /**
     * constructor
     *
     * @param displayName the string that the label displays
     */
    public NodeLabel(String displayName) {
        super();
        this.setText(displayName);
        this.setAlignment(Pos.CENTER);
        this.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);
        this.setMaxWidth(170);
        this.setFont(new Font(13));
        this.getStyleClass().remove("label");
        this.setId("NodeLabel");
    }
}
