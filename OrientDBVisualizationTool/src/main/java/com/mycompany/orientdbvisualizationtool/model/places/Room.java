package com.mycompany.orientdbvisualizationtool.model.places;

/**
 *
 * @author albert
 */
public class Room extends Place {

    /**
     * constructor
     *
     * @param id The id of the room
     * @param name The name of the room
     */
    public Room(String id, String name) {
        super(id, name);
        type = PlaceCategory.Room;
    }

}
