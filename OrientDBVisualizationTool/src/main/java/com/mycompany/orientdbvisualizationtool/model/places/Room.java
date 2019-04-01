package com.mycompany.orientdbvisualizationtool.model.places;

/**
 *
 * @author albert
 */
public class Room extends Place{

    public Room(String id, String name) {
        super(id, name);
        type = PlaceCategory.Room;
    }
    
}
