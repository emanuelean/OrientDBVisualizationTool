package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
import com.mycompany.orientdbvisualizationtool.controller.NodeAction.NodeMouseClickedAction;
import com.mycompany.orientdbvisualizationtool.controller.NodeAction.NodeMouseEnteredAction;
import com.mycompany.orientdbvisualizationtool.controller.NodeAction.NodeMouseExitedAction;
import com.mycompany.orientdbvisualizationtool.controller.NodeAction.NodeMousePressedAction;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Node represents a vertex in view.
 *
 * @author Emanuel Nae, Yona Moreda
 */
public class Node extends StackPane {

    private NodeLabel nodeLabel;
    private String nodeId;
    private boolean selected;
    private Rectangle rectangle;
    private Color DEFAULT_COLOR;
    private Color DEFAULT_SELECTED_COLOR;
    private MainController mainController;
    private Boolean expanded;
    private VBox childrenVBox;
    private Pane containerPane;

    /**
     * Constructor
     *
     * @param id The id of the node
     * @param NodeType The type of the node
     * @param displayName The display name of the node
     */
    public Node(String id, String NodeType, String displayName, MainController controller) {
        this.nodeId = id;
        this.selected = false;
        this.expanded = false;
        this.DEFAULT_COLOR = Color.LIGHTGRAY;
        this.DEFAULT_SELECTED_COLOR = Color.LAVENDER;

        this.setId("NodeStackPane");
        this.nodeLabel = new NodeLabel(displayName);
        this.mainController = controller;
        this.setRectangleProperty();
        this.setLayoutProperties();

        Tooltip.install(this, new Tooltip("A " + NodeType + " entity\nDouble click here to expand or contract"));
        this.setMouseListenerProperties();
    }

    /**
     * Sets up the packing layout elements such as childrenVBox and containerPane
     */
    private void setLayoutProperties() {
        this.getChildren().addAll(rectangle, this.getLabel());
        this.layout();

        this.childrenVBox = new VBox(15);
        this.childrenVBox.setLayoutX(this.getBoundsInLocal().getWidth() + 50);
        this.childrenVBox.setLayoutY(0);
        this.childrenVBox.layout();

        this.containerPane = new Pane();
        this.containerPane.getChildren().addAll(this, childrenVBox);
        this.containerPane.layout();
    }

    /**
     * Sets the properties of a rectangle
     */
    private void setRectangleProperty() {
        //rounded rectangle
        this.rectangle = new Rectangle();
        this.rectangle.setWidth(nodeLabel.getMaxWidth() + 30);
        this.rectangle.setHeight(40.0f);
        this.rectangle.setArcWidth(40);
        this.rectangle.setArcHeight(40);
        this.rectangle.setFill(DEFAULT_COLOR);
        this.rectangle.setStroke(Color.DARKGREY);
        this.rectangle.setId("NodeRectangle");
    }

    /**
     * Sets properties for mouse events. Mouse pressed -> selection 2x Mouse
     * click -> expansion of node Mouse entered/exited -> highlight node
     */
    private void setMouseListenerProperties() {
        this.setOnMousePressed(new NodeMousePressedAction(this));
        this.setOnMouseClicked(new NodeMouseClickedAction(this, mainController, mainController.getNodes(), mainController.getEdges()));
        this.setOnMouseEntered(new NodeMouseEnteredAction(this));
        this.setOnMouseExited(new NodeMouseExitedAction(this));

    }

    /**
     * gets label
     *
     * @return javafx label
     */
    public NodeLabel getLabel() {
        return nodeLabel;
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
            rectangle.setFill(Color.LAVENDER);
            this.mainController.showSelectedNodeDetails(this);
        } else {
            rectangle.setFill(DEFAULT_COLOR);
        }
    }

    /**
     * Rectangle that represents node
     *
     * @return Rectangle for representing a Node
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * @param expanded The new expanded value
     */
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * @return The node id
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * @return The vertical box for the children
     */
    public VBox getChildrenVBox() {
        return childrenVBox;
    }

    /**
     * @return If this is expanded
     */
    public Boolean isExpanded() {
        return expanded;
    }

    /**
     * @return the container pane that holds this node and its children (a subtree containing pane)
     */
    public Pane getContainerPane() {
        return containerPane;
    }

    /**
     * @return default color of node
     */
    public Color getDEFAULT_COLOR() {
        return DEFAULT_COLOR;
    }
}
