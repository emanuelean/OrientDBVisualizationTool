package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.PlaceManager;
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
        Vertex v = getLocation("GSV.HQ");
        addLocationToModel(v);
    }

    private void addLocationToModel(Vertex location) {
        Iterable<Edge> edges;
        edges = location.getEdges(Direction.IN, "part-of");
        for (Edge e : edges) {
            System.out.println(e);
        }
    }

    private Vertex getLocation(String id) {
        Iterable<Vertex> vertices = graph.getVertices("V_location.id", id);
        for(Vertex v: vertices){
            System.out.println("Something: " + v);
            return v;
        }
        return null;
    }

    public void shutdown() {
        graph.shutdown();
    }

}
