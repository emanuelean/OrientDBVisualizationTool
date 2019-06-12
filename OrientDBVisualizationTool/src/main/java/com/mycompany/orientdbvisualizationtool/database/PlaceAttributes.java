package com.mycompany.orientdbvisualizationtool.database;

import com.tinkerpop.blueprints.Vertex;

/**
 * Retrieves the place attributes from the database
 *
 * @author Niels, Albert, Carlos
 */
public class PlaceAttributes extends Attributes {

    /**
     * Creates an object with the attributes of the place
     *
     * @param v the vertex to get the attributes from
     */
    public PlaceAttributes(Vertex v) {
        addStringProperties(v);
        addDoubleProperties(v);
    }

    /**
     * Adds all the properties to the list of properties that have string values
     *
     * @param v The vertex we want the properties from
     */
    private void addStringProperties(Vertex v) {
        String[] keys = {
            "organization",
            "year",
            "city",
            "country",
            "address",
            "name",
            "postal",
            "e-label",
            "building-type",};
        addProperties(v, keys);
    }

    /**
     * Adds all the properties to the list of properties that have a
     * double/float values
     *
     * @param v The vertex we want the properties from
     */
    private void addDoubleProperties(Vertex v) {
        String[] keys = {
            "area",
            "e-peak",
            "g-peak",
            "e-index"
        };
        addProperties(v, keys);
    }
}
