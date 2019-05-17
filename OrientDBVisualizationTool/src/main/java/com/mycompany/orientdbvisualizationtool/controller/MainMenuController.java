package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.VisApplication;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.mycompany.orientdbvisualizationtool.model.Property;
import com.mycompany.orientdbvisualizationtool.model.managers.OrganizationManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class for the main menu
 *
 * @author Niels, Albert
 */
public class MainMenuController extends ParentController {

    @FXML
    private TextField Node_Name_Text_Field;
    @FXML
    private TextField Node_ID_Text_Field;
    @FXML
    private TextField Node_Type_Text_Field;
    @FXML
    private TreeView Organization_Tree_View;
    @FXML
    private TreeView Location_Tree_View;
    @FXML
    private TableColumn Table_View_PropertyKey;
    @FXML
    private TableColumn Table_View_PropertyValue;
    @FXML
    private TableView Properties_Table;

    private final ObservableList<Property> propertiesTable = FXCollections.observableArrayList();
    private OrganizationManager organizationManager;
    private Place currentPlace;

    /**
     * The main default properties of controller are initialized
     */
    @FXML
    public void initialize() {

        Node_Name_Text_Field.setDisable(true);
        Node_Name_Text_Field.setStyle("-fx-opacity: 1;");
        Node_ID_Text_Field.setDisable(true);
        Node_ID_Text_Field.setStyle("-fx-opacity: 1;");
        Node_Type_Text_Field.setDisable(true);
        Node_Type_Text_Field.setStyle("-fx-opacity: 1;");
        Properties_Table.setSelectionModel(null);
        organizationManager = OrganizationManager.getInstance();
        populateOrganizationTreeView();

        Properties_Table.setPrefWidth(240);
        Properties_Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Table_View_PropertyKey.setCellValueFactory(new PropertyValueFactory<Property, String>("key"));
        Table_View_PropertyValue.setCellValueFactory(new PropertyValueFactory<Property, String>("value"));
        Properties_Table.setItems(propertiesTable);
    }

    /**
     * Sets the fields of a node/place object to be shown on right panel.
     *
     * @param nodePlace Has properties to be shown.
     */
    public void showSelectedPlaceDetails(Place nodePlace) {
        Node_Name_Text_Field.setText(nodePlace.toString());
        Node_ID_Text_Field.setText(nodePlace.toString());
        Node_Type_Text_Field.setText(nodePlace.getType().toString());
        propertiesTable.clear();
        nodePlace.loadAttributes();
        propertiesTable.addAll(nodePlace.getAttributes().getProperties());
    }


    /**
     * populates the tree view with data from model
     */
    public void populateOrganizationTreeView() {
        TreeItem<String> rootItem = new TreeItem<>();
        List<Organization> organizations = organizationManager.getOrganizations();
        for (Organization o : organizations) {
            String itemName = o.getId();
            TreeItem<String> newItem = new TreeItem<>(itemName);
            newItem.setExpanded(true);
            newItem.setGraphic(new ImageView("icons/organization-icon.png"));
            rootItem.getChildren().add(newItem);
        }

        Organization_Tree_View.setRoot(rootItem);
        Organization_Tree_View.setShowRoot(false);

        Organization_Tree_View.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    populateLocationTreeView(((TreeItem<String>) newValue).getValue());
                });
    }

    /**
     * populates the tree view with data from model
     *
     * @param id The id of the organization we want the locations from
     */
    public void populateLocationTreeView(String id) {
        TreeItem rootItem = Location_Tree_View.getRoot();
        if (rootItem != null) {
            rootItem.getChildren().clear();
        } else {
            rootItem = new TreeItem<>();
        }
        Organization currentOrganization = organizationManager.getOrganization(id);

        List<Place> places = currentOrganization.getPlaces();
        for (Place p : places) {
            TreeItem newItem = new TreeItem<>(p);
            newItem.setExpanded(true);
            newItem.setGraphic(iconize(p.getType()));
            rootItem.getChildren().add(newItem);
        }

        Location_Tree_View.setRoot(rootItem);
        Location_Tree_View.setShowRoot(false);

        Location_Tree_View.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    currentPlace = ((TreeItem<Place>) newValue).getValue();
                    showSelectedPlaceDetails(currentPlace);
                });
    }

    /**
     * Quits program.
     *
     * @param actionEvent Action event for exiting the program
     */
    public void quitProgram(ActionEvent actionEvent) {
    }

    /**
     * Selects a place and changes the application to the tree view of this
     */
    public void selectPlace() {
        if (currentPlace == null) {
            return;
        }
        DatabaseManager db = DatabaseManager.getInstance();
        db.getPlaceData().refresh(currentPlace.getId());
        VisApplication.getInstance().changeToMain();
    }
}
