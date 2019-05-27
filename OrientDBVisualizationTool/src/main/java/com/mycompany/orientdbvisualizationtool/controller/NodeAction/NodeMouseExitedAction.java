package com.mycompany.orientdbvisualizationtool.controller.NodeAction;

import com.mycompany.orientdbvisualizationtool.View.Node;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Action taken when mouse exists Node
 */
public class NodeMouseExitedAction implements EventHandler<MouseEvent> {

    private Node node;

    /**
     * constructor
     * @param node to be handled with mouse exited event
     */
    public NodeMouseExitedAction(Node node) {
        this.node = node;
    }

    /**
     * Node is no longer highlighted when exited
     * @param event for mouse exited.
     */
    @Override
    public void handle(MouseEvent event) {
        if (node.isSelected()) {
            node.getRectangle().setFill(Color.LAVENDER);
        } else {
            node.getRectangle().setFill(node.getDEFAULT_COLOR());
        }
    }
}
