package com.mycompany.orientdbvisualizationtool.controller.NodeAction;

import com.mycompany.orientdbvisualizationtool.View.Node;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Action taken when mouse is pressed on a Node
 */
public class NodeMousePressedAction implements EventHandler<MouseEvent> {

    private Node node;

    /**
     * constructor
     *
     * @param node for setting selection when mouse pressed
     */
    public NodeMousePressedAction(Node node) {
        this.node = node;
    }

    /**
     * Node is selected when mouse pressed
     *
     * @param event event for setting selection
     */
    @Override
    public void handle(MouseEvent event) {
        if (!node.isSelected()) {
            node.setSelected(true);
        } else {
            node.setSelected(false);
        }
    }
}
