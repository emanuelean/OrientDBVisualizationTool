package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
import javafx.scene.control.Tooltip;
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
public class Node extends StackPane {

    private Text label;
    private String nodeName;
    private String nodeDisplayName;
    private String nodeId;
    private String nodeType;
    private boolean selected;
    private Rectangle rectangle;
    private Color DEFAULT_COLOR;
    private Color DEFAULT_SELECTED_COLOR;
    private MainController mainController;
    private Boolean expanded;
    private VBox childrenVBox;

    /**
     * Constructor
     *
     * @param id The id of the node
     * @param nodeName The name of the node
     * @param NodeType The type of the node
     * @param displayName The display name of the node
     */
    public Node(String id, String nodeName, String NodeType, String displayName) {
        this.DEFAULT_COLOR = Color.LIGHTGRAY;
        this.DEFAULT_SELECTED_COLOR = Color.LAVENDER;
        this.nodeId = id;
        this.nodeName = nodeName;
        this.nodeDisplayName = displayName;
        this.nodeType = NodeType;
        this.childrenVBox = new VBox();

        this.label = new Text(displayName);
        this.label.setFont(new Font(13));
        this.label.setId("NodeLabel");
        this.selected = false;

        this.setRectangleProperty();
        this.getChildren().addAll(rectangle, this.getLabel());
        this.expanded = false;
        Tooltip.install(this, new Tooltip("A " + NodeType + " entity\nDouble click here to expand or contract"));
        this.setMouseListenerProperties();
        this.setId("NodeStackPane");
    }

    /**
     * Sets the properties of a rectangle
     */
    private void setRectangleProperty() {
        //rounded rectangle
        this.rectangle = new Rectangle();
        this.rectangle.setWidth(label.getLayoutBounds().getWidth() + 30);
        this.rectangle.setHeight(40.0f);
        this.rectangle.setArcWidth(40);
        this.rectangle.setArcHeight(40);
        this.rectangle.setFill(DEFAULT_COLOR);
        this.rectangle.setStroke(Color.DARKGREY);
        this.rectangle.setId("NodeRectangle");
    }

    /**
     * Constructor
     *
     * @param id The id of the node
     * @param nodeName The name of the node
     * @param NodeType The type of the node
     */
    public Node(String id, String nodeName, String NodeType) {
        this(id, nodeName, NodeType, nodeName);
        this.DEFAULT_COLOR = Color.LIGHTGRAY;
        this.DEFAULT_SELECTED_COLOR = Color.LAVENDER;
    }

    /**
     * Sets properties for mouse events. Mouse pressed -> selection 2x Mouse
     * click -> expansion of node Mouse entered/exited -> highlight node
     */
    private void setMouseListenerProperties() {
        this.setOnMousePressed(event -> {
            if (!this.isSelected()) {
                this.setSelected(true);
            } else {
                this.setSelected(false);
            }
        }
        );
        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                //Double click
                if (event.getClickCount() == 2) {
                    mainController.expandNode(this);
                }
            }
        }
        );
        this.setOnMouseEntered(event -> {
            if (!selected) {
                rectangle.setFill(DEFAULT_COLOR.deriveColor(0, 1, 0.8, 1));
            } else {
                rectangle.setFill(DEFAULT_COLOR.deriveColor(0, 1, 1 / 0.8, 1));
            }
        }
        );
        this.setOnMouseExited(event -> {
            if (selected) {
                rectangle.setFill(Color.LAVENDER);
            } else {
                rectangle.setFill(DEFAULT_COLOR);
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
     * Adds aa vertical box to a vertical box
     * @param vBox The vertical box that needs to be added
     */
    public void addToVBox(VBox vBox) {
        vBox.getChildren().add(this);
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
     * Assigns the controller instance to node in view
     *
     * @param mainController the main controller
     */
    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * 
     * @param expanded The new expanded value
     */
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * 
     * @param vbox The new vertical box
     */
    public void setChildrenVBox(VBox vbox) {
        this.childrenVBox = vbox;
    }

    /**
     * 
     * @return The node id
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * 
     * @return The node name
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * 
     * @return The display name
     */
    public String getDisplayName() {
        return nodeDisplayName;
    }

    /**
     * 
     * @return The node type
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 
     * @return The vertical box for the children
     */
    public VBox getChildrenVBox() {
        return childrenVBox;
    }

    /**
     * 
     * @return If this is expanded
     */
    public Boolean isExpanded() {
        return expanded;
    }

}
