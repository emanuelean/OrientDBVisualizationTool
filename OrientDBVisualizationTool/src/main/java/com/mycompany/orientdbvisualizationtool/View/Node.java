package com.mycompany.orientdbvisualizationtool.View;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private StringProperty nodeName;
    private StringProperty nodeDisplayName;
    private StringProperty nodeId;
    private StringProperty NodeType;
    private boolean selected;
    private Rectangle rectangle;
    private final Color DEFAULT_COLOR = Color.LIGHTGRAY;
    private MainController mainController;
    private Boolean expanded;
    private VBox childrenVBox;

    public Node(String id, String nodeName, String NodeType, String displayName){
        this.nodeId = new SimpleStringProperty(id);
        this.nodeName = new SimpleStringProperty(nodeName);
        this.nodeDisplayName = new SimpleStringProperty(displayName);
        this.NodeType = new SimpleStringProperty(NodeType);
        this.childrenVBox = new VBox();

        this.label = new Text(displayName);
        this.label.setFont(new Font(13));
        this.selected = false;

        //rounded rectangle
        this.rectangle = new Rectangle();
        this.rectangle.setWidth(label.getLayoutBounds().getWidth() + 30);
        this.rectangle.setHeight(40.0f);
        this.rectangle.setArcWidth(40);
        this.rectangle.setArcHeight(40);
        this.rectangle.setFill(DEFAULT_COLOR);
        this.rectangle.setStroke(Color.DARKGREY);
        this.getChildren().addAll(rectangle, this.getLabel());
        this.expanded = false;
        this.setMouseListenerProperties();
    }

    public Node(String id, String nodeName, String NodeType) {
        this(id, nodeName, NodeType, nodeName);
    }

    /**
     * Sets properties for mouse events.
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
    }

    /**
     * gets label
     *
     * @return javafx label
     */
    public Text getLabel() {
        return label;
    }


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

    public String getNodeId() {
        return nodeId.get();
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
        return nodeName.get();
    }

    public String getDisplayName() {
        return nodeDisplayName.get();
    }

    public StringProperty displayNameProperty() {
        return nodeDisplayName;
    }

    public String getNodeType() {
        return NodeType.get();
    }

    public Boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public void setChildrenVBox(VBox vbox) {
        this.childrenVBox = vbox;
    }

    public VBox getChildrenVBox() {
        return childrenVBox;
    }
}
