package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.mycompany.orientdbvisualizationtool.model.managers.OrganizationManager;
import com.mycompany.orientdbvisualizationtool.model.places.Location;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 *
 * @author Niels
 */
public class OrganizationData extends Database {

    private OrganizationManager organizationManager;

    public OrganizationData(OrientGraph graph) {
        super(graph);
        organizationManager = OrganizationManager.getInstance();
    }

    @Override
    public void refresh(String id) {

    }

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
        System.out.println("All places for organization " + organization.getId());
        for (Vertex v : queryVertices("SELECT * FROM V_location WHERE @RID IN (SELECT out(owns)  FROM V_organization WHERE id == '" + organization.getId() + "')")) {
            Place newPlace = new Location(v.getProperty("id"), v.getProperty("name"));
            organization.addPlace(newPlace);
            System.out.println("location: " + newPlace.getDisplayName());
        }
    }

    public void refreshAll() {
        organizationManager.emptyOrganizations();
        for (Vertex v : queryVertices("SELECT id FROM V_organization WHERE @RID IN (SELECT out FROM E_owns)")) {
            refresh(v);
        }
    }

}
