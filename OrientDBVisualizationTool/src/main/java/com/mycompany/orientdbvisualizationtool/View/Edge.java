package com.mycompany.orientdbvisualizationtool.View;

import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Represents the line that connects two nodes in view.
 * Connects two nodes from view.
 */
public class Edge extends Line {

    private Node firstNode;
    private Node secondNode;
    private AnchorPane anchorPane;

    public Edge(Node firstNode, Node secondNode, AnchorPane anchorPane) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.anchorPane = anchorPane;
        //id for css
        this.setId("Edge");
        this.setStartEndPoints();
    }

    /**
     * Sets the start/end points for connecting nodes
     */
    private void setStartEndPoints() {

        Bounds firstNodePoint = anchorPane.sceneToLocal(firstNode.localToScene(firstNode.getBoundsInLocal()));
        Bounds secondNodePoint = anchorPane.sceneToLocal(secondNode.localToScene(secondNode.getBoundsInLocal()));

        double firstNodeOffset = (firstNode.getParent().getBoundsInLocal().getWidth() - firstNode.getRectangle().getBoundsInLocal().getWidth()) / 2;
        double secondNodeOffset = (secondNode.getParent().getBoundsInLocal().getWidth() - secondNode.getWidth()) / 2;

        this.setStartX(firstNodePoint.getMaxX() - firstNodeOffset);
        this.setStartY(firstNodePoint.getMaxY() - firstNode.getHeight() / 2);
        this.setEndX(secondNodePoint.getMinX() + secondNodeOffset);
        this.setEndY(secondNodePoint.getMaxY() - secondNode.getHeight() / 2);
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }
}

