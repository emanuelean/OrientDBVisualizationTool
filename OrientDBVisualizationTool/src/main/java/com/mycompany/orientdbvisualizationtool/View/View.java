package com.mycompany.orientdbvisualizationtool.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Super class for the view
 *
 * @author Albert, Niels, Yona
 */
public abstract class View {

    /**
     * Loader for the fxml file
     */
    protected FXMLLoader fxmlLoader;

    private static final int WIDTH = 1400;
    private Scene scene;

    /**
     * Constructor
     *
     * @param resource File path of fxml class that needs to be loaded
     */
    public View(String resource) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            Parent root = fxmlLoader.load();
            scene = new Scene(root, WIDTH, WIDTH * AspectRatio.Vertical / AspectRatio.Horizontal);
            scene.getStylesheets().add("styles/01_DefaultStyle.css");
        } catch (Exception e) {
            e.printStackTrace(); //needs to change to some sort of error pane
        }
    }

    /**
     *
     * @return The width
     */
    public static int getWIDTH() {
        return WIDTH;
    }

    /**
     * Starts a view
     */
    public void start() {
    }

    /**
     *
     * @return The scene
     */
    public Scene getScene() {
        return scene;
    }
}
