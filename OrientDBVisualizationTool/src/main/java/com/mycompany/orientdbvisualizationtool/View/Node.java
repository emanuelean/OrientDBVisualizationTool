package com.mycompany.orientdbvisualizationtool.View;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Node represents a vertex in view.
 * @author Emanuel Nae, Yona Moreda
 */
public class Node extends Rectangle {

    private Text label;
    private boolean selected;
    private StackPane rectangleAndLabel;
    private final Color DEFAULT_COLOR = Color.LIGHTGRAY;

    public Node(String nodeName) {
        this.label = new Text(nodeName);
        this.label.setFont(new Font(13));
        this.setWidth(label.getLayoutBounds().getWidth() + 30);
        this.setHeight(40.0f);
        this.selected = false;
        this.rectangleAndLabel = new StackPane();

        //rounded rectangle
        this.setArcWidth(40);
        this.setArcHeight(40);
        this.setFill(DEFAULT_COLOR);
        this.setStroke(Color.DARKGREY);

        this.setMouseListenerProperties();
    }

    /**
     * Sets properties for mouse events.
     */
    private void setMouseListenerProperties() {
        this.setOnMousePressed(e -> {
                    if (!this.isSelected()) {
                        this.setSelected(true);
                    } else {
                        this.setSelected(false);
                    }
                }
        );
        label.setOnMousePressed(e -> {
                    if (!this.isSelected()) {
                        this.setSelected(true);
                    } else {
                        this.setSelected(false);
                    }
                }
        );
    }

    /**
     * gets label
     * @return javafx label
     */
    public Text getLabel() {
        return label;
    }

    /**
     * Moves node to point
     * @param x horizontal position of node
     * @param y vertical position of node
     */
    public void setLocation(double x, double y) {
        this.rectangleAndLabel.setTranslateX(x);
        this.rectangleAndLabel.setTranslateY(y);
    }

    /**
     * Adds node to the central pane.
     * @param centerPane parent pane on the center of scene.
     */
    public void addToParentPane(AnchorPane centerPane) {
        rectangleAndLabel.getChildren().addAll(this, this.getLabel());
        centerPane.getChildren().add(rectangleAndLabel);
    }

    /**
     * Property of node selection.
     * @return selection boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selection boolean property and the color accordingly
     * @param selected selection boolean
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            this.setFill(Color.LAVENDER);
        } else {
            this.setFill(DEFAULT_COLOR);
        }
    }

    /**
     * encapsulates Rectangle and Text Label as Node
     * @return Pane for representing a Node (rectangle + label)
     */
    public StackPane getRectangleAndLabel() {
        return rectangleAndLabel;
    }
}
