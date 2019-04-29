package com.mycompany.orientdbvisualizationtool.model;

/**
 * Contains all the data about an entity
 * 
 * @author Niels
 */
public class Entity {

    private String id;

    /**
     * constructor
     *
     * @param id The id of the entity
     */
    public Entity(String id) {
        this.id = id;
    }

    /**
     *
     * @return The id of the entity
     */
    public String getId() {
        return id;
    }
}
