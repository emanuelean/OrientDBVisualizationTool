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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import javafx.stage.Modality;

/**
 * FXML Controller class for the tree view
 *
 * @author Yona
 */
public class MainController {

    @FXML
    private SplitPane Center_Split_Pane;
    @FXML
    private Button Left_Collapse_Button;
    @FXML
    private Button Right_Collapse_Button;
    @FXML
    private TextField Node_Name_Text_Field;
    @FXML
    private TextField Node_ID_Text_Field;
    @FXML
    private TextField Node_Type_Text_Field;
    @FXML
    private TreeView Left_Tree_View;
    @FXML
    private TableColumn Table_View_Entity_ID;
    @FXML
    private TableView Table_View;
    @FXML
    private Button Hide_Button;
    @FXML
    private Button Show_All_Button;
    @FXML
    private Label Left_Status_Label;
    @FXML
    private Label Right_Status_Label;
    @FXML
    private ChoiceBox Theme_Choice_Box;

    private javafx.scene.Node Right_Anchor_Pane;
    private javafx.scene.Node Left_Anchor_Pane;

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
        Center_Anchor_Pane.layout();

        Center_Anchor_Pane.setPrefWidth(WIDTH * .60);
        Center_Anchor_Pane.setPrefHeight(WIDTH * 9 / 16);
        Center_Anchor_Pane.setId("Center_Anchor_Pane");

        Left_Anchor_Pane = Center_Split_Pane.getItems().get(0);
        Right_Anchor_Pane = Center_Split_Pane.getItems().get(2);

        setPanelCollapseButtonProperty();
        setTableViewCellsProperty();
        setHideActionProperty();
        setThemeChoiceBoxProperty();
        zoomFunction();
    }

    /**
     * Sets up the properties of panel collapse or panel contract button
     */
    private void setPanelCollapseButtonProperty() {
        Left_Collapse_Button.setOnAction(event -> {
            if (Center_Split_Pane.getItems().contains(Left_Anchor_Pane)) {
                Center_Split_Pane.getItems().remove(Left_Anchor_Pane);
                if (Center_Split_Pane.getItems().size() == 2) {
                    Center_Split_Pane.setDividerPosition(0, 0.8);
                    Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                } else if (Center_Split_Pane.getItems().size() == 1) {
                    Center_Anchor_Pane.setPrefWidth(WIDTH);
                }
                Left_Collapse_Button.setText("▶");
            } else {
                Center_Split_Pane.getItems().add(0, Left_Anchor_Pane);
                if (Center_Split_Pane.getItems().size() == 2) {
                    Center_Split_Pane.setDividerPosition(0, 0.2);
                    Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                } else if (Center_Split_Pane.getItems().size() == 3) {
                    Center_Split_Pane.setDividerPosition(0, 0.2);
                    Center_Split_Pane.setDividerPosition(1, 0.8);
                    Center_Anchor_Pane.setPrefWidth(WIDTH * .60);
                }
                Left_Collapse_Button.setText("◀");

            }
        });
        Right_Collapse_Button.setOnAction(event -> {
            if (Center_Split_Pane.getItems().contains(Right_Anchor_Pane)) {
                Center_Split_Pane.getItems().remove(Right_Anchor_Pane);
                if (Center_Split_Pane.getItems().size() == 2) {
                    Center_Split_Pane.setDividerPosition(0, 0.2);
                    Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                } else if (Center_Split_Pane.getItems().size() == 1) {
                    Center_Anchor_Pane.setPrefWidth(WIDTH);
                }
                Right_Collapse_Button.setText("◀");

            } else {
                Center_Split_Pane.getItems().add(Center_Split_Pane.getItems().size(), Right_Anchor_Pane);
                if (Center_Split_Pane.getItems().size() == 2) {
                    Center_Split_Pane.setDividerPosition(0, 0.8);
                    Center_Anchor_Pane.setPrefWidth(WIDTH * .80);
                } else if (Center_Split_Pane.getItems().size() == 3) {
                    Center_Split_Pane.setDividerPosition(0, 0.2);
                    Center_Split_Pane.setDividerPosition(1, 0.8);
                    Center_Anchor_Pane.setPrefWidth(WIDTH * .60);
                }
                Right_Collapse_Button.setText("▶");
            }
        });
    }

    /**
     * Setting the check box action property to enable/disable Dark Mode theme
     */
    private void setThemeChoiceBoxProperty() {
        File folder = new File(getClass().getResource("/styles").getPath());
        Theme_Choice_Box.getItems().addAll("Default Theme", "Dark Mode", "Natural Blue", "S.B. Green", "Crimson Red");
        Theme_Choice_Box.getSelectionModel().selectFirst();
        Theme_Choice_Box.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Center_Anchor_Pane.getScene().getStylesheets().remove("styles/" + Objects.requireNonNull(folder.listFiles())[oldValue.intValue()].getName());
                    Center_Anchor_Pane.getScene().getStylesheets().add("styles/" + Objects.requireNonNull(folder.listFiles())[newValue.intValue()].getName());
                }
        );
    }

    /**
     * Table view listens to changes with respect to the properties that a Node
     * has. The table view uses the id from Entity class.
     */
    private void setTableViewCellsProperty() {
        Table_View_Entity_ID.setPrefWidth(240);
        Table_View_Entity_ID.setCellValueFactory(new PropertyValueFactory<Entity, String>("id"));
        Table_View.setItems(tableViewObserveData);
    }

    /**
     * The Hide_Button hides nodes on button pressed and its corresponding
     * edges. The Show_All_Button shows all hidden nodes.
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
     * Zoom functionality When scrolling up and down, the pane that contains the
     * tree is zooming in and out on the current mouse point coordinates.
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
     * clicking and in addition used for recording the start point of a mouse
     * position for function centerPaneDragged();
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

        String id = rootPlace.getId();
        String name = rootPlace.getName();
        String type = rootPlace.getType().toString();
        String displayName = rootPlace.toString();
        Node rootNode = new Node(id, name, type, displayName);
        rootNode.setController(this);
        nodes.add(rootNode);

        VBox rootVBox = new VBox(15);
        rootVBox.getChildren().add(rootNode.getContainerPane());
        rootVBox.layout();
        //rootVBox.setBorder(new Border(new BorderStroke(Color.RED,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        rootVBox.setLayoutX(100);
        rootVBox.setLayoutY(WIDTH * 9 / (16 * 2));
        Center_Anchor_Pane.getChildren().add(rootVBox);
        Center_Anchor_Pane.layout();
    }

    private final ObservableList<Entity> tableViewObserveData = FXCollections.observableArrayList();

    /**
     * Sets the fields of a node/place object to be shown on right panel.
     *
     * @param node has properties to be shown.
     */
    public void showSelectedNodeDetails(Node node) {
        Place nodePlace = placeManager.getPlace(node.getNodeId());
        Node_Name_Text_Field.setText(nodePlace.toString());
        Node_ID_Text_Field.setText(nodePlace.getId());
        Node_Type_Text_Field.setText(nodePlace.getType().toString());

        Left_Status_Label.setTooltip(new Tooltip("Path to the currently selected place"));
        Left_Status_Label.setGraphic(iconize(nodePlace.getType()));
        Left_Status_Label.setText("/" + nodePlace.getPath());
        Right_Status_Label.setText("Children | " + nodePlace.getChildren().size() + "   ");

        tableViewObserveData.clear();
        PlaceManager placeManager = PlaceManager.getInstance();
        Place place = placeManager.getPlace(node.getNodeId());
        place.loadEntities();
        ArrayList<Entity> placeEntities = place.getEntities();
        tableViewObserveData.addAll(placeEntities);
    }

    /**
     * expands a node to add/show the children of the node, if node is not
     * expanded and contracts a node to remove/hide the children of the node
     * otherwise.
     *
     * @param parentNode source node for expansion
     */
    public void expandContractNode(Node parentNode) {
        if (!parentNode.isExpanded()) {
            expandNode(parentNode);
            parentNode.setExpanded(true);
            clearEdgeLinks();
        } else {
            removeNodeAndChildren(parentNode);
            parentNode.setExpanded(false);
        }
    }

    /**
     * expands a nodes to show the children of the node
     *
     * @param parentNode source node for expansion
     */
    private void expandNode(Node parentNode) {
        VBox childrenVBox = parentNode.getChildrenVBox();
        Place sourcePlace = placeManager.getPlace(parentNode.getNodeId());
        ArrayList<Place> childrenPlaces = sourcePlace.getChildren();

        if (!childrenPlaces.isEmpty()) {
            for (Place place : childrenPlaces) {
                String id = place.getId();
                String name = place.getName();
                String type = place.getType().toString();
                String displayName = place.toString();
                Node childNode = new Node(id, name, type, displayName);
                childNode.setController(this);
                nodes.add(childNode);
                childrenVBox.getChildren().add(childNode.getContainerPane());
                childrenVBox.layout();
            }
        }
        parentNode.setLayoutY((childrenVBox.getBoundsInLocal().getHeight() + parentNode.getHeight()) / 2);
        redrawEdges(parentNode);
    }

    /**
     * clears and redraws the edge present in the central pane. used when nodes
     * are moved around (when a node is expanded).
     */
    private void clearEdgeLinks() {
        edges.clear();
        //keep whole tree to maintain its position when it is expanded (within scroll pane bounds)
        Node rootNode = nodes.get(0);
        VBox rootVBox = (VBox) rootNode.getContainerPane().getParent();
        Bounds rootNodeChildrenVBoxBounds = rootNode.getChildrenVBox().getBoundsInLocal();
        rootVBox.setLayoutY(Math.abs(rootVBox.getLayoutY() - ((rootNodeChildrenVBoxBounds.getHeight()) - rootNode.getHeight()) / 2));

        for (int i = 0; i < nodes.size(); i++) {
            Pane containerPane = nodes.get(i).getContainerPane();
            //reposition node to the center of its children
            ObservableList<javafx.scene.Node> genericNodes = nodes.get(i).getChildrenVBox().getChildren();
            if (!genericNodes.isEmpty()) {
                nodes.get(i).setLayoutY(((nodes.get(i).getChildrenVBox().getBoundsInLocal().getHeight()) - rootNode.getHeight()) / 2);
            }
            for (int j = 0; j < containerPane.getChildren().size(); j++) {
                if (containerPane.getChildren().get(j) instanceof Edge) {
                    containerPane.getChildren().remove(j);
                    j--;
                }
            }
        }
        redrawEdges(rootNode);
    }

    /**
     * recursively redraws the edges then nodes are moved around, (when a node
     * is expanded).
     *
     * @param startNode: starting node of the subtree that needs to redraw
     * edges.
     */
    private void redrawEdges(Node startNode) {
        if (startNode.getChildrenVBox().getChildren().isEmpty()) {
            return;
        }
        for (javafx.scene.Node childGenericNode : startNode.getChildrenVBox().getChildren()) {
            Pane containerPane = (Pane) childGenericNode;
            Node childNode = (Node) containerPane.getChildren().get(0);
            Edge edge = new Edge(startNode, childNode);
            startNode.getContainerPane().getChildren().add(edge);
            startNode.getContainerPane().layout();
            edges.add(edge);
            redrawEdges(childNode);
        }
    }

    /**
     * recursively removes the children in the subtree of given node. Used when
     * the user contracts a node
     *
     * @param node: the node to be contracted
     */
    private void removeNodeAndChildren(Node node) {
        Pane container = node.getContainerPane();

        if (container.getChildren().size() < 2) {
            return;
        }

        Iterator<javafx.scene.Node> iterator = container.getChildren().iterator();
        while (iterator.hasNext()) {
            javafx.scene.Node nextNode = iterator.next();
            if (nextNode instanceof Edge) {
                edges.remove(nextNode);
                iterator.remove();
            }
        }

        for (javafx.scene.Node genericNode : node.getChildrenVBox().getChildren()) {
            Pane containerPane = (Pane) genericNode;
            Node childNode = (Node) containerPane.getChildren().get(0);
            nodes.remove(childNode);
            removeNodeAndChildren(childNode);
        }

        node.getChildrenVBox().getChildren().clear();
    }

    /**
     * recursively populates tree view items with data from model. An auxiliary
     * function for populateTreeView();
     *
     * @param sourcePlace source Place to create a parent treeView item
     * @param sourceItem a source treeView-item to which children treeView-items
     * are added
     * @return source tree item populated with children tree items
     */
    private TreeItem recursePopulateTreeView(Place sourcePlace, TreeItem sourceItem) {
        if (sourcePlace.getChildren().isEmpty()) {
            return sourceItem;
        }

        for (Place place : sourcePlace.getChildren()) {
            TreeItem childItem = new TreeItem<>(place.toString());
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
        String itemName = rootPlace.getType().toString() + ": " + rootPlace.toString();
        TreeItem rootItem = new TreeItem<>(itemName);
        rootItem.setExpanded(true);
        rootItem.setGraphic(new ImageView("icons/location-icon.png"));
        Left_Tree_View.setRoot(recursePopulateTreeView(rootPlace, rootItem));

        /*
        TODO:: TO BE USED IN UPCOMING ITERATIONS
        int row = Left_Tree_View.getRow(rootItem);
        Left_Tree_View.getSelectionModel().select(3);
        Left_Tree_View.get
         */
    }

    /**
     * Switches from the main view to the main menu
     */
    public void switchToMainMenu() {
        Center_Anchor_Pane.getChildren().clear();
        VisApplication.getInstance().changeToMenu();
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
