package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;

/**
 * a special control node for showing more nodes used when the number of
 * children nodes is too large
 */
public class ShowMoreNode extends Node {

    private int remainingNodes;

    /**
     * Constructor
     *
     * @param controller main controller for node
     * @param remainingNodes The remaining nodes
     */
    public ShowMoreNode(MainController controller, int remainingNodes) {
        super("Show more node", "", "Show more(" + remainingNodes + ")", controller);
        this.remainingNodes = remainingNodes;
    }
}
