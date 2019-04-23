package com.mycompany.orientdbvisualizationtool.model.places;

import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author albert
 */
public abstract class Place {

    private String id;
    private String name;
    private Place parent;
    private ArrayList<Place> children;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> childrenEntities;
    
    /**
     * The type/category of place
     */
    protected PlaceCategory type;

    /**
     * Constructor
     *
     * @param id The id of the place
     * @param name The name of the place
     */
    public Place(String id, String name) {
        this.id = id;
        this.name = name;
        parent = null;
        children = new ArrayList<>();
    }

    /**
     *
     * @param newParent The new parent of the place
     */
    public void setParent(Place newParent) {
        parent = newParent;
    }

    /**
     * Adds a new child to the list of children
     *
     * @param newChild The child we want to add
     */
    public void addChild(Place newChild) {
        children.add(newChild);
    }

    /**
     *
     * @return The id of this place
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return The name of this place
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return The parent of this place
     */
    public Place getParent() {
        return parent;
    }

    /**
     *
     * @return A list of children of this place
     */
    public ArrayList<Place> getChildren() {
        return children;
    }

    /**
     *
     * @return the type of the place
     */
    public PlaceCategory getType() {
        return type;
    }

    /**
     * Dereferences the parent and the list of children
     */
    public void dereferenceAll() {
        parent = null;
        children = null;
    }

    /**
     *
     * @return the name to display on the graph
     */
    public String getDisplayName() {
        int index = name.lastIndexOf(".");
        if (index == -1) {
            return name;
        }
        return type + ": " + name.substring(index + 1);
    }

    /**
     *
     * @return A list of entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Generates a list of entities for the current place and all of its
     * children
     *
     * @return A list of entities
     */
    public ArrayList<Entity> getAllEntities() {
        if (!childrenEntities.isEmpty()) {
            return childrenEntities;
        }
        for (Place p : children) {
            childrenEntities.addAll(p.getAllEntities());
        }
        loadEntities();
        childrenEntities.addAll(entities);
        return childrenEntities;
    }

    /**
     * Loads the entities into the entity list
     */
    public void loadEntities() {
        if (!entities.isEmpty()) {
            return;
        }
        DatabaseManager db = DatabaseManager.getInstance();
        List<Vertex> vertexEntities = db.getEntityData().getSensorsFromLocation(id);
        for(Vertex v: vertexEntities){
            Entity newEntity = new Entity((String)v.getProperty("id"));
            entities.add(newEntity);
        }
    }
}
