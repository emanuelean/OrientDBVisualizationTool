package com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions;

import com.mycompany.orientdbvisualizationtool.View.Node;
import com.mycompany.orientdbvisualizationtool.controller.MainController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Action taken when mouse pressed in center anchor pane
 */
public class CenterPanePressedAction implements EventHandler<MouseEvent> {

    private MainController controller;
    private ArrayList<Node> nodes;

    /**
     *
     * @param controller main controller class that contains source mouse x and y for mouse pressed
     * @param nodes list of nodes for keeping track of selection/deselection
     */
    public CenterPanePressedAction(MainController controller, ArrayList<Node> nodes) {
        this.controller = controller;
        this.nodes = nodes;
    }

    /**
     * Action taken in the anchor pane in the center. Used for deselection when
     * clicking and in addition used for recording the start point of a mouse
     * position for function centerPaneDragged();
     *
     * @param event Mouse event for mouse pressed
     */
    @Override
    public void handle(MouseEvent event) {
        controller.setMouseSourceX(event.getX());
        controller.setMouseSourceY(event.getY());
        for (Node node : nodes) {
            if (!node.isPressed()) {
                node.setSelected(false);
            }
        }
    }
}
