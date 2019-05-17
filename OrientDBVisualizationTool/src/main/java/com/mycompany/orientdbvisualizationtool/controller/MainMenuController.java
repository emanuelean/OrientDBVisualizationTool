package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.VisApplication;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.mycompany.orientdbvisualizationtool.model.managers.OrganizationManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import com.mycompany.orientdbvisualizationtool.model.places.PlaceCategory;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class for the main menu
 *
 * @author Niels, Albert
 */
public class MainMenuController {

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

    //private final ObservableList<Property> propertiesTable = FXCollections.observableArrayList();
    private OrganizationManager organizationManager;
    private Place currentPlace;

    /**
     * The main default properties of controller are initialized
     */
    @FXML
    public void initialize() {

        Node_Name_Text_Field.setDisable(true);
        Node_ID_Text_Field.setDisable(true);
        Node_Type_Text_Field.setDisable(true);
        organizationManager = OrganizationManager.getInstance();
        populateOrganizationTreeView();
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
        //propertiesTable.clear();
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
    public void populateOrganizationTreeView() {
        TreeItem<String> rootItem = new TreeItem<>();
        List<Organization> organizations = organizationManager.getOrganizations();
        for (Organization o : organizations) {
            String itemName = o.getId();
            TreeItem<String> newItem = new TreeItem<>(itemName);
            newItem.setExpanded(true);
            newItem.setGraphic(new ImageView("icons/location-icon.png"));
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
