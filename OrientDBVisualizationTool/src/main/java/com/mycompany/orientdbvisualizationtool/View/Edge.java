package com.mycompany.orientdbvisualizationtool.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Represents the line that connects two nodes in view.
 * Connects two nodes from view.
 */
public class Edge extends Line {

    private Node firstNode;
    private Node secondNode;

    public Edge(Node firstNode, Node secondNode) {
        this.setFill(Color.BLACK);
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.setStartEndPoints();
    }

    /**
     * Sets the start/end points for connecting nodes
     */
    private void setStartEndPoints() {
        this.setStartX(firstNode.getRectangleAndLabel().getTranslateX() + firstNode.getWidth() / 2);
        this.setStartY(firstNode.getRectangleAndLabel().getTranslateY()+ firstNode.getHeight());
        this.setEndX(secondNode.getRectangleAndLabel().getTranslateX()+ secondNode.getWidth() / 2);
        this.setEndY(secondNode.getRectangleAndLabel().getTranslateY());
    }
}

