package com.mycompany.orientdbvisualizationtool.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Niels
 */
public class Entity {

    private StringProperty id;

    /**
     * constructor
     *
     * @param id The id of the entity
     */
    public Entity(String id) {
        this.id = new SimpleStringProperty(id);
    }

    /**
     *
     * @return The id of the entity
     */
    public String getId() {
        return id.get();
    }
}
