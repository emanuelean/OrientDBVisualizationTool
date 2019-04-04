package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.Edge;
import com.mycompany.orientdbvisualizationtool.View.MainView;
import com.mycompany.orientdbvisualizationtool.View.Node;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainController {

    @FXML
    public TextField Node_Name_Text_Field;
    @FXML
    public TextField Node_ID_Text_Field;
    @FXML
    public TextField Node_Type_Text_Field;
    @FXML
    public TreeView Left_Tree_View;
    @FXML
    public CheckBox Hide_Check_Box;

    private PlaceManager placeManager;

    /**
     * The main default properties of controller are initialized
     */
    @FXML
    public void initialize() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        Node_Name_Text_Field.setDisable(true);
        Node_ID_Text_Field.setDisable(true);
        Node_Type_Text_Field.setDisable(true);

        selectionArea = new Rectangle();
        selectionArea.setFill(Color.rgb(0, 70, 255, 0.1));
        selectionArea.setStroke(Color.LIGHTBLUE);
        Center_Anchor_Pane.getChildren().add(selectionArea);
        Center_Anchor_Pane.setPrefWidth(WIDTH * .60 + 300);
        Center_Anchor_Pane.setPrefHeight(WIDTH * 9 / 16);

        setHideCheckBoxActionProperty();
    }

    /**
     * The Hide_Check_Box hides/shows nodes and its corresponding edges.
     * The property of Hide_Check_Box is defined here.
     */
    private void setHideCheckBoxActionProperty() {
        Hide_Check_Box.setOnAction(event -> {
                    if (Hide_Check_Box.isSelected()) {
                        for (Node node : nodes) {
                            if (node.isSelected()) {
                                node.getRectangleAndLabel().setVisible(false);
                                for (Edge edge : edges) {
                                    if (edge.getFirstNode() == node || edge.getSecondNode() == node) {
                                        edge.setVisible(false);
                                    }
                                }
                            }
                        }
                    } else {
                        for (Node node : nodes) {
                            node.getRectangleAndLabel().setVisible(true);
                        }
                        for (Edge edge : edges) {
                            edge.setVisible(true);
                        }
                    }
                }
        );
    }


    /**
     * Center anchor pane controls
     **/
    @FXML
    public AnchorPane Center_Anchor_Pane;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private double mouseSourceX = 0;
    private double mouseSourceY = 0;
    private Rectangle selectionArea;
    private static final int WIDTH = MainView.WIDTH;

    /**
     * Action taken in the anchor pane in the center. Defines how a selection
     * area rectangle is drawn to select several nodes.
     *
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
     * Used for deselection when clicking and
     * records start point of a mouse position for function centerPaneDragged();
     *
     * @param mouseEvent Mouse event for mouse pressed
     */
    public void centerPanePressed(MouseEvent mouseEvent) {
        mouseSourceX = mouseEvent.getX();
        mouseSourceY = mouseEvent.getY();

        for (Node node : nodes) {
            if (!node.getRectangleAndLabel().isPressed()) {
                node.setSelected(false);
            }
        }
    }

    /**
     * Action taken in the anchor pane in the center.
     *
     * @param mouseEvent Mouse event for mouse drag released
     */
    public void centerPaneReleased(MouseEvent mouseEvent) {
        //area selection
        if (mouseEvent.isControlDown()) {
            if (!nodes.isEmpty()) {
                for (Node node : nodes) {
                    StackPane pane = node.getRectangleAndLabel();
                    Bounds selectionAreaBounds = selectionArea.localToScene(selectionArea.getBoundsInLocal());
                    Bounds nodeBounds = pane.localToScene(pane.getBoundsInLocal());
                    if (selectionAreaBounds.intersects(nodeBounds)) {
                        node.setSelected(true);
                    } else {
                        node.setSelected(false);
                    }
                }
                Center_Anchor_Pane.getScene().setCursor(Cursor.DEFAULT);
            }
        }
        selectionArea.setVisible(false);
    }

    /**
     * sets the place manager interface to handle model
     *
     * @param placeManager handles controller to model interactions
     */
    public void setPlaceManager(PlaceManager placeManager) {
        this.placeManager = placeManager;
    }

    /**
     * The source node is added into graph.
     */
    public void addRootNodeToPane() {
        Place rootPlace = placeManager.getRoot();
        VBox rootVBox = new VBox();
        rootVBox.setSpacing(14);
        Node rootNode = new Node(rootPlace.getId(), rootPlace.getName(), rootPlace.getClass().getSimpleName());
        rootNode.setController(this);
        rootNode.addToVBox(rootVBox);
        nodes.add(rootNode);
        rootVBox.setTranslateX(100);
        rootVBox.setTranslateY(WIDTH / 4);
        Center_Anchor_Pane.getChildren().add(rootVBox);
    }

    /**
     * Sets the fields of a node/place object to be shown on right panel.
     *
     * @param node has properties to be shown.
     */
    public void showSelectedNodeDetails(Node node) {
        Node_Name_Text_Field.setText(node.getNodeName());
        Node_ID_Text_Field.setText(node.getNodeId());
        Node_Type_Text_Field.setText(node.getType());
    }

    /**
     * expands a nodes to show the children of the node
     *
     * @param node source node for expansion
     */
    public void expandNode(Node node) {
        Bounds nodeBounds = Center_Anchor_Pane.sceneToLocal(node.localToScene(node.getBoundsInLocal()));
        StackPane nodeWithLabel = node.getRectangleAndLabel();
        Bounds parentBounds = node.getParent().getBoundsInLocal();

        double horizontalOffset = (parentBounds.getWidth() - nodeWithLabel.getWidth()) / 2;
        double sourceNodeX = nodeBounds.getMaxX() - horizontalOffset;
        double sourceNodeY = nodeBounds.getMaxY() - nodeWithLabel.getHeight() / 2;

        Place sourcePlace = placeManager.getPlace(node.getNodeId());
        ArrayList<Place> childrenPlaces = sourcePlace.getChildren();
        int totalPlaces = childrenPlaces.size();

        if (!childrenPlaces.isEmpty()) {
            int vBoxSpacing = 14;
            VBox vbox = new VBox(vBoxSpacing);

            for (Place place : childrenPlaces) {
                Node childNode = new Node(place.getId(), place.getName(), place.getClass().getSimpleName());
                childNode.setController(this);
                childNode.addToVBox(vbox);
                nodes.add(childNode);
            }

            Center_Anchor_Pane.getChildren().add(vbox);

            double vBoxHeight = (totalPlaces * node.getHeight()) + ((totalPlaces - 1) * vBoxSpacing);
            double offsetFromSource = (parentBounds.getWidth() + vbox.getBoundsInLocal().getWidth()) / 2;

            vbox.setTranslateX(sourceNodeX + offsetFromSource);
            vbox.setTranslateY(sourceNodeY - vBoxHeight / 2);

            for (int i = nodes.size() - 1; i > nodes.size() - 1 - totalPlaces; i--) {
                Node childNode = nodes.get(i);
                Edge edge = new Edge(node, childNode, Center_Anchor_Pane);
                edges.add(edge);
                Center_Anchor_Pane.getChildren().add(edge);
            }
        }
    }

    /**
     * recursively populates tree view items with data from model.
     * An auxiliary function for populateTreeView();
     *
     * @param sourcePlace source Place to create a parent treeView item
     * @param sourceItem  a source treeView-item to which children treeView-items are added
     * @return source tree item populated with children tree items
     */
    private TreeItem recursePopulateTreeView(Place sourcePlace, TreeItem sourceItem) {
        if (sourcePlace.getChildren().isEmpty()) {
            return sourceItem;
        }
        for (Place place : sourcePlace.getChildren()) {
            TreeItem childItem = new TreeItem<>(place.getClass().getSimpleName() + ": " + place.getId() + ": " + place.getName());
            sourceItem.getChildren().add(recursePopulateTreeView(place, childItem));
        }
        return sourceItem;
    }

    /**
     * populates the tree view with data from model
     */
    public void populateTreeView() {
        Place rootPlace = placeManager.getRoot();
        String itemName = rootPlace.getClass().getSimpleName() + ": " + rootPlace.getId() + ": " + rootPlace.getName();
        TreeItem rootItem = new TreeItem<>(itemName);
        Left_Tree_View.setRoot(recursePopulateTreeView(rootPlace, rootItem));
    }

    /**
     * Quits program.
     *
     * @param actionEvent Action event for exiting the program
     */
    public void quitProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) Center_Anchor_Pane.getScene().getWindow();
        stage.close();
    }
}
