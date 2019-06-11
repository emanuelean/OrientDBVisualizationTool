package com.mycompany.orientdbvisualizationtool.model.places;

import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.database.PlaceAttributes;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Super class for places Contains all the functionality for locations,
 * buildings, floors, rooms, area and cells
 *
 * @author Albert, Niels
 */
public abstract class Place {

    private final String id;
    private final String name;
    private Place parent;
    private ArrayList<Place> children;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> childrenEntities;
    private PlaceAttributes attributes;

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
        entities = new ArrayList<>();
        childrenEntities = new ArrayList<>();
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
    @Override
    public String toString() {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return type + ": " + name;
        }
        return type + ": " + name.substring(index + 1);
    }

    /**
     *
     * @return the name to display in the list
     */
    public String getShortName() {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name;
        }
        return name.substring(index + 1);
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
        for (Vertex v : vertexEntities) {
            Entity newEntity = new Entity((String) v.getProperty("id"));
            entities.add(newEntity);
        }
    }

    /**
     * adds an entity to the entities list
     *
     * @param newEntity the new entity to be added
     */
    public void addEntity(Entity newEntity) {
        entities.add(newEntity);
    }

    /**
     * gets the full path of place from root.
     *
     * @return The path
     */
    public String getPath() {
        Place place = this;
        StringBuilder path = new StringBuilder();
        path.insert(0, place.toString() + "/");
        while (place.getParent() != null) {
            place = place.getParent();
            path.insert(0, place.toString() + "/");
        }
        return path.toString();
    }

    /**
     *
     * @return The attributes of a place
     */
    public PlaceAttributes getAttributes() {
        return attributes;
    }

    /**
     * Loads the attributes for this place
     */
    public void loadAttributes() {
        if (attributes == null) {
            attributes = DatabaseManager.getInstance().getPlaceData().getAttributes(this);
        }
    }

    /**
     * Checks if this place is equal to an object
     *
     * @param o The object this place is compared to
     * @return Whether or not this place is equal to the object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Place) {
            Place p = (Place) o;
            if (p.getId().equals(id) && p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
