package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Node represents a vertex in view.
 *
 * @author Emanuel Nae, Yona Moreda
 */
public class Node extends Rectangle {

    private Text label;
    private String nodeName;
    private String nodeId;
    private String type;
    private boolean selected;
    private StackPane rectangleAndLabel;
    private final Color DEFAULT_COLOR = Color.LIGHTGRAY;
    private MainController mainController;

    public Node(String id, String nodeName, String type) {
        this.nodeId = id;
        this.nodeName = nodeName;
        this.type = type;
        this.label = new Text(type + ": " + id + ":" + nodeName);
        this.label.setFont(new Font(13));
        this.setWidth(label.getLayoutBounds().getWidth() + 30);
        this.setHeight(40.0f);
        this.selected = false;
        this.rectangleAndLabel = new StackPane();
        rectangleAndLabel.getChildren().addAll(this, this.getLabel());

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
        rectangleAndLabel.setOnMousePressed(event -> {
                    if (!this.isSelected()) {
                        this.setSelected(true);
                    } else {
                        this.setSelected(false);
                    }
                }
        );
        rectangleAndLabel.setOnMouseClicked(event -> {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        //Double click
                        if (event.getClickCount() == 2) {
                            mainController.expandNode(this);
                        }
                    }
                }
        );
    }

    /**
     * gets label
     *
     * @return javafx label
     */
    public Text getLabel() {
        return label;
    }

    /**
     * Moves node to point
     *
     * @param x horizontal position of node
     * @param y vertical position of node
     */
    public void setLocation(double x, double y) {
        this.rectangleAndLabel.setTranslateX(x);
        this.rectangleAndLabel.setTranslateY(y);
    }

    /**
     * Adds node to the central pane.
     *
     * @param centerPane parent pane on the center of scene.
     */
    public void addToParentPane(AnchorPane centerPane) {
        centerPane.getChildren().add(rectangleAndLabel);
    }

    public void addToVBox(VBox vBox) {
        vBox.getChildren().add(rectangleAndLabel);
        vBox.layout();
    }

    /**
     * Property of node selection.
     *
     * @return selection boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selection boolean property and the color accordingly
     *
     * @param selected selection boolean
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            this.setFill(Color.LAVENDER);
            this.mainController.showSelectedNodeDetails(this);
        } else {
            this.setFill(DEFAULT_COLOR);
        }
    }

    /**
     * encapsulates Rectangle and Text Label as Node
     *
     * @return Pane for representing a Node (rectangle + label)
     */
    public StackPane getRectangleAndLabel() {
        return rectangleAndLabel;
    }

    public String getNodeId() {
        return nodeId;
    }

    /**
     * Assigns the controller instance to node in view
     *
     * @param mainController the main controller
     */
    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getType() {
        return type;
    }
}
