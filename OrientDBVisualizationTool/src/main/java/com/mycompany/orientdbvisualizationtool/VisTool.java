package com.mycompany.orientdbvisualizationtool;

import com.mycompany.orientdbvisualizationtool.View.MainView;
import java.util.List;

//import com.mycompany.orientdbvisualizationtool.View.MainFrame;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.model.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.*;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import javafx.application.Application;

//import javafx.application.Application;

/**
 *
 * @author Niels
 */
public class VisTool {

    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();
        db.refreshGraph("GSV.HQ");
        Application.launch(MainView.class, args);
        
        System.out.println("Database loaded");
        List<Vertex> sensors;
        sensors = db.getSensorsFromLocation("Energy Academy Europe");
        System.out.println(sensors);
        for (Vertex v: sensors) {
        	System.out.printf(v.getProperty("id"));
        }
        db.shutdown();
    }

    /**
     * This data is meant for the front end, so that they have some data
     */
    private static void testDataForFrontEnd() {
        PlaceManager manager = PlaceManager.getInstance();

        Place location = new Location("L", "L");
        Place buildingA = new Building("L.A", "A");
        Place buildingB = new Building("L.B", "B");
        Place buildingC = new Building("L.C", "C");
        Place buildingD = new Building("L.D", "D");
        Place floorAA = new Floor("L.A.A", "AA");
        Place floorAB = new Floor("L.A.B", "AB");
        Place floorBA = new Floor("L.B.A", "BA");
        Place floorBB = new Floor("L.B.B", "BB");
        Place roomAAA = new Room("L.A.A.A", "AAA");
        Place roomAAB = new Room("L.A.A.B", "AAB");
        Place roomABA = new Room("L.A.B.A", "ABA");
        Place roomABB = new Room("L.A.B.B", "ABB");
        Place roomBAA = new Room("L.B.A.A", "BAA");
        Place roomBAB = new Room("L.B.A.B", "BAB");
        Place roomBBA = new Room("L.B.B.A", "BBA");
        Place roomBBB = new Room("L.B.B.B", "BBB");

        manager.addPlace(location, null);
        manager.addPlace(buildingA, location);
        manager.addPlace(buildingB, location);
        manager.addPlace(buildingC, location);
        manager.addPlace(buildingD, location);
        manager.addPlace(floorAA, buildingA);
        manager.addPlace(floorAB, buildingA);
        manager.addPlace(floorBA, buildingB);
        manager.addPlace(floorBB, buildingB);
        manager.addPlace(roomAAA, floorAA);
        manager.addPlace(roomAAB, floorAA);
        manager.addPlace(roomABA, floorAB);
        manager.addPlace(roomABB, floorAB);
        manager.addPlace(roomBAA, floorBA);
        manager.addPlace(roomBAB, floorBA);
        manager.addPlace(roomBBA, floorBB);
        manager.addPlace(roomBBB, floorBB);
    }
}
