package com.mycompany.orientdbvisualizationtool;

import com.mycompany.orientdbvisualizationtool.model.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.*;
import com.mycompany.orientdbvisualizationtool.View.MainView;
import javafx.application.Application;

/**
 *
 * @author Niels
 */
public class VisTool {
    
    public static void main(String[] args) {
        testDataForFrontEnd();
        //Test test = new Test();
        //test.mainTestDB();
        Application.launch(MainView.class, args);
    }
    
    /**
     * This data is meant for the front end, so that they have some data
     */
    public static PlaceManager testDataForFrontEnd() {
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
        return manager;
    }
}
