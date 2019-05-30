package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.Property;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;

/**
 * Class that can retrieve and save the attributes of a certain vertex
 * 
 * @author Niels, Albert, Carlos
 */
public class Attributes {

    /**
     * A list of properties of a certain vertex
     */
    protected ArrayList<Property> properties;

    /**
     * Creates an object with the attributes of the vertex
     *
     */
    public Attributes() {
        properties = new ArrayList();
    }

    /**
     * Adds all the properties to the list of properties that have string values
     *
     * @param v The vertex we want the properties from
     * @param keys The list of property keys we want to try to add
     */
    protected void addProperties(Vertex v, String[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (hasProperty(keys[i], v)) {
                Property prop = new Property(keys[i], v.getProperty(keys[i]));
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
            String value = v.getProperty(key);
            if (value == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return A list of properties
     */
    public ArrayList<Property> getProperties() {
        return properties;
    }

}
