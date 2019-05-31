package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.mycompany.orientdbvisualizationtool.model.managers.OrganizationManager;
import com.mycompany.orientdbvisualizationtool.model.places.Location;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for retrieving specific information about organizations from the
 * database
 *
 * @author Niels, Albert
 */
public class OrganizationData extends Database {

    private final OrganizationManager organizationManager;
    private Organization allOrganization;

    /**
     * constructor
     *
     * @param graph The graph we want to load the data from
     */
    public OrganizationData(OrientGraph graph) {
        super(graph);
        organizationManager = OrganizationManager.getInstance();
    }

    @Override
    public void refresh(String id) {
        refresh(getVertexById("V_organization.id", id));
    }

    /**
     * Loads the graph with an organization based on a vertex
     *
     * @param vertex The vertex we want to add
     */
    public void refresh(Vertex vertex) {
        Organization newOrganization = new Organization(vertex.getProperty("id"));
        organizationManager.addOrganization(newOrganization);
        addPlacesToOrganization(newOrganization);
    }

    /**
     * Adds all places of a specific organization
     *
     * @param organization The organization of which we want to add the places
     */
    private void addPlacesToOrganization(Organization organization) {
        for (Vertex v : queryVertices("SELECT * FROM V_location WHERE @RID IN (SELECT out(owns)  FROM V_organization WHERE id == '" + organization.getId() + "')")) {
            Place newPlace = new Location(v.getProperty("id"), v.getProperty("name"));
            organization.addPlace(newPlace);
        }
    }

    /**
     * Refreshes the data for all of the organizations
     */
    public void refreshAll() {
        organizationManager.emptyOrganizations();
        for (Vertex v : queryVertices("SELECT id FROM V_organization WHERE @RID IN (SELECT out FROM E_owns)")) {
            refresh(v);
        }
        addAllOrganization();
        addNoOrganization();
        organizationManager.addOrganization(allOrganization);
    }

    /**
     * Retrieves the attributes of a certain organization
     *
     * @param org The organization we want to know the attributes of
     * @return An object containing the place attributes
     */
    public OrganizationAttributes getAttributes(Organization org) {
        Vertex v = getVertexById("V_organization.id", org.getId());
        return new OrganizationAttributes(v);
    }

    /**
     * Adds all the unconnected places
     */
    private void addNoOrganization() {
        Organization organization = new Organization("Unknown");
        for (Place p : allOrganization.getPlaces()) {
            organization.addPlace(p);
        }
        //list of places that have an organization
        for (Organization o : OrganizationManager.getInstance().getOrganizations()) {
            organization.getPlaces().removeAll(o.getPlaces());
        }
        organizationManager.addOrganization(organization);
    }

    /**
     * Adds all the places
     */
    private void addAllOrganization() {
        allOrganization = new Organization("All");
        for (Vertex v : queryVertices("SELECT * FROM V_location ")) {
            Place newPlace = new Location(v.getProperty("id"), v.getProperty("name"));
            allOrganization.addPlace(newPlace);
        }
    }
}
