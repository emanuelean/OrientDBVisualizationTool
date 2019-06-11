package com.mycompany.orientdbvisualizationtool.controller.NodeAction;

import com.mycompany.orientdbvisualizationtool.View.Node;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Action taken when Mouse enters in Node
 */
public class NodeMouseEnteredAction implements EventHandler<MouseEvent> {

    private Node node;

    /**
     * constructor
     *
     * @param node to be handled with mouse entered event
     */
    public NodeMouseEnteredAction(Node node) {
        this.node = node;
    }

    /**
     * Node is highlighted when entered
     *
     * @param event for mouse entered
     */
    @Override
    public void handle(MouseEvent event) {
        if (!node.isSelected()) {
            node.getRectangle().setFill(node.getDEFAULT_COLOR().deriveColor(0, 1, 0.8, 1));
        } else {
            node.getRectangle().setFill(node.getDEFAULT_COLOR().deriveColor(0, 1, 1 / 0.8, 1));
        }
    }
}
