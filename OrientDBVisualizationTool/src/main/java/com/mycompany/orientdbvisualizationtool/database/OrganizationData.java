
package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.mycompany.orientdbvisualizationtool.model.managers.OrganizationManager;
import com.mycompany.orientdbvisualizationtool.model.places.Location;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * Responsible for retrieving specific information about organizations from the
 * database
 *
 * @author Niels
 */
public class OrganizationData extends Database {

    private OrganizationManager organizationManager;

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
    }
}
