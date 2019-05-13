package com.mycompany.orientdbvisualizationtool.model.places;

/**
 * Contains the information about an area
 *
 * @author Albert, Niels
 */
public class Area extends Place {

    /**
     * constructor
     *
     * @param id The id of the area
     * @param name The name of the area
     */
    public Area(String id, String name) {
        super(id, name);
        type = PlaceCategory.Area;
    }

}
