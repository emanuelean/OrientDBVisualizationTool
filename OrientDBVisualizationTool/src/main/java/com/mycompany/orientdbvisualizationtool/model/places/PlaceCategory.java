package com.mycompany.orientdbvisualizationtool.model.places;

/**
 * Enumerator that can describe what kind of category a place is
 *
 * @author albert
 */
public enum PlaceCategory {

    /**
     * A location
     */
    Location,

    /**
     * A building, usually belongs to a location
     */
    Building,

    /**
     * A floor, usually belongs to a building
     */
    Floor,

    /**
     * A room, usually belongs to a floor
     */
    Room,

    /**
     * An area, usually belongs to a room
     */
    Area,

    /**
     * A cell, usually belongs to an area
     */
    Cell
}
