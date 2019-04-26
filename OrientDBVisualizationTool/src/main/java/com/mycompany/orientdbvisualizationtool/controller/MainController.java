package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.*;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;

import com.mycompany.orientdbvisualizationtool.model.places.PlaceCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
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
    public TableColumn Table_View_Entity_ID;
    @FXML
    public TableView Table_View;
    @FXML
    public Button Hide_Button;
    @FXML
    public Button Show_All_Button;
    @FXML
    public Label Left_Status_Label;
    @FXML
    public Label Right_Status_Label;
    @FXML
    public CheckBox Dark_Mode_Check_Box;

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

        Center_Anchor_Pane.setPrefWidth(WIDTH * .60);
        Center_Anchor_Pane.setPrefHeight(WIDTH * 9 / 16);
        Center_Anchor_Pane.setId("Center_Anchor_Pane");

        setTableViewCellsProperty();
        setHideActionProperty();
        setDarkModeCheckBoxProperty();
        zoomFunction();
    }

    private void setDarkModeCheckBoxProperty() {
        Dark_Mode_Check_Box.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                Center_Anchor_Pane.getScene().getStylesheets().add("styles/DarkModeStyle.css");
            } else {
                Center_Anchor_Pane.getScene().getStylesheets().remove("styles/DarkModeStyle.css");
            }
        });
    }

    /**
     * Table view listens to changes with respect to the properties that a Node has.
     * The table view uses the id from Entity class.
     */
    private void setTableViewCellsProperty() {
        Table_View_Entity_ID.setPrefWidth(240);
        //TODO:: IS THIS STRING USAGE SAFE?
        Table_View_Entity_ID.setCellValueFactory(new PropertyValueFactory<Entity, String>("id"));
        Table_View.setItems(tableViewObserveData);
    }

    /**
     * The Hide_Button hides nodes on button pressed and its corresponding edges.
     * The Show_All_Button shows all hidden nodes.
     */
    private void setHideActionProperty() {
        Hide_Button.setOnAction(event -> {
                    for (Node node : nodes) {
                        if (node.isSelected()) {
                            node.setVisible(false);
                            for (Edge edge : edges) {
                                if (edge.getFirstNode() == node || edge.getSecondNode() == node) {
                                    edge.setVisible(false);
                                }
                            }
                        }
                    }
                }
        );
        Show_All_Button.setOnAction(event -> {
                    for (Node node : nodes) {
                        node.setVisible(true);
                    }
                    for (Edge edge : edges) {
                        edge.setVisible(true);
                    }
                }
        );
    }

    /**
     * Zoom functionality
     * When scrolling up and down, the pane that contains the tree
     * is zooming in and out on the current mouse point coordinates.
     */

    private void zoomFunction() {
        Center_Anchor_Pane.setOnScroll(event -> {
            if (event.isControlDown()) {
                double zoomFactor = 1.05;
                double deltaY = event.getDeltaY();

                if (deltaY < 0) {
                    zoomFactor = 2.0 - zoomFactor;
                }

                Scale scale = new Scale();
                scale.setPivotX(event.getX());
                scale.setPivotY(event.getY());
                scale.setX(Center_Anchor_Pane.getScaleX() * zoomFactor);
                scale.setY(Center_Anchor_Pane.getScaleY() * zoomFactor);

                Center_Anchor_Pane.getTransforms().add(scale);

                event.consume();
            }
        });
    }


    /**
     * Center anchor pane controls
     */
    @FXML
    private AnchorPane Center_Anchor_Pane;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private double mouseSourceX = 0;
    private double mouseSourceY = 0;
    private Rectangle selectionArea;
    private static final int WIDTH = MainView.getWIDTH();

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
            selectionArea.setLayoutX(mouseSourceX);
            selectionArea.setLayoutY(mouseSourceY);

            double selectionAreaWidth = mouseEvent.getX() - mouseSourceX;
            double selectionAreaHeight = mouseEvent.getY() - mouseSourceY;

            if (selectionAreaHeight < 0 && selectionAreaWidth < 0) {
                selectionArea.setLayoutX(mouseEvent.getX());
                selectionArea.setLayoutY(mouseEvent.getY());
                selectionArea.setWidth(-selectionAreaWidth);
                selectionArea.setHeight(-selectionAreaHeight);
            } else if (selectionAreaHeight >= 0 && selectionAreaWidth < 0) {
                selectionArea.setLayoutX(mouseEvent.getX());
                selectionArea.setLayoutY(mouseSourceY);
                selectionArea.setWidth(-selectionAreaWidth);
                selectionArea.setHeight(selectionAreaHeight);
            } else if (selectionAreaHeight < 0 && selectionAreaWidth >= 0) {
                selectionArea.setLayoutX(mouseSourceX);
                selectionArea.setLayoutY(mouseEvent.getY());
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
     * Action taken in the anchor pane in the center. Used for deselection when
     * clicking and in addition used for recording the start point of a mouse position for function
     * centerPaneDragged();
     *
     * @param mouseEvent Mouse event for mouse pressed
     */
    public void centerPanePressed(MouseEvent mouseEvent) {
        mouseSourceX = mouseEvent.getX();
        mouseSourceY = mouseEvent.getY();

        for (Node node : nodes) {
            if (!node.isPressed()) {
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
                    Bounds selectionAreaBounds = selectionArea.localToScene(selectionArea.getBoundsInLocal());
                    Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
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
        String id = rootPlace.getId();
        String name = rootPlace.getName();
        String type = rootPlace.getType().toString();
        String displayName = rootPlace.getDisplayName();
        Node rootNode = new Node(id, name, type, displayName);
        rootNode.setController(this);
        rootNode.addToVBox(rootVBox);
        nodes.add(rootNode);

        rootVBox.setLayoutX(100);
        rootVBox.setLayoutY(WIDTH / 4);
        Center_Anchor_Pane.getChildren().add(rootVBox);

    }

    private final ObservableList<Entity> tableViewObserveData = FXCollections.observableArrayList();

    /**
     * Sets the fields of a node/place object to be shown on right panel.
     *
     * @param node has properties to be shown.
     */
    public void showSelectedNodeDetails(Node node) {
        Place nodePlace = placeManager.getPlace(node.getNodeId());
        Node_Name_Text_Field.setText(nodePlace.getDisplayName());
        Node_ID_Text_Field.setText(nodePlace.getId());
        Node_Type_Text_Field.setText(nodePlace.getType().toString());

        Left_Status_Label.setTooltip(new Tooltip("Path to the currently selected place"));
        Left_Status_Label.setGraphic(iconize(nodePlace.getType()));
        Left_Status_Label.setText("/" + nodePlace.getPath());
        Right_Status_Label.setText("Children | " + nodePlace.getChildren().size() + "   ");

        tableViewObserveData.clear();
        PlaceManager placeManager = PlaceManager.getInstance();
        Place place = placeManager.getPlace(node.getNodeId());
        ArrayList<Entity> placeEntities = place.getEntities();
        tableViewObserveData.addAll(placeEntities);
    }

    /**
     * expands a nodes to show the children of the node
     *
     * @param node source node for expansion
     */

    public void expandNode(Node node) {
        if (!node.isExpanded()) {
            Bounds rectangleBounds = Center_Anchor_Pane.sceneToLocal(node.getRectangle().localToScene(node.getBoundsInLocal()));
            Bounds nodeBounds = node.getBoundsInLocal();

            double horizontalOffset = (nodeBounds.getWidth() - node.getWidth()) / 2;
            double sourceNodeX = rectangleBounds.getMaxX() - horizontalOffset;
            double sourceNodeY = rectangleBounds.getMaxY() - node.getHeight() / 2;

            Place sourcePlace = placeManager.getPlace(node.getNodeId());
            ArrayList<Place> childrenPlaces = sourcePlace.getChildren();
            int totalPlaces = childrenPlaces.size();

            if (!childrenPlaces.isEmpty()) {
                int vBoxSpacing = 14;
                VBox vbox = new VBox(vBoxSpacing);
                //vbox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                for (Place place : childrenPlaces) {
                    String id = place.getId();
                    String name = place.getName();
                    String type = place.getType().toString();
                    String displayName = place.getDisplayName();
                    Node childNode = new Node(id, name, type, displayName);
                    childNode.setController(this);
                    childNode.addToVBox(vbox);
                    nodes.add(childNode);
                }

                Center_Anchor_Pane.getChildren().add(vbox);

                double vBoxHeight = (totalPlaces * node.getHeight()) + ((totalPlaces - 1) * vBoxSpacing);
                double horizontalOffsetFromSource = 100;

                double yShift = sourceNodeY - vBoxHeight / 2;
                vbox.setLayoutX(sourceNodeX + horizontalOffsetFromSource);
                vbox.setLayoutY(yShift);
                addAnchorPaneOffset(sourceNodeX, horizontalOffsetFromSource, yShift, vBoxHeight);
                if (yShift < 0) {
                    repositionGraph(yShift);
                }

                //create and connect edges from node to node's children
                for (int i = nodes.size() - 1; i > nodes.size() - 1 - totalPlaces; i--) {
                    Node childNode = nodes.get(i);
                    Edge edge = new Edge(node, childNode, Center_Anchor_Pane);
                    edges.add(edge);
                    Center_Anchor_Pane.getChildren().add(edge);
                }
                node.setChildrenVBox(vbox);
                node.setExpanded(true);
            }
        } else {
            //contract node
            removeNodeAndChildren(node);
        }
    }

    /**
     * Inserts a rectangle node in Anchor pane/Scroll pane to add spacing
     * between edge of a node that is near the boundary of Anchor pane/Scroll pane
     * @param sourceNodeX x coordinate of source node
     * @param offsetFromSource Horizontal offset from source node
     * @param yShift Vertical offset from source node
     * @param vBoxHeight the height of the container that holds children rectangle nodes
     */
    private void addAnchorPaneOffset(double sourceNodeX, double offsetFromSource, double yShift, double vBoxHeight) {
        Rectangle temp = new Rectangle();
        temp.setLayoutX(sourceNodeX + offsetFromSource + 200);
        temp.setLayoutY(yShift + vBoxHeight + 100);
        Center_Anchor_Pane.getChildren().add(temp);
    }

    /**
     * recursively removes the children in the subtree of given node
     * Used  when the user contracts a node
     *
     * @param node the node to be contracted
     */
    private void removeNodeAndChildren(Node node) {
        if (node.getChildrenVBox().getChildren().isEmpty()) {
            node.setExpanded(false);
            return;
        }

        for (javafx.scene.Node child : node.getChildrenVBox().getChildren()) {
            removeNodeAndChildren((Node) child);
            node.setExpanded(false);
            for (Edge edge : edges) {
                if (edge.getSecondNode().equals(child)) {
                    Center_Anchor_Pane.getChildren().remove(edge);
                }
            }
        }
        Center_Anchor_Pane.getChildren().remove(node.getChildrenVBox());
    }

    /**
     * horizontally repositions graph if the graph goes out of bounds in the y axis
     *
     * @param yValue negative vertical value used to reposition graph
     */
    private void repositionGraph(double yValue) {
        for (javafx.scene.Node node : Center_Anchor_Pane.getChildren()) {
            if (!(node instanceof Label)) {
                node.setLayoutY(node.getLayoutY() - yValue + 30);
            }
        }
    }

    /**
     * recursively populates tree view items with data from model. An auxiliary
     * function for populateTreeView();
     *
     * @param sourcePlace source Place to create a parent treeView item
     * @param sourceItem  a source treeView-item to which children treeView-items
     *                    are added
     * @return source tree item populated with children tree items
     */
    private TreeItem recursePopulateTreeView(Place sourcePlace, TreeItem sourceItem) {
        if (sourcePlace.getChildren().isEmpty()) {
            return sourceItem;
        }

        for (Place place : sourcePlace.getChildren()) {
            TreeItem childItem = new TreeItem<>(place.getDisplayName());
            sourceItem.getChildren().add(recursePopulateTreeView(place, childItem));
            childItem.setGraphic(iconize(place.getType()));
        }
        return sourceItem;
    }

    /**
     * returns the correct icon for a given place type.
     *
     * @param placeType enum for the place type
     * @return image view icon for given place type
     */
    private ImageView iconize(PlaceCategory placeType) {
        ImageView view = new ImageView();
        switch (placeType) {
            case Location:
                view.setImage(new Image("icons/location-icon.png"));
                break;
            case Building:
                view.setImage(new Image("icons/building-icon.png"));
                break;
            case Floor:
                view.setImage(new Image("icons/floor-icon.png"));
                break;
            case Room:
                view.setImage(new Image("icons/room-icon.png"));
                break;
            case Area:
                view.setImage(new Image("icons/area-icon.png"));
                break;
            case Cell:
                view.setImage(new Image("icons/cell-icon.png"));
                break;
        }
        return view;
    }

    /**
     * populates the tree view with data from model
     */
    public void populateTreeView() {
        Place rootPlace = placeManager.getRoot();
        String itemName = rootPlace.getType().toString() + ": " + rootPlace.getDisplayName();
        TreeItem rootItem = new TreeItem<>(itemName);
        rootItem.setExpanded(true);
        rootItem.setGraphic(new ImageView("icons/location-icon.png"));
        Left_Tree_View.setRoot(recursePopulateTreeView(rootPlace, rootItem));

        /*
        TODO:: USE FOR SELECTING SEARCHED NODE
        int row = Left_Tree_View.getRow(rootItem);
        Left_Tree_View.getSelectionModel().select(3);
        Left_Tree_View.get
        */
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
