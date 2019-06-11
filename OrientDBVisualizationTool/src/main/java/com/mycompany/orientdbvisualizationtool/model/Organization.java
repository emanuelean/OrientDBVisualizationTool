package com.mycompany.orientdbvisualizationtool.model;

import com.mycompany.orientdbvisualizationtool.database.DatabaseManager;
import com.mycompany.orientdbvisualizationtool.database.OrganizationAttributes;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the data about an organization Has a list of places that are
 * connected to the organization
 *
 * @author Niels
 */
public class Organization {

    private final String id;
    private final String name;
    private List<Place> places;
    private OrganizationAttributes attributes;

    /**
     * Constructor
     *
     * @param id The id of the organization
     */
    public Organization(String id) {
        this.id = id;
        name = getNameFromId(id);
        places = new ArrayList<>();
    }
    
    private String getNameFromId(String id) {
        String newName;
        try{ 
            newName = id.substring(id.lastIndexOf('.') + 1);
        } catch(Exception e) {
            return id;
        }
        return newName;
    }

    /**
     * adds a location the list of locations
     *
     * @param place The place we want to add
     */
    public void addPlace(Place place) {
        places.add(place);
    }

    /**
     *
     * @return The id of the organization
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return The name of the organization
     */
    public String getName() {
        return name;
    }

    /**
     * Dereferences the list of places
     */
    public void dereferenceAll() {
        places = null;
    }

    /**
     *
     * @return The list of places connected to this organization
     */
    public List<Place> getPlaces() {
        return places;
    }

    /**
     * Loads the attributes for this place
     */
    public void loadAttributes() {
        if (attributes == null) {
            attributes = DatabaseManager.getInstance().getOrganizationData().getAttributes(this);
        }
    }

    /**
     *
     * @return The organization attributes
     */
    public OrganizationAttributes getAttributes() {
        return attributes;
    }
}
