package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

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

    /**
     * constructor
     */
    private DatabaseManager() {
        this("plocal:localhost/Demo", "Foo", "bar");
    }

    /**
     * constructor
     *
     * @param host The file path of the database host
     * @param user The username of the user you want to log in as(for the
     * database)
     * @param password The password of the user you want to log in as(for the
     * database)
     */
    private DatabaseManager(String host, String user, String password) {
        if (user.equals("") || password.equals("")) {
            graph = new OrientGraph(host);
        } else {
            graph = new OrientGraph(host, user, password);
        }
        placeManager = PlaceManager.getInstance();
    }

    /**
     * refreshes all the children of a location so that these classes contain
     * the most recent data from the database
     *
     * @param id The id of the location we want to refresh
     */
    public void refreshGraph(String id) {
        placeManager.emptyPlaces();
        Vertex v = getPlace(id);
        addPlaceToModel(v);
        //For development purposes only
        placeManager.printData();
    }

    /**
     * Determines the category of a certain place
     *
     * @param place The place we want to know the category of
     * @return The category of the place
     */
    private String getCategory(Vertex place) {
        Iterable<Vertex> vertices;
        vertices = place.getVertices(Direction.OUT, "instance-of");
        for (Vertex v : vertices) {
            return v.getProperty("id");
        }
        return "location";
    }

    /**
     * Adds a place and all its children to the model
     *
     * @param place The place we want to add
     */
    private void addPlaceToModel(Vertex place) {
        addPlaceToModel(place, null);
    }

    /**
     * Adds a place and all its children to the model and connects all the
     * children to their respective parents
     *
     * @param place The place we want to add
     * @param parent The parent we want to link the place to
     */
    private void addPlaceToModel(Vertex place, Place parent) {
        String id = (String) place.getProperty("id");
        String name = (String) place.getProperty("name");
        String category = getCategory(place);
        Place newPlace = placeManager.addPlace(id, name, category, parent);

        //perform a sort of depth first search on the graph until we have added all the children
        Iterable<Vertex> vertices;
        vertices = place.getVertices(Direction.IN, "part-of");
        for (Vertex v : vertices) {
            addPlaceToModel(v, newPlace);
        }
    }

    /**
     * retrieves a place based on an id
     *
     * @param id The id of the place we want to find
     * @return The place that matches the id
     */
    private Vertex getPlace(String id) {
        Iterable<Vertex> vertices = graph.getVertices("V_location.id", id);
        for (Vertex v : vertices) {
            return v;
        }
        return null;
    }

    /**
     * closes the database connection
     */
    public void shutdown() {
        graph.shutdown();
    }

}
