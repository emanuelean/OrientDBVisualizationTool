package com.mycompany.orientdbvisualizationtool.model.places;

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
     * Dereferences the parent and the list of children
     */
    public void dereferenceAll() {
        parent = null;
        children = null;
    }
}
