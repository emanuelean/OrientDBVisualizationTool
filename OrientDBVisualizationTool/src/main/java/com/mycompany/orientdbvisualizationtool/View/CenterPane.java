/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orientdbvisualizationtool.View;


import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


/**
 * @author emanuelnae
 */
public class CenterPane extends StackPane {

    private double mouseSourceX = 0;
    private double mouseSourceY = 0;
    private Rectangle selectionArea = new Rectangle();
    private ArrayList<Node> nodes = new ArrayList<>();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = WIDTH * 9 / 16;
    private Scene scene;


    public CenterPane(Scene scene, ArrayList<Node> nodes) {
        this.scene = scene;
        this.nodes = nodes;
        setProperties();

    }

    private void setProperties() {
        this.setOnMousePressed(e -> {
            mouseSourceX = e.getSceneX();
            mouseSourceY = e.getSceneY();
        });


        this.getChildren().add(selectionArea);

        this.setOnMouseDragged(e -> {
                    //area selection using blue rectangle
                    if (e.isControlDown()) {
                        selectionArea.setVisible(true);
                        selectionArea.setX(mouseSourceX);
                        selectionArea.setY(mouseSourceY);
                        selectionArea.setTranslateX(mouseSourceX - WIDTH / 2 + selectionArea.getWidth() / 2);
                        selectionArea.setTranslateY(mouseSourceY - HEIGHT / 2 + selectionArea.getHeight() / 2);

                        selectionArea.setWidth(e.getX() - mouseSourceX);
                        selectionArea.setHeight(e.getY() - mouseSourceY + 13);
                        selectionArea.setFill(Color.rgb(0, 70, 255, 0.1));
                        selectionArea.setStroke(Color.LIGHTBLUE);
                        scene.setCursor(Cursor.CROSSHAIR);
                    }
                    //panning over the screen
                    if (e.isShiftDown()) {
                        double tempDiffX = mouseSourceX - e.getSceneX();
                        double tempDiffY = mouseSourceY - e.getSceneY();
                        scene.setCursor(Cursor.CLOSED_HAND);
                        for (Node node : nodes) {
                            node.setLocation(node.getTranslateX() - tempDiffX, node.getTranslateY() - tempDiffY);
                            mouseSourceX = e.getSceneX();
                            mouseSourceY = e.getSceneY();
                        }
                    }
                }
        );

        this.setOnMouseReleased(e -> {
                    //area selection
                    if (e.isControlDown()) {
                        for (Node node : nodes) {
                            if (selectionArea.intersects(node.getBoundsInParent())) {
                                node.setSelected(true);
                            } else {
                                node.setSelected(false);
                            }
                        }
                        selectionArea.setVisible(false);
                        scene.setCursor(Cursor.DEFAULT);
                    }
                    if (e.isShiftDown()) {
                        scene.setCursor(Cursor.DEFAULT);
                    }
                }
        );

    }

    public double getMouseSourceX() {
        return mouseSourceX;
    }

    public double getMouseSourceY() {
        return mouseSourceY;
    }

    public void setMouseSourceX(double mouseSourceX) {
        this.mouseSourceX = mouseSourceX;
    }

    public void setMouseSourceY(double mouseSourceY) {
        this.mouseSourceY = mouseSourceY;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
