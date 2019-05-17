package com.mycompany.orientdbvisualizationtool.controller.ShowHideNodeAction;

import com.mycompany.orientdbvisualizationtool.View.Edge;
import com.mycompany.orientdbvisualizationtool.View.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

/**
 * Action taken when the show all nodes button is pressed
 */
public class ShowNodesButtonAction implements EventHandler<ActionEvent> {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    /**
     * constructor
     * @param nodes list of nodes for keeping track of selection/deselection
     * @param edges list of edges for keeping track of selection/deselection
     */
    public ShowNodesButtonAction(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    /**
     * shows hidden nodes by setting their visibility to true.
     * @param event action event for button
     */
    @Override
    public void handle(ActionEvent event) {
        for (Node node : nodes) {
            node.setVisible(true);
        }
        for (Edge edge : edges) {
            edge.setVisible(true);
        }
    }
}
