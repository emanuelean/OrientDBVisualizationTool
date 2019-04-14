package com.mycompany.orientdbvisualizationtool.model;

import com.mycompany.orientdbvisualizationtool.model.places.Place;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class Organization {

    private String id;
    private List<Place> places;

    /**
     * Constructor
     *
     * @param id The id of the organization
     */
    public Organization(String id) {
        this.id = id;
        places = new ArrayList<>();
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
     * Dereferences the list of places
     */
    public void dereferenceAll() {
        places = null;
    }

}
