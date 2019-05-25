package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.*;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.CenterPaneDraggedAction;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.CenterPanePressedAction;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.CenterPaneReleasedAction;
import com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions.ScrollZoomAction;
import com.mycompany.orientdbvisualizationtool.controller.CollapseButtonsAction.CollapseButtonActionBuilder;
import com.mycompany.orientdbvisualizationtool.controller.ShowHideNodeAction.HideNodesButtonAction;
import com.mycompany.orientdbvisualizationtool.controller.ShowHideNodeAction.ShowNodesButtonAction;
import com.mycompany.orientdbvisualizationtool.controller.TableViewAction.TableViewItemClickedAction;
import com.mycompany.orientdbvisualizationtool.controller.ThemeChoiceAction.ThemeChoiceBoxAction;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

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
    @FXML
    private AnchorPane Center_Anchor_Pane;
    @FXML
    private TextField Location_Search;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Double mouseSourceX = 0.;
    private Double mouseSourceY = 0.;
    private Rectangle selectionArea;
    private PlaceManager placeManager;
    private static final int WIDTH = MainView.getWIDTH();

    /**
     * The main default properties of controller are initialized
     */
    @FXML public void initialize() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        Node_Name_Text_Field.setDisable(true);
        Node_Name_Text_Field.setStyle("-fx-opacity: 1;");
        Node_ID_Text_Field.setDisable(true);
        Node_ID_Text_Field.setStyle("-fx-opacity: 1;");
        Node_Type_Text_Field.setDisable(true);
        Node_Type_Text_Field.setStyle("-fx-opacity: 1;");

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

        Location_Search.textProperty().addListener((observable, oldValue, newValue) -> {

            populateTreeView(newValue);
        });
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
                .setIsLeft(true)
                .createCollapseButtonAction();

        new CollapseButtonActionBuilder()
                .setCollapse_Button(Right_Collapse_Button)
                .setSplit_Pane(Center_Split_Pane)
                .setCollapse_Anchor_Pane(right_Anchor_Pane)
                .setCenter_Anchor_Pane(Center_Anchor_Pane)
                .setIsLeft(false)
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
        Table_View.setOnMouseClicked(new TableViewItemClickedAction(Table_View));
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
        Node rootNode = new Node(id, name, type, displayName, this);
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
        Right_Status_Label.setText(nodePlace.getChildren().size() + " | children  ");

        tableViewObserveData.clear();
        PlaceManager placeManager = PlaceManager.getInstance();
        Place place = placeManager.getPlace(node.getNodeId());
        place.loadEntities();
        ArrayList<Entity> placeEntities = place.getEntities();
        tableViewObserveData.addAll(placeEntities);
    }

    /**
     * @return list of nodes
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * @return list of edges
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * recursively populates tree view items with data from model. An auxiliary
     * function for populateTreeView();
     *
     * @param sourcePlace source Place to create a parent treeView item
     * @param sourceItem  a source treeView-item to which children treeView-items are added
     * @param searchKey the string to search for
     * @return source tree item populated with children tree items
     */
    private TreeItem recursePopulateTreeView(Place sourcePlace, TreeItem sourceItem, String searchKey) {
        if (sourcePlace.getChildren().isEmpty()) {
            if(sourcePlace.toString().toLowerCase().contains(searchKey.toLowerCase())){
                return sourceItem;
            }else{
                return null;
            }
        }

        for (Place place : sourcePlace.getChildren()) {
            TreeItem childItem = new TreeItem<>(place.toString());
            childItem.setExpanded(true);
            TreeItem children = recursePopulateTreeView(place, childItem, searchKey);

            if(children != null || place.toString().toLowerCase().contains(searchKey.toLowerCase())){
                sourceItem.getChildren().add(children);
                childItem.setGraphic(iconize(place.getType()));
            }
        }
        if(sourcePlace.toString().toLowerCase().contains(searchKey.toLowerCase()) || !sourceItem.getChildren().isEmpty()){
            return sourceItem;
        }
        return null;
    }

    /**
     * populates the tree view with data from model
     * @param searchKey the string to search for
     */
    public void populateTreeView(String searchKey) {
        Place rootPlace = placeManager.getRoot();
        String itemName = rootPlace.getType().toString() + ": " + rootPlace.toString();
        TreeItem rootItem = new TreeItem<>(itemName);
        rootItem.setExpanded(true);
        rootItem.setGraphic(new ImageView("icons/location-icon.png"));
        Left_Tree_View.setRoot(recursePopulateTreeView(rootPlace, rootItem, searchKey));
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

    /**
     * @return mouseSourceX on mouse pressed
     */
    public Double getMouseSourceX() {
        return mouseSourceX;
    }

    /**
     * @return mouseSourceY on mouse pressed
     */
    public Double getMouseSourceY() {
        return mouseSourceY;
    }

    /**
     * @param mouseSourceX on mouse pressed
     */
    public void setMouseSourceX(Double mouseSourceX) {
        this.mouseSourceX = mouseSourceX;
    }

    /**
     * @param mouseSourceY on mouse pressed
     */
    public void setMouseSourceY(Double mouseSourceY) {
        this.mouseSourceY = mouseSourceY;
    }

    public void openPreferences(ActionEvent actionEvent) {
        VisApplication.getInstance().changeToPreferences();
    }
}