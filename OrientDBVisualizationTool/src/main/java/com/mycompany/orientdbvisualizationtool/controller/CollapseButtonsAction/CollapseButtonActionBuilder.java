package com.mycompany.orientdbvisualizationtool.controller.CollapseButtonsAction;

import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

public class CollapseButtonActionBuilder {
    private Button collapse_button;
    private SplitPane split_pane;
    private AnchorPane collapse_anchor_pane;
    private AnchorPane center_anchor_pane;
    private boolean left;

    public CollapseButtonActionBuilder setCollapse_Button(Button collapse_button) {
        this.collapse_button = collapse_button;
        return this;
    }

    public CollapseButtonActionBuilder setSplit_Pane(SplitPane split_pane) {
        this.split_pane = split_pane;
        return this;
    }

    public CollapseButtonActionBuilder setCollapse_Anchor_Pane(AnchorPane collapse_anchor_pane) {
        this.collapse_anchor_pane = collapse_anchor_pane;
        return this;
    }

    public CollapseButtonActionBuilder setCenter_Anchor_Pane(AnchorPane center_anchor_pane) {
        this.center_anchor_pane = center_anchor_pane;
        return this;
    }

    public CollapseButtonActionBuilder setLeft(boolean left) {
        this.left = left;
        return this;
    }

    public CollapseButtonAction createCollapseButtonAction() {
        if(left) {
            return new LeftCollapseButtonAction(collapse_button, split_pane, collapse_anchor_pane, center_anchor_pane);
        } else {
            return new RightCollapseButtonAction(collapse_button, split_pane, collapse_anchor_pane, center_anchor_pane);
        }
    }
}