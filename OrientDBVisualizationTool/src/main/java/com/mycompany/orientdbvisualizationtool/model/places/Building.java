package com.mycompany.orientdbvisualizationtool.model.places;

/**
 *
 * @author albert
 */
public class Building extends Place {

    /**
     * constructor
     *
     * @param id The id of the building
     * @param name The name of the building
     */
    public Building(String id, String name) {
        super(id, name);
        type = PlaceCategory.Building;
    }

}
