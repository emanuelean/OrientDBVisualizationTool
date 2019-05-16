package com.mycompany.orientdbvisualizationtool.View;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Represents the line that connects two nodes in view. Connects two nodes from
 * view.
 */
public class Edge extends Line {

    private Node firstNode;
    private Node secondNode;

    /**
     * Constructor
     *
     * @param firstNode The first node that the edge is connected to
     * @param secondNode The second node that the edge is connected to
     */
    public Edge(Node firstNode, Node secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        //id for css
        this.setId("Edge");
        this.setStartEndPoints();
    }

    /**
     * Sets the start/end points for connecting nodes
     */
    private void setStartEndPoints() {
        Pane parentPane = firstNode.getContainerPane();
        Bounds boundsInSceneParent = parentPane.sceneToLocal(firstNode.localToScene(firstNode.getBoundsInLocal()));
        Bounds boundsInSceneChild = parentPane.sceneToLocal(secondNode.localToScene(secondNode.getBoundsInLocal()));

        this.setStartX(boundsInSceneParent.getMaxX());
        this.setStartY(boundsInSceneParent.getMaxY() - firstNode.getHeight() / 2);
        this.setEndX(boundsInSceneChild.getMinX());
        this.setEndY(boundsInSceneChild.getMaxY() - secondNode.getHeight() / 2);
    }

    /**
     *
     * @return The first node
     */
    public Node getFirstNode() {
        return firstNode;
    }

    /**
     *
     * @return The second node
     */
    public Node getSecondNode() {
        return secondNode;
    }

    /**
     *
     * @param firstNode start node of edge line
     */
    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    /**
     *
     * @param secondNode end node of edge line
     */
    public void setSecondNode(Node secondNode) {
        this.secondNode = secondNode;
    }
}
