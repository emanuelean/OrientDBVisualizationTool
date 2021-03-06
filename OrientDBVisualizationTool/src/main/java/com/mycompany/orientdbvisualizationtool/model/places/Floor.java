package com.mycompany.orientdbvisualizationtool.model.places;

/**
 * Contains the information about a floor
 *
 * @author Albert, Niels
 */
public class Floor extends Place {

    /**
     * constructor
     *
     * @param id The id of the floor
     * @param name The name of the floor
     */
    public Floor(String id, String name) {
        super(id, name);
        type = PlaceCategory.Floor;
    }

}
