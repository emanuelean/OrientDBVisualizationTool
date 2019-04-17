package com.mycompany.orientdbvisualizationtool;

import com.mycompany.orientdbvisualizationtool.View.MainView;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.*;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.database.EntityData;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
/**
 *
 * @author Niels
 */
public class VisTool {

    /**
     * Main function
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        //DatabaseManager db = DatabaseManager.getInstance();
        //db.getOrganizationData().refreshAll();
        testDataForFrontEnd();
        //db.getPlaceData().refresh("GSV.HQ");
        Application.launch(MainView.class, args);

        //EntityData e = db.getEntityData();
        //List<Vertex> lv = e.getSensorsFromLocation("Energy Academy Europe");
    }

    /**
     * Prints some data of all sensors of a specific location
     *
     */
    private static void testDataForFrontEnd() {
        PlaceManager manager = PlaceManager.getInstance();
        addTestPlaces(manager, null, 0);
        addTestOrganizations();
    }
    
    private static void addTestOrganizations(){
        int organizationAmount = 8;
        int placesAmount = 4;
        for (int i = 0; i < organizationAmount; i++) {
            Organization newOrg = new Organization("Organization" + Integer.toString(i));
            for (int j = 0; j < placesAmount; j++) {
                Location newPlace = new Location("Location"+Integer.toString(j), "Location"+Integer.toString(j));
                newOrg.addPlace(newPlace);
            }
        }
    }
    
    private static void addTestPlaces(PlaceManager manager, Place parent, int index){
        
        //base case
        if(index >= PlaceCategory.values().length){
            return;
        }
        
        //get parent info
        String prefix = "";
        if(parent != null){
            prefix = parent.getId() + ".";
        }
        
        //create info for the newPlace
        int number = (new Random()).nextInt(150);
        String currentCategory = (PlaceCategory.values()[index]).toString().toLowerCase();
        String name = prefix + currentCategory + Integer.toString(number);
        String id =  prefix + currentCategory + Integer.toString(number);
        
        Place newPlace = manager.addPlace(id, name, currentCategory, parent);
        addEntities(newPlace);
        
        //recursvily call it for all the children
        int childrenAmount = 5;
        for (int i = 0; i < childrenAmount; i++) {
            addTestPlaces(manager,newPlace, index+1);
        }
    }
    
    private static void addEntities(Place currentPlace){
        Random random = new Random();
        for (int i = 0; i < random.nextInt(100); i++) {
            Entity newEntity = new Entity("long_name_for_testdata_sensor_stuff_" + i);
            currentPlace.addEntity(newEntity);
        }
    }

    /**
     * Prints some data of all sensors of a specific location
     *
     * @param db The DatabaseManager that handles the graph
     */
    private static void printSensors(DatabaseManager db) {
        System.out.println("Database loaded");
        List<Vertex> sensors;
        sensors = db.getEntityData().getSensorsFromLocation("Energy Academy Europe");
        System.out.println(sensors);
        for (Vertex v : sensors) {
            System.out.printf(v.getProperty("id"));
        }
    }
}
