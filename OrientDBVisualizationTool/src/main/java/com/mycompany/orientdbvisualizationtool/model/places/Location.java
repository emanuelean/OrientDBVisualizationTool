package com.mycompany.orientdbvisualizationtool.model.places;

/**
 * Contains the information about a location
 * @author albert
 */
public class Location extends Place {

    /**
     * constructor
     *
     * @param id The id of the location
     * @param name The name of the location
     */
    public Location(String id, String name) {
        super(id, name);
        type = PlaceCategory.Location;
    }

}
