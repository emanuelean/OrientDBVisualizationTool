package com.mycompany.orientdbvisualizationtool.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A struct to store a property and a property value
 *
 * @author Niels, Albert
 */
public class Property {

    /**
     * The name that describes what kind of property it is
     */
    private final StringProperty key;
    /**
     * The actual value of said property
     */
    private final StringProperty value;

    /**
     * Constructor
     *
     * @param key Property description
     * @param value Property value
     */
    public Property(String key, String value) {
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleStringProperty(value);
    }

    /**
     *
     * @return property key/description
     */
    public String getKey() {
        return key.get();
    }

    /**
     *
     * @return property value
     */
    public String getValue() {
        return value.get();
    }

}
