package com.mycompany.orientdbvisualizationtool.controller.CollapseButtonsAction;

import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

/**
 * A builder for action taken when collapse button is clicked.
 */
public class CollapseButtonActionBuilder {

    private Button collapse_button;
    private SplitPane split_pane;
    private AnchorPane collapse_anchor_pane;
    private AnchorPane center_anchor_pane;
    private Boolean isLeft;

    /**
     * setting the Collapse_Button
     *
     * @param collapse_button button that collapses panel
     * @return
     */
    public CollapseButtonActionBuilder setCollapse_Button(Button collapse_button) {
        this.collapse_button = collapse_button;
        return this;
    }

    /**
     * setting the split_pane
     *
     * @param split_pane container for the panel to be collapsed
     * @return
     */
    public CollapseButtonActionBuilder setSplit_Pane(SplitPane split_pane) {
        this.split_pane = split_pane;
        return this;
    }

    /**
     * setting collapse_anchor_pane
     *
     * @param collapse_anchor_pane the isLeft or right pane that is to be collapsed
     * @return
     */
    public CollapseButtonActionBuilder setCollapse_Anchor_Pane(AnchorPane collapse_anchor_pane) {
        this.collapse_anchor_pane = collapse_anchor_pane;
        return this;
    }

    /**
     * setting the center_anchor_pane
     *
     * @param center_anchor_pane the pane on the center that adjusts size accordingly to the
     *                           collapse or expansion of isLeft/right pane
     * @return
     */
    public CollapseButtonActionBuilder setCenter_Anchor_Pane(AnchorPane center_anchor_pane) {
        this.center_anchor_pane = center_anchor_pane;
        return this;
    }

    /**
     * setting the isLeft parameter
     *
     * @param isLeft boolean to distinguish betten isLeft or right panel.
     * @return
     */
    public CollapseButtonActionBuilder setIsLeft(Boolean isLeft) {
        this.isLeft = isLeft;
        return this;
    }

    /**
     * the creation of the CollapseButtonAction
     *
     * @return
     */
    public CollapseButtonAction createCollapseButtonAction() {
        if (isLeft) {
            validate();
            return new LeftCollapseButtonAction(collapse_button, split_pane, collapse_anchor_pane, center_anchor_pane);
        } else {
            validate();
            return new RightCollapseButtonAction(collapse_button, split_pane, collapse_anchor_pane, center_anchor_pane);
        }
    }

    private void validate() {
        if (isLeft == null) {
            throw new IllegalStateException("Parameter isLeft is not set and is required for build.");
        }
        if(collapse_button == null) {
            throw new IllegalStateException("Parameter collapse_button is not set and is required for build.");
        }
        if(split_pane == null) {
            throw new IllegalStateException("Parameter split_pane is not set and is required for build.");
        }
        if(collapse_anchor_pane == null) {
            throw new IllegalStateException("Parameter collapse_anchor_pane is not set and is required for build.");
        }
        if(center_anchor_pane == null) {
            throw new IllegalStateException("Parameter center_anchor_pane is not set and is required for build.");
        }
    }
}