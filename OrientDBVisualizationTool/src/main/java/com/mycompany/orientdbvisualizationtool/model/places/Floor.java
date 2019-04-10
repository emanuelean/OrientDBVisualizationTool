package com.mycompany.orientdbvisualizationtool.model.places;

/**
 *
 * @author albert
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
