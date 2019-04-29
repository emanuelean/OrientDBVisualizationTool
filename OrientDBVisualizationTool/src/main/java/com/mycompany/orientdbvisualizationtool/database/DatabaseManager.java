package com.mycompany.orientdbvisualizationtool.database;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * Responsible for setting up the database connection and initializing the
 * classes that take care of the retrieving the data
 *
 * @author Niels
 */
public class DatabaseManager {

    private static DatabaseManager singletonInstance;

    private OrientGraph graph;
    private OrganizationData organizationData;
    private PlaceData placeData;
    private EntityData entityData;

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
        organizationData = new OrganizationData(graph);
        placeData = new PlaceData(graph);
        entityData = new EntityData(graph);
    }

    /**
     * closes the database connection
     */
    public void shutdown() {
        graph.shutdown();
    }

    /**
     *
     * @return The organization data
     */
    public OrganizationData getOrganizationData() {
        return organizationData;
    }

    /**
     *
     * @return The place data
     */
    public PlaceData getPlaceData() {
        return placeData;
    }

    /**
     *
     * @return The entity data
     */
    public EntityData getEntityData() {
        return entityData;
    }
}
