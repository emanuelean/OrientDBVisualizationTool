package com.mycompany.orientdbvisualizationtool.model.places;

/**
 *
 * @author albert
 */
public class Location extends Place {

    public Location(String id, String name) {
        super(id, name);
        type = PlaceCategory.Location;
    }
    
}
