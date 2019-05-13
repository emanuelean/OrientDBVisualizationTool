package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.Property;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;

/**
 * Retrieves the organization attributes from the database
 *
 * @author Niels, Albert
 */
public class OrganizationAttributes {

    private ArrayList<Property> properties;

    /**
     * Creates an object with the attributes of the organization
     *
     * @param v the vertex to get the attributes from
     */
    public OrganizationAttributes(Vertex v) {
        properties = new ArrayList();
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
            "password",
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
        for (int i = 0; i < keys.length; i++) {
            if (hasProperty(keys[i], v)) {
                Property prop = new Property();
                prop.key = keys[i];
                prop.value = v.getProperty(keys[i]);
                properties.add(prop);
            }
        }
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
        for (int i = 0; i < keys.length; i++) {
            if (hasProperty(keys[i], v)) {
                Property prop = new Property();
                prop.key = keys[i];
                if (v.getProperty(keys[i])) {
                    prop.value = "True";
                } else {
                    prop.value = "False";
                }
                properties.add(prop);
            }
        }
    }

    /**
     * Checks if the vertex has a certain property
     *
     * @param key The property we want to check
     * @param v The vertex we want the properties from
     */
    private boolean hasProperty(String key, Vertex v) {
        try {
            v.getProperty(key);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
