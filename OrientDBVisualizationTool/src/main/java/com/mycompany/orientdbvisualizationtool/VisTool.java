package com.mycompany.orientdbvisualizationtool;

import com.mycompany.orientdbvisualizationtool.View.MainView;
import java.util.List;
import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.database.EntityData;
import com.tinkerpop.blueprints.Vertex;
import javafx.application.Application;

//import javafx.application.Application;
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
        DatabaseManager db = DatabaseManager.getInstance();
        db.getOrganizationData().refreshAll();

        db.getPlaceData().refresh("GSV.HQ");
        Application.launch(MainView.class, args);

        //EntityData e = db.getEntityData();
        //List<Vertex> lv = e.getSensorsFromLocation("Energy Academy Europe");
    }

    /**
     * Prints some data of all sensors of a specific location
     *
     * @param db The databasemanager that handles the graph
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
