package com.mycompany.orientdbvisualizationtool.database;

import com.tinkerpop.blueprints.Vertex;

/**
 * Retrieves the organization attributes from the database
 *
 * @author Niels, Albert, Carlos
 */
public class OrganizationAttributes extends Attributes {

    /**
     * Creates an object with the attributes of the organization
     *
     * @param v the vertex to get the attributes from
     */
    public OrganizationAttributes(Vertex v) {
        addStringProperties(v);
        addBooleanProperties(v);
    }

    /**
     * Adds all the properties to the list of properties that have string values
     *
     * @param v The vertex we want the properties from
     */
    private void addStringProperties(Vertex v) {
        String[] keys = {
            "area",
            "username",
            "email",
            "job-title",
            "mobile",
            "first-name",
            "last-name",
            "role",
            "name",
            "working-hours-start",
            "short",
            "working-hours-end",
            "oid"
        };
        addProperties(v, keys);
    }

    /**
     * Adds all the properties to the list of properties that have a boolean
     * values
     *
     * @param v The vertex we want the properties from
     */
    private void addBooleanProperties(Vertex v) {
        String[] keys = {
            "consumption",
            "environment",
            "savings-plan",
            "public-dashboards",
            "generation"
        };
        addProperties(v, keys);
    }
}
