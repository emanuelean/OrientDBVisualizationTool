package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import com.orientechnologies.orient.core.exception.OSchemaException;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import javax.persistence.*;

/**
 *
 * @author Niels
 */
public class DatabaseManager {

    private static DatabaseManager singletonInstance;

    private OrientGraph graph;
    private PlaceManager placeManager;

    /**
     * Makes sure only one instance of this class can exist
     *
     * @return the current class
     */
    public static DatabaseManager getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new DatabaseManager();
        }
        return singletonInstance;
    }

    private DatabaseManager() {
        //this("plocal:/opt/orientdb/databases/Demo", "root", "root");
        this("plocal:localhost/Demo", "Foo", "bar");
        long amount_vertices = graph.countVertices();
        System.out.println("Counter: " + amount_vertices);
    }

    private DatabaseManager(String host, String user, String password) {
        if (user.equals("") || password.equals("")) {
            graph = new OrientGraph(host);
        } else {
            graph = new OrientGraph(host, user, password);
        }
        placeManager = PlaceManager.getInstance();
    }

    public void refreshGraph(String id) {
        placeManager.emptyPlaces();
        Vertex v = getLocation(id);
        addLocationToModel(v);
        placeManager.printData();
    }

    private String getCategory(Vertex place) {
        Iterable<Vertex> vertices;
        vertices = place.getVertices(Direction.OUT, "instance-of");
        for (Vertex v : vertices) {
            return v.getProperty("id");
        }
        return "location";
    }

    private void addLocationToModel(Vertex location) {
        addLocationToModel(location, null);
    }

    private void addLocationToModel(Vertex location, Place parent) {
        String id = (String) location.getProperty("id");
        String name = (String) location.getProperty("name");
        String category = getCategory(location);
        Place newPlace = placeManager.addPlace(id, name, category, parent);
                
        Iterable<Vertex> vertices;
        vertices = location.getVertices(Direction.IN, "part-of");
        for (Vertex v : vertices) {
            addLocationToModel(v, newPlace);
        }
    }

    private Vertex getLocation(String id) {
        Iterable<Vertex> vertices = graph.getVertices("V_location.id", id);
        for (Vertex v : vertices) {
            System.out.println("Something: " + v);
            return v;
        }
        return null;
    }

    public void shutdown() {
        graph.shutdown();
    }

}
