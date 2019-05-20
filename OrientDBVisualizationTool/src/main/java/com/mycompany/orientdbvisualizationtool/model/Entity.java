package com.mycompany.orientdbvisualizationtool.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains all the data about an entity
 * 
 * @author Niels, Albert
 */
public class Entity {

    private final StringProperty id;

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

    @Override
    public String toString(){
        return id.get();
    }
}
