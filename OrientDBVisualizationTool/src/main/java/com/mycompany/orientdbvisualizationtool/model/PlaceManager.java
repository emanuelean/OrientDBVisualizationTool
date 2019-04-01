package com.mycompany.orientdbvisualizationtool.model;

import com.mycompany.orientdbvisualizationtool.model.places.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class PlaceManager {

    private static PlaceManager singletonInstance;
    private List<Place> places;

    private PlaceManager() {
        places = new ArrayList<>();
    }

    /**
     * Makes sure only one instance of this class can exist
     *
     * @return the current class
     */
    public static PlaceManager getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new PlaceManager();
        }
        return singletonInstance;
    }

    /**
     * Links a child and a parent to eachother if they exist
     *
     * @param child The child we want to link the parent to
     * @param parent The parent we want to link a child to
     */
    public void linkPlaces(Place child, Place parent) {
        if (parent != null && child != null) {
            child.setParent(parent);
            parent.addChild(child);
        }
    }

    /**
     * Adds a new place and adds this place to the list of places
     *
     * @param newPlace The place we want to add
     * @param parent The parent of the place we want to add
     * @return the new place
     */
    public Place addPlace(Place newPlace, Place parent) {
        linkPlaces(newPlace, parent);
        places.add(newPlace);
        return newPlace;
    }

    /**
     * Adds a new place and adds this place to the list of places
     *
     * @param id The id of the place, contains the hierarchy
     * @param name The name of the place
     * @param category Indicates what kind of place it is
     * @param parent The parent of the new place we want to add(if it exists)
     * @return the new place
     */
    public Place addPlace(String id, String name, String category, Place parent) {
        Place newPlace = null;
        switch (category) {
            case "location":
                newPlace = new Location(id, name);
                break;
            case "building":
                newPlace = new Building(id, name);
                break;
            case "floor":
                newPlace = new Floor(id, name);
                break;
            case "room":
                newPlace = new Room(id, name);
                break;
            case "area":
                newPlace = new Area(id, name);
                break;
            case "cell":
                newPlace = new Cell(id, name);
                break;
            default:
                break;
        }
        return addPlace(newPlace, parent);
    }

    /**
     * Destroys all instances of its classes to prevent all things existing at
     * the same time
     */
    public void emptyPlaces() {
        for (Place p : places) {
            p.dereferenceAll();
        }
        places = new ArrayList<>();
    }

    /**
     *
     * @param id The id of the class we want to find
     * @return The class with the id
     */
    public Place getPlace(String id) {
        for (Place p : places) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Gets the root of the tree
     *
     * @return The root of places tree
     */
    public Place getRoot() {     //TODO:: make it official
        return places.get(0);
    }

    /**
     * prints some data for all the places
     */
    public void printData() {
        for (Place p : places) {
            System.out.println("[Object] - " + p.getId() + " - " + p.getName() + " - " + p.getClass().getName());
        }
    }

}
