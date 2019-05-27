package com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions;

import com.mycompany.orientdbvisualizationtool.View.Node;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Action taken in the center anchor pane when mouse released.
 */
public class CenterPaneReleasedAction implements EventHandler<MouseEvent> {

    private Rectangle selectionArea;
    private AnchorPane Center_Anchor_Pane;
    private ArrayList<Node> nodes;

    /**
     * constructor
     * @param selectionArea rectangle drawn to represent an area selection
     * @param Center_Anchor_Pane anchor to which the mouse released event occurs
     * @param nodes list of nodes for keeping track of selection/deselection
     */
    public CenterPaneReleasedAction(Rectangle selectionArea, AnchorPane Center_Anchor_Pane, ArrayList<Node> nodes) {
        this.selectionArea = selectionArea;
        this.Center_Anchor_Pane = Center_Anchor_Pane;
        this.nodes = nodes;
    }

    /**
     * Action taken in the anchor pane in the center.
     *
     * @param event Mouse event for mouse drag released
     */
    @Override
    public void handle(MouseEvent event) {
        //area selection
        if (event.isControlDown()) {
            if (!nodes.isEmpty()) {
                for (Node node : nodes) {
                    Bounds selectionAreaBounds = selectionArea.localToScene(selectionArea.getBoundsInLocal());
                    Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
                    if (selectionAreaBounds.intersects(nodeBounds)) {
                        node.setSelected(true);
                    } else {
                        node.setSelected(false);
                    }
                }
                Center_Anchor_Pane.getScene().setCursor(Cursor.DEFAULT);
            }
        }
        selectionArea.setVisible(false);
    }
}
