package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.*;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.CenterPaneDraggedAction;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.CenterPanePressedAction;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.CenterPaneReleasedAction;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.ScrollZoomAction;
import com.mycompany.orientdbvisualizationtool.controller.CollapseButtonsAction.CollapseButtonActionBuilder;
import com.mycompany.orientdbvisualizationtool.controller.ShowHideNodeAction.HideNodesButtonAction;
import com.mycompany.orientdbvisualizationtool.controller.ShowHideNodeAction.ShowNodesButtonAction;
import com.mycompany.orientdbvisualizationtool.controller.ThemeChoiceAction.ThemeChoiceBoxAction;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * FXML Controller class for the tree view
 *
 * @author Yona
 */
public class MainController extends ParentController {

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
    private Button Hide_Nodes_Button;
    @FXML
    private Button Show_All_Nodes_Button;
    @FXML
    private Label Left_Status_Label;
    @FXML
    private Label Right_Status_Label;
    @FXML
    private ChoiceBox Theme_Choice_Box;

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

        setCenterAnchorMouseProperties();
        setCollapseButtons();
        setTableViewCellsProperty();
        setHideActionProperty();
        setThemeChoiceBoxProperty();
        zoomFunction();
    }

    /**
     * Buttons for collapsing left and right panels are set up.
     */
    private void setCollapseButtons() {
        AnchorPane left_Anchor_Pane = (AnchorPane) Center_Split_Pane.getItems().get(0);
        AnchorPane right_Anchor_Pane = (AnchorPane) Center_Split_Pane.getItems().get(2);

        new CollapseButtonActionBuilder()
                .setCollapse_Button(Left_Collapse_Button)
                .setSplit_Pane(Center_Split_Pane)
                .setCollapse_Anchor_Pane(left_Anchor_Pane)
                .setCenter_Anchor_Pane(Center_Anchor_Pane)
                .setLeft(true)
                .createCollapseButtonAction();

        new CollapseButtonActionBuilder()
                .setCollapse_Button(Right_Collapse_Button)
                .setSplit_Pane(Center_Split_Pane)
                .setCollapse_Anchor_Pane(right_Anchor_Pane)
                .setCenter_Anchor_Pane(Center_Anchor_Pane)
                .setLeft(false)
                .createCollapseButtonAction();
    }

    /**
     * Setting the check choice box property to enable different themes
     */
    private void setThemeChoiceBoxProperty() {
        Theme_Choice_Box.getSelectionModel().selectedIndexProperty().addListener(new ThemeChoiceBoxAction(Center_Anchor_Pane, Theme_Choice_Box));
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
     * The Hide_Nodes_Button hides nodes on button pressed and its corresponding
     * edges. The Show_All_Nodes_Button shows all hidden nodes.
     */
    private void setHideActionProperty() {
        Hide_Nodes_Button.setOnAction(new HideNodesButtonAction(nodes, edges));
        Show_All_Nodes_Button.setOnAction(new ShowNodesButtonAction(nodes, edges));
    }

    /**
     * Zoom functionality When scrolling up and down, the pane that contains the
     * tree is zooming in and out on the current mouse point coordinates.
     */
    private void zoomFunction() {
        Center_Anchor_Pane.setOnScroll(new ScrollZoomAction(Center_Anchor_Pane));
    }

    /**
     * Center anchor pane controls
     */
    @FXML
    private AnchorPane Center_Anchor_Pane;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Double mouseSourceX = 0.;
    private Double mouseSourceY = 0.;
    private Rectangle selectionArea;
    private static final int WIDTH = MainView.getWIDTH();

    public Double getMouseSourceX() {
        return mouseSourceX;
    }

    public Double getMouseSourceY() {
        return mouseSourceY;
    }

    public void setMouseSourceX(Double mouseSourceX) {
        this.mouseSourceX = mouseSourceX;
    }

    public void setMouseSourceY(Double mouseSourceY) {
        this.mouseSourceY = mouseSourceY;
    }

    /**
     * Action taken when mouse dragged in center anchor pane
     * Action taken when mouse pressed in center anchor pane
     * Action taken when mouse released in center anchor pane
     */
    private void setCenterAnchorMouseProperties() {
        Center_Anchor_Pane.setOnMouseDragged(new CenterPaneDraggedAction(Center_Anchor_Pane, selectionArea, this));
        Center_Anchor_Pane.setOnMousePressed(new CenterPanePressedAction(this, nodes));
        Center_Anchor_Pane.setOnMouseReleased(new CenterPaneReleasedAction(selectionArea, Center_Anchor_Pane, nodes));
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
     * Sets the fields of a node/place object when the node is selected (to be shown on right panel).
     *
     * @param node has properties to be shown.
     */
    public void showSelectedNodeDetails(Node node) {
        Place nodePlace = placeManager.getPlace(node.getNodeId());

        //setting the text fields on right panel
        Node_Name_Text_Field.setText(nodePlace.toString());
        Node_ID_Text_Field.setText(nodePlace.getId());
        Node_Type_Text_Field.setText(nodePlace.getType().toString());

        //setting the status bar contents
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
     * expands a node to add/show the children of the node, if node is not expanded and
     * contracts a node to remove/hide the children of the node otherwise.
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
     * clears and redraws the edge present in the central pane.
     * used when nodes are moved around (when a node is expanded).
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
     * recursively redraws the edges then nodes are moved around, (when a node is expanded).
     *
     * @param startNode starting node of the subtree that needs to redraw edges.
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
     * @param node the node to be contracted
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
     * @param sourceItem  a source treeView-item to which children treeView-items are added
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
     * populates the tree view with data from model
     */
    public void populateTreeView() {
        Place rootPlace = placeManager.getRoot();
        String itemName = rootPlace.getType().toString() + ": " + rootPlace.toString();
        TreeItem rootItem = new TreeItem<>(itemName);
        rootItem.setExpanded(true);
        rootItem.setGraphic(new ImageView("icons/location-icon.png"));
        Left_Tree_View.setRoot(recursePopulateTreeView(rootPlace, rootItem));

        /* TODO:: TO BE USED IN UPCOMING ITERATIONS
        int row = Left_Tree_View.getRow(rootItem);
        Left_Tree_View.getSelectionModel().select(4);
        Left_Tree_View.get
        */
    }

    public void switchToMainMenu() {
        VisApplication.getInstance().changeToMenu();
        //does not close anything yet, should maybe happen?
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