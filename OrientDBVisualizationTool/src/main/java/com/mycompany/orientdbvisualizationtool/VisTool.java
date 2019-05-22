package com.mycompany.orientdbvisualizationtool;

import com.mycompany.orientdbvisualizationtool.View.VisApplication;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.*;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.Organization;

import java.util.Random;
import javafx.application.Application;

/**
 * Main class
 *
 * @author Niels, Albert, Yona
 */
public class VisTool {

    /**
     * Main function
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();
        db.getOrganizationData().refreshAll();
        Application.launch(VisApplication.class, args);
    }

    /**
     * Prints some data of all sensors of a specific location
     *
     */
    private static void testDataForFrontEnd() {
        addTestPlaces(null, 0);
        addTestOrganizations();
    }


    /**
     * Adds test organization
     */
    private static void addTestOrganizations() {
        int organizationAmount = 8;
        int placesAmount = 4;
        for (int i = 0; i < organizationAmount; i++) {
            Organization newOrg = new Organization("Organization" + Integer.toString(i));
            for (int j = 0; j < placesAmount; j++) {
                Location newPlace = new Location("Location" + Integer.toString(j), "Location" + Integer.toString(j));
                newOrg.addPlace(newPlace);
            }
        }
    }

    /**
     * Adds test places
     *
     * @param parent The parent of the places
     * @param index The index of the place
     */
    private static void addTestPlaces(Place parent, int index) {
        PlaceManager manager = PlaceManager.getInstance();
        //base case
        if (index >= PlaceCategory.values().length) {
            return;
        }

        //get parent info
        String prefix = "";
        if (parent != null) {
            prefix = parent.getId() + ".";
        }

        //create info for the newPlace
        int number = (new Random()).nextInt(150);
        String currentCategory = (PlaceCategory.values()[index]).toString().toLowerCase();
        String name = prefix + currentCategory + Integer.toString(number);
        String id = prefix + currentCategory + Integer.toString(number);

        Place newPlace = manager.addPlace(id, name, currentCategory, parent);
        addEntities(newPlace);

        //recursvily call it for all the children
        int childrenAmount = 5;
        for (int i = 0; i < childrenAmount; i++) {
            addTestPlaces(newPlace, index + 1);
        }
    }

    /**
     * adds random test entities
     *
     * @param currentPlace The place that the entities are added to
     */
    private static void addEntities(Place currentPlace) {
        Random random = new Random();
        for (int i = 0; i < random.nextInt(100); i++) {
            Entity newEntity = new Entity("long_name_for_testdata_sensor_stuff_" + i);
            currentPlace.addEntity(newEntity);
        }
    }
}
