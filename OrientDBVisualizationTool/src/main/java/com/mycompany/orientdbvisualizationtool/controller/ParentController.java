package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.model.places.PlaceCategory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Parent controller for MainController and MainMenuController
 */
public abstract class ParentController {

    /**
     * returns the correct icon for a given place type.
     *
     * @param placeType enum for the place type
     * @return image view icon for given place type
     */
    protected ImageView iconize(PlaceCategory placeType) {
        ImageView view = new ImageView();
        switch (placeType) {
            case Location:
                view.setImage(new Image("icons/location-icon.png"));
                break;
            case Building:
                view.setImage(new Image("icons/building-icon.png"));
                break;
            case Floor:
                view.setImage(new Image("icons/floor-icon.png"));
                break;
            case Room:
                view.setImage(new Image("icons/room-icon.png"));
                break;
            case Area:
                view.setImage(new Image("icons/area-icon.png"));
                break;
            case Cell:
                view.setImage(new Image("icons/cell-icon.png"));
                break;
        }
        return view;
    }
}
