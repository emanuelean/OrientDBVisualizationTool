package com.mycompany.orientdbvisualizationtool.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Line {

    private Node firstNode;
    private Node secondNode;

    public Edge(Node firstNode, Node secondNode) {
        setFill(Color.BLACK);
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }
}

