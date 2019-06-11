package com.mycompany.orientdbvisualizationtool.model;

import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.database.EntityAttributes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains all the data about an entity
 *
 * @author Niels, Albert
 */
public class Entity {

    private final StringProperty id;
    private EntityAttributes attributes;

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

    /**
     * Returns a string representation of this class
     * 
     * @return The string representation of this class
     */
    @Override
    public String toString() {
        return id.get();
    }

    /**
     * Loads the attributes for this place
     */
    public void loadAttributes() {
        if (attributes == null) {
            attributes = DatabaseManager.getInstance().getEntityData().getAttributes(this);
        }
    }

    /**
     *
     * @return The entity attributes
     */
    public EntityAttributes getAttributes() {
        return attributes;
    }
}
