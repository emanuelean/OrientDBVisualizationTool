package com.mycompany.orientdbvisualizationtool.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Albert
 */
public class View {

    private static final int WIDTH = 1400;
    protected FXMLLoader fxmlLoader; 
    private Scene scene;

    public View(String resource) {
        try{
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            Parent root = fxmlLoader.load();
            scene = new Scene(root, WIDTH, WIDTH * 9 / 16);
            scene.getStylesheets().add("styles/01_DefaultStyle.css");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getWIDTH() {
        return WIDTH;
    }
    
    public void start(){};
    
    public Scene getScene(){
        return scene;
    }
}
