package com.mycompany.orientdbvisualizationtool.controller.ShowHideNodeAction;

import com.mycompany.orientdbvisualizationtool.View.Edge;
import com.mycompany.orientdbvisualizationtool.View.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

/**
 * Action taken when hide nodes button is pressed
 */
public class HideNodesButtonAction implements EventHandler<ActionEvent> {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    /**
     * constructor
     * @param nodes list of nodes for keeping track of selection/deselection
     * @param edges list of edges for keeping track of selection/deselection
     */
    public HideNodesButtonAction(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    /**
     * hides selected nodes by setting their visibility to false
     * @param event action event for button
     */
    @Override
    public void handle(ActionEvent event) {
        for (Node node : nodes) {
            if (node.isSelected()) {
                node.setVisible(false);
                for (Edge edge : edges) {
                    if (edge.getFirstNode() == node || edge.getSecondNode() == node) {
                        edge.setVisible(false);
                    }
                }
            }
        }
    }
}
