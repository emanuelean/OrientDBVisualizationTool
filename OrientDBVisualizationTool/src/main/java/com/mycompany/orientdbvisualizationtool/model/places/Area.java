package com.mycompany.orientdbvisualizationtool.model.places;

/**
 *
 * @author albert
 */
public class Area extends Place{

    public Area(String id, String name) {
        super(id, name);
        type = PlaceCategory.Area;
    }
    
}
