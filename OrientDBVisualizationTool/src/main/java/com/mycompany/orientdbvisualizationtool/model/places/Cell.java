package com.mycompany.orientdbvisualizationtool.model.places;

/**
 * Contains the information about a cell
 * 
 * @author Albert, Niels
 */
public class Cell extends Place {

    /**
     * constructor
     *
     * @param id The id of the cell
     * @param name The name of the cell
     */
    public Cell(String id, String name) {
        super(id, name);
        type = PlaceCategory.Cell;
    }

}
