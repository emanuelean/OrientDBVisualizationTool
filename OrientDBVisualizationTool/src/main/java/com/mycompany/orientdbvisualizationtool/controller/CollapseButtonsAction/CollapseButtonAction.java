package com.mycompany.orientdbvisualizationtool.controller.CollapseButtonsAction;

import com.mycompany.orientdbvisualizationtool.View.MainView;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

/**
 * Action taken when collapse button is clicked.
 */
public abstract class CollapseButtonAction {

    private Button Collapse_Button;
    private SplitPane Split_Pane;
    private AnchorPane Collapse_Anchor_Pane;
    private AnchorPane Center_Anchor_Pane;
    private final int WIDTH = MainView.getWIDTH();
    private boolean left;

    /**
     * constructor
     * @param Collapse_Button button for collapsing pane
     * @param Split_Pane pane containing the pane that is to be collapsed
     * @param Collapse_Anchor_Pane pane to be collapsed/expanded
     * @param Center_Anchor_Pane pane centered within split pane
     * @param left for determining if the pane is left or right sided
     */
    public CollapseButtonAction(Button Collapse_Button, SplitPane Split_Pane, AnchorPane Collapse_Anchor_Pane, AnchorPane Center_Anchor_Pane, boolean left) {
        this.Collapse_Button = Collapse_Button;
        this.Split_Pane = Split_Pane;
        this.Collapse_Anchor_Pane = Collapse_Anchor_Pane;
        this.Center_Anchor_Pane = Center_Anchor_Pane;
        this.left = left;
        setPanelCollapseButtonProperty();
    }

    /**
     * Sets up the properties of panel collapse or panel contract button
     */
    private void setPanelCollapseButtonProperty() {
        if(left) {
            Collapse_Button.setOnAction(event -> {
                if (Split_Pane.getItems().contains(Collapse_Anchor_Pane)) {
                    Split_Pane.getItems().remove(Collapse_Anchor_Pane);
                    int splitPaneSize = Split_Pane.getItems().size();
                    if (splitPaneSize == 2) {
                        Split_Pane.setDividerPosition(0, 0.8);
                        Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                    } else if (splitPaneSize == 1) {
                        Center_Anchor_Pane.setPrefWidth(WIDTH);
                    }
                    Collapse_Button.setText("▶");
                } else {
                    Split_Pane.getItems().add(0, Collapse_Anchor_Pane);
                    int splitPaneSize = Split_Pane.getItems().size();
                    if (splitPaneSize == 2) {
                        Split_Pane.setDividerPosition(0, 0.2);
                        Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                    } else if (splitPaneSize == 3) {
                        Split_Pane.setDividerPosition(0, 0.2);
                        Split_Pane.setDividerPosition(1, 0.8);
                        Center_Anchor_Pane.setPrefWidth(WIDTH * .60);
                    }
                    Collapse_Button.setText("◀");
                }
            });
        } else {
            Collapse_Button.setOnAction(event -> {
                if (Split_Pane.getItems().contains(Collapse_Anchor_Pane)) {
                    Split_Pane.getItems().remove(Collapse_Anchor_Pane);
                    int splitPaneSize = Split_Pane.getItems().size();
                    if (splitPaneSize == 2) {
                        Split_Pane.setDividerPosition(0, 0.2);
                        Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                    } else if (splitPaneSize == 1) {
                        Center_Anchor_Pane.setPrefWidth(WIDTH);
                    }
                    Collapse_Button.setText("◀");
                } else {
                    Split_Pane.getItems().add(Split_Pane.getItems().size(), Collapse_Anchor_Pane);
                    int splitPaneSize = Split_Pane.getItems().size();
                    if (splitPaneSize == 2) {
                        Split_Pane.setDividerPosition(0, 0.8);
                        Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                    } else if (splitPaneSize == 3) {
                        Split_Pane.setDividerPosition(0, 0.2);
                        Split_Pane.setDividerPosition(1, 0.8);
                        Center_Anchor_Pane.setPrefWidth(WIDTH * .60);
                    }
                    Collapse_Button.setText("▶");
                }
            });
        }
    }
}
