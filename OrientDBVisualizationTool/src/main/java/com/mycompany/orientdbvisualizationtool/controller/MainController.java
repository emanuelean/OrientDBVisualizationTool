package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.MainView;
import com.mycompany.orientdbvisualizationtool.View.Edge;
import com.mycompany.orientdbvisualizationtool.View.Node;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class MainController {

    @FXML
    public void initialize() {
        //TODO :: REMOVE DEMO NODES
        Node node1 = new Node("node1");
        Node node2 = new Node("node2");
        Node node3 = new Node("node3");
        Node node4 = new Node("node4");
        Node node5 = new Node("node5");
        node1.setLocation(300, 100);
        node2.setLocation(100, 200);
        node3.setLocation(200, 200);
        node4.setLocation(300, 200);
        node5.setLocation(400, 200);

        nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);

        Edge edge1 = new Edge(node1, node2);
        Edge edge2 = new Edge(node1, node3);
        Edge edge3 = new Edge(node1, node4);
        Edge edge4 = new Edge(node1, node5);
        Center_Anchor_Pane.getChildren().addAll(edge1, edge2, edge3, edge4);
        node1.addToParentPane(Center_Anchor_Pane);
        node2.addToParentPane(Center_Anchor_Pane);
        node3.addToParentPane(Center_Anchor_Pane);
        node4.addToParentPane(Center_Anchor_Pane);
        node5.addToParentPane(Center_Anchor_Pane);
        //TODO :: DEMO ENDS HERE

        selectionArea = new Rectangle();
        selectionArea.setFill(Color.rgb(0, 70, 255, 0.1));
        selectionArea.setStroke(Color.LIGHTBLUE);
        Center_Anchor_Pane.getChildren().add(selectionArea);
        Center_Anchor_Pane.setPrefWidth(WIDTH * .52);
        Center_Anchor_Pane.setPrefHeight(WIDTH * 9 / 16);
    }


    /**
     * Center anchor pane controls
     **/
    @FXML
    public AnchorPane Center_Anchor_Pane;

    private Rectangle selectionArea;
    private double mouseSourceX = 0;
    private double mouseSourceY = 0;
    private static final int WIDTH = MainView.WIDTH;
    private ArrayList<Node> nodes;

    /**
     * Action taken in the anchor pane in the center.
     * @param mouseEvent Mouse event for mouse dragged
     */
    public void centerPaneDragged(MouseEvent mouseEvent) {
        //area selection using blue rectangle
        if (mouseEvent.isControlDown()) {
            selectionArea.setVisible(true);
            selectionArea.setTranslateX(mouseSourceX);
            selectionArea.setTranslateY(mouseSourceY);

            double selectionAreaWidth = mouseEvent.getX() - mouseSourceX;
            double selectionAreaHeight = mouseEvent.getY() - mouseSourceY;

            if (selectionAreaHeight < 0 && selectionAreaWidth < 0) {
                selectionArea.setTranslateX(mouseEvent.getX());
                selectionArea.setTranslateY(mouseEvent.getY());
                selectionArea.setWidth(-selectionAreaWidth);
                selectionArea.setHeight(-selectionAreaHeight);
            } else if (selectionAreaHeight >= 0 && selectionAreaWidth < 0) {
                selectionArea.setTranslateX(mouseEvent.getX());
                selectionArea.setTranslateY(mouseSourceY);
                selectionArea.setWidth(-selectionAreaWidth);
                selectionArea.setHeight(selectionAreaHeight);
            } else if (selectionAreaHeight < 0 && selectionAreaWidth >= 0) {
                selectionArea.setTranslateX(mouseSourceX);
                selectionArea.setTranslateY(mouseEvent.getY());
                selectionArea.setWidth(selectionAreaWidth);
                selectionArea.setHeight(-selectionAreaHeight);
            } else if (selectionAreaHeight >= 0 && selectionAreaWidth >= 0) {
                selectionArea.setWidth(selectionAreaWidth);
                selectionArea.setHeight(selectionAreaHeight);
            }
            Center_Anchor_Pane.getScene().setCursor(Cursor.CROSSHAIR);
        }
    }

    /**
     * Action taken in the anchor pane in the center.
     * @param mouseEvent Mouse event for mouse pressed
     */
    public void centerPanePressed(MouseEvent mouseEvent) {
        mouseSourceX = mouseEvent.getX();
        mouseSourceY = mouseEvent.getY();
        Point2D point = new Point2D(mouseSourceX, mouseSourceY);
        for (Node node : nodes) {
            if (!node.getRectangleAndLabel().getBoundsInParent().contains(point)) {
                node.setSelected(false);
            }
        }
    }

    /**
     * Action taken in the anchor pane in the center.
     * @param mouseEvent Mouse event for mouse drag released
     */
    public void centerPaneReleased(MouseEvent mouseEvent) {
        //area selection
        if (mouseEvent.isControlDown()) {
            for (Node node : nodes) {
                if (selectionArea.getBoundsInParent().intersects(node.getRectangleAndLabel().getBoundsInParent())) {
                    node.setSelected(true);
                } else {
                    node.setSelected(false);
                }
            }
            Center_Anchor_Pane.getScene().setCursor(Cursor.DEFAULT);
        }
        selectionArea.setVisible(false);
    }

}
