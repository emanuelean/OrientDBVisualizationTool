package com.mycompany.orientdbvisualizationtool.View;

import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
        this.setStroke(Color.BLACK);
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.anchorPane = anchorPane;
        this.setStartEndPoints();
    }

    /**
     * Sets the start/end points for connecting nodes
     */
    private void setStartEndPoints() {
        StackPane firstNodePane = firstNode.getRectangleAndLabel();
        StackPane secondNodePane = secondNode.getRectangleAndLabel();

        Bounds firstNodePoint = anchorPane.sceneToLocal(firstNodePane.localToScene(firstNodePane.getBoundsInLocal()));
        Bounds secondNodePoint = anchorPane.sceneToLocal(secondNodePane.localToScene(secondNodePane.getBoundsInLocal()));

        double firstNodeOffset = (firstNodePane.getParent().getBoundsInLocal().getWidth() - firstNode.getBoundsInLocal().getWidth()) / 2;
        double secondNodeOffset = (secondNodePane.getParent().getBoundsInLocal().getWidth() - secondNodePane.getWidth()) / 2;

        this.setStartX(firstNodePoint.getMaxX() - firstNodeOffset);
        this.setStartY(firstNodePoint.getMaxY() - firstNodePane.getHeight() / 2);
        this.setEndX(secondNodePoint.getMinX() + secondNodeOffset);
        this.setEndY(secondNodePoint.getMaxY() - secondNodePane.getHeight() / 2);
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }
}

