package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.VisApplication;
import com.mycompany.orientdbvisualizationtool.controller.ThemeChoiceAction.ThemeChoiceBoxAction;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.mycompany.orientdbvisualizationtool.model.Property;
import com.mycompany.orientdbvisualizationtool.model.managers.OrganizationManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TextField Organization_Search;
    @FXML
    private TextField Location_Search;
    @FXML
    private Button Select_Field;
    @FXML
    private ChoiceBox Theme_Choice_Box;

    private final ObservableList<Property> propertiesTable = FXCollections.observableArrayList();
    private OrganizationManager organizationManager;
    private Place currentPlace;
    private Organization currentOrganization;

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
        Select_Field.setVisible(false);
        organizationManager = OrganizationManager.getInstance();
        initLocationTreeView();
        populateOrganizationTreeView();

        Properties_Table.setPrefWidth(240);
        Properties_Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Table_View_PropertyKey.setCellValueFactory(new PropertyValueFactory<Property, String>("key"));
        Table_View_PropertyValue.setCellValueFactory(new PropertyValueFactory<Property, String>("value"));
        Properties_Table.setItems(propertiesTable);
        setThemeChoiceBoxProperty();
    }

    /**
     * Sets the fields of a node/place object to be shown on right panel.
     *
     * @param nodePlace Has properties to be shown.
     */
    public void showSelectedPlaceDetails(Place nodePlace) {
        Select_Field.setVisible(true);
        Node_Name_Text_Field.setText(nodePlace.getShortName());
        Node_ID_Text_Field.setText(nodePlace.getId());
        Node_Type_Text_Field.setText(nodePlace.getType().toString());
        propertiesTable.clear();
        nodePlace.loadAttributes();
        propertiesTable.addAll(nodePlace.getAttributes().getProperties());
    }

    /**
     * Sets the fields of an organization to be shown on right panel.
     *
     * @param nodeOrganization Has properties to be shown.
     */
    public void showSelectedPlaceDetails(Organization nodeOrganization) {
        Select_Field.setVisible(false);
        Node_Name_Text_Field.setText(nodeOrganization.getName());
        Node_ID_Text_Field.setText(nodeOrganization.getId());
        Node_Type_Text_Field.setText("Organization");
        propertiesTable.clear();
        nodeOrganization.loadAttributes();
        propertiesTable.addAll(nodeOrganization.getAttributes().getProperties());
    }

    /**
     * Setting the check choice box property to enable different themes
     */
    private void setThemeChoiceBoxProperty() {
        Theme_Choice_Box.getSelectionModel().selectedIndexProperty().addListener(new ThemeChoiceBoxAction(null, Theme_Choice_Box));
    }

    /**
     * populates the tree view with data from model
     */
    public void populateOrganizationTreeView() {
        Organization_Tree_View.setRoot(new TreeItem<>());
        searchOrganization("");

        Organization_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            searchOrganization(newValue);
        });

        Organization_Tree_View.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    populateLocationTreeView(((TreeItem<String>) newValue).getValue());
                    showSelectedPlaceDetails(currentOrganization);
                });

        Organization_Tree_View.setShowRoot(false);
    }

    /**
     * populates the tree view with data from model
     *
     * @param id The id of the organization we want the locations from
     */
    public void populateLocationTreeView(String id) {
        currentOrganization = organizationManager.getOrganization(id);
        searchLocations("");
    }

    private void initLocationTreeView() {

        TreeItem rootItem = new TreeItem<>();
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

        Location_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            searchLocations(newValue);
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

    /**
     * Searches trough the organizations
     *
     * @param searchKey string to search for
     */
    public void searchOrganization(String searchKey) {
        TreeItem rootItem = Organization_Tree_View.getRoot();
        rootItem.getChildren().clear();
        List<Organization> organizations = organizationManager.getOrganizations();
        for (Organization o : organizations) {
            String itemName = o.getId();
            if (itemName.toLowerCase().contains(searchKey.toLowerCase())) {
                TreeItem<String> newItem = new TreeItem<>(itemName);
                newItem.setExpanded(true);
                newItem.setGraphic(new ImageView("icons/organization-icon.png"));
                rootItem.getChildren().add(newItem);
            }
        }
    }

    /**
     * Searches through the locations
     *
     * @param searchKey string to search for
     */
    public void searchLocations(String searchKey) {

        if (currentOrganization == null) {
            return;
        }

        TreeItem rootItem = Location_Tree_View.getRoot();
        rootItem.getChildren().clear();

        List<Place> places = currentOrganization.getPlaces();
        for (Place p : places) {
            if (p.getName().toLowerCase().contains(searchKey.toLowerCase())) {
                TreeItem newItem = new TreeItem<>(p);
                newItem.setExpanded(true);
                newItem.setGraphic(iconize(p.getType()));
                rootItem.getChildren().add(newItem);
            }
        }
    }
}
