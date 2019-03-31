package com.mycompany.orientdbvisualizationtool.model;

import com.mycompany.orientdbvisualizationtool.model.places.Location;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import java.util.List;

/**
 *
 * @author Niels
 */
public class Orginazation {

    private String id;
    private List<Place> places;
    
    /**
     * constructor
     */
    public Orginazation(String id) {
        this.id = id;
    }
    
    /**
     * adds a location the list of locations
     * 
     * @param location 
     */
    public void addLocation(Location location) {
        places.add(location);
    }

    /**
     * 
     * @return The id of the organization
     */
    public String getId() {
        return id;
    }  
    
}
